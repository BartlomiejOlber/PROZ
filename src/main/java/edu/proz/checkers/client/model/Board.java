package edu.proz.checkers.client.model;

import java.util.LinkedList;

import edu.proz.checkers.Constants;

/**
 * Class that represents the whole board in model.
 */
public class Board {

    // 2D array that holds all the squares the board consists of.
    private Square[][] allSquaresOnBoard;

    /**
     * Constructor which:
     * - sets the size of 2D array,
     * - assigns the right colors to the squares,
     * - assigns player's ID to appropriate squares.
     */
    public Board() {
        allSquaresOnBoard = new Square[Constants.NUMBER_OF_COLS.getValue()][Constants.NUMBER_OF_ROWS.getValue()];
        setSquaresAvailability();
        assignPlayerIDToSquares();
    }

    // Method that sets which squares are possible to put the pawn on them.
    private void setSquaresAvailability() {

        // variable that tells if you can move around this square during the game.
        boolean isAvailableForPawns;

        /*
         * variable that holds the number of square.
         * The numbering looks like this: 1 2 3 4 5 6 7 8
         *                                9 ...
         *                                ...
         *                                ...
         *                                ...
         *                                ...
         *                                ...
         *                                ...          64
         */
        int numberOfSquare = 0;

        for (int y = 0; y < Constants.NUMBER_OF_ROWS.getValue(); ++y) {
            for (int x = 0; x < Constants.NUMBER_OF_COLS.getValue(); ++x) {
                isAvailableForPawns = ((y + x) % 2) == 1 ? true : false;
                ++numberOfSquare;
                allSquaresOnBoard[y][x] = new Square(numberOfSquare, y, x, isAvailableForPawns);
            }
        }
    }

    /*
     * Method that assigns player's ID to appropriate squares.
     * The squares with pawns will get appropriate player ID.
     */
    private void assignPlayerIDToSquares() {
        for (int y = 0; y < Constants.NUMBER_OF_ROWS.getValue() - 5; ++y)
            for (int x = 0; x < Constants.NUMBER_OF_COLS.getValue(); ++x)
                if (allSquaresOnBoard[y][x].getIsAvailableForPawns())
                    allSquaresOnBoard[y][x].setPlayerID(Constants.PLAYER_ONE_ID.getValue());

        for (int y = 5; y < Constants.NUMBER_OF_ROWS.getValue(); ++y)
            for (int x = 0; x < Constants.NUMBER_OF_COLS.getValue(); ++x)
                if (allSquaresOnBoard[y][x].getIsAvailableForPawns())
                    allSquaresOnBoard[y][x].setPlayerID(Constants.PLAYER_TWO_ID.getValue());
    }

    /**
     * Method to get the total number of squares on board.
     * @return Total number of squares on board.
     */
    public int getNumberOfSquaresOnBoard() {
        return allSquaresOnBoard.length;
    }

    /**
     * Method to get the 2D array of squares on board.
     * @return 2D array that contain all squares on board.
     */
    public Square[][] getAllSquaresOnBoard() {
        return this.allSquaresOnBoard;
    }

    /*
     * Method that adds front squares to the list of the squares possible to move if there are any options.
     *
     * @param list                       List of squares. If there are more possible front squares, they will be added to the list.
     * @param rowThatCanBePossibleToMove Row that indicates the square that can be possible to move.
     * @param selectedCol                Column for which we check if there are front squares possible to move.
     */
    private void addIfAnyOfTwoFrontSquaresPossibleToMove(LinkedList<Square> list, int rowThatCanBePossibleToMove, int selectedCol) {
        // we need to check if we do not go outside the board
        if (rowThatCanBePossibleToMove >= 0 && rowThatCanBePossibleToMove < Constants.NUMBER_OF_ROWS.getValue()) {
			/* if we have the right corner (we are not in the last column on the right)
			 the right corner will be the bottom or top depending on the row that can be possible to move
			 we set up earlier in the returnSquaresPossibleToMove function
			 */
            if (selectedCol >= 0 && selectedCol < Constants.NUMBER_OF_COLS.getValue() - 1) {
                // we take the square from the appropriate right corner
                Square rightCorner = allSquaresOnBoard[rowThatCanBePossibleToMove][selectedCol + 1];
				/* we need to check if there is any pawn that belongs to the other player
				 if there is no pawn (PLAYER ID equals 0 - it means that the square is empty
				 we set the flag and add the square to the list of squares possible to move
				 */
                if (rightCorner.getPlayerID() == Constants.SQUARE_NOT_OCCUPIED.getValue()) {
                    rightCorner.setIsPossibleToMove(true);
                    list.add(rightCorner);
                }
            }

            // identically for the left corner
            if (selectedCol > 0 && selectedCol <= Constants.NUMBER_OF_COLS.getValue()) {
                Square leftCorner = allSquaresOnBoard[rowThatCanBePossibleToMove][selectedCol - 1];
                if (leftCorner.getPlayerID() == Constants.SQUARE_NOT_OCCUPIED.getValue()) {
                    leftCorner.setIsPossibleToMove(true);
                    list.add(leftCorner);
                }
            }
        }
    }

    /*
     * Method that adds the squares, in which our pawn will be if we do single cross jump, to the list
     * of squares possible to move.
     * @param list                       List of the squares possible to move.
     * @param rowThatCanBePossibleToMove Row that indicates the square in which our pawn will be if we do cross jump.
     * @param selectedCol                Column for which we check if there are cross jumps possible to do.
     * @param middleRow                  Row between the starting row and the row in which we will be after single cross jump.
     */
    private void addIfCrossJumpPossible(LinkedList<Square> list, int rowThatCanBePossibleToMove, int selectedCol, int middleRow) {
        // we need it to determine the position of the pawn we want to beat
        int middleCol;

        // right corner
        // we need to check if we do not go outside the board
        if (rowThatCanBePossibleToMove >= 0 && rowThatCanBePossibleToMove < Constants.NUMBER_OF_COLS.getValue()) {
            if (selectedCol >= 0 && selectedCol < Constants.NUMBER_OF_COLS.getValue() - 2) {
                // we take the square in which we will be after cross jump
                Square rightCorner = allSquaresOnBoard[rowThatCanBePossibleToMove][selectedCol + 2];
                middleCol = (selectedCol + selectedCol + 2) / 2;

                // if cross jump is possible (there is pawn to beat and there is place behind it)
                if (rightCorner.getPlayerID() == Constants.SQUARE_NOT_OCCUPIED.getValue() && opponentToBeatAvailable(middleRow, middleCol)) {
                    rightCorner.setIsPossibleToMove(true);
                    list.add(rightCorner);
                }
            }

            //left corner
            // identically like for the right corner
            if (selectedCol >= 2 && selectedCol < Constants.NUMBER_OF_COLS.getValue()) {
                Square leftCorner = allSquaresOnBoard[rowThatCanBePossibleToMove][selectedCol - 2];
                middleCol = (selectedCol + selectedCol - 2) / 2;
                if (leftCorner.getPlayerID() == Constants.SQUARE_NOT_OCCUPIED.getValue() && opponentToBeatAvailable(middleRow, middleCol)) {
                    leftCorner.setIsPossibleToMove(true);
                    list.add(leftCorner);
                }
            }
        }
    }

    /*
     * Method that tells if there is opponent to make cross jump available.
     * @param row Row for which we want to check if there is opponent's pawn.
     * @param col Column for which we want to check if there is opponent's pawn.
     * @return true - there is opponent to beat, false - there is not opponent to beat.
     */
    private boolean opponentToBeatAvailable(int row, int col) {
        return allSquaresOnBoard[row][col].doesTheSquareBelongToTheOpponent();
    }

    /**
     * Method that returns list of squares the player is allowed to choose to move the pawn from selected square.
     * @param selectedSquare The square for which we want to get the list of possible squares to move our pawn.
     * @return The list of squares that are possible to choose to move the pawn from selected square.
     */
    public LinkedList<Square> returnSquaresPossibleToMove(Square selectedSquare) {
        LinkedList<Square> squaresPossibleToMove = new LinkedList<Square>();

        int actualRow = selectedSquare.getRowNumber();
        int actualCol = selectedSquare.getColNumber();

        // depending on which player we are, we choose the appropriate row to which we want to move
        int rowThatCanBePossibleToMove;
        if (selectedSquare.getPlayerID() == Constants.PLAYER_ONE_ID.getValue())
            rowThatCanBePossibleToMove = actualRow + 1;
        else
            rowThatCanBePossibleToMove = actualRow - 1;

        addIfAnyOfTwoFrontSquaresPossibleToMove(squaresPossibleToMove, rowThatCanBePossibleToMove, actualCol);

        int newRowThatCanBePossibleToMove;
        if (selectedSquare.getPlayerID() == Constants.PLAYER_ONE_ID.getValue())
            newRowThatCanBePossibleToMove = rowThatCanBePossibleToMove + 1;
        else
            newRowThatCanBePossibleToMove = rowThatCanBePossibleToMove - 1;

        // cross jump in the direction of the move of pawns
        addIfCrossJumpPossible(squaresPossibleToMove, newRowThatCanBePossibleToMove, actualCol, rowThatCanBePossibleToMove);

        // cross jump in the opposite direction of the move of pawns
        if (selectedSquare.getPlayerID() == Constants.PLAYER_ONE_ID.getValue()) {
            rowThatCanBePossibleToMove = actualRow - 1;
            newRowThatCanBePossibleToMove = rowThatCanBePossibleToMove - 1;
        }
        else {
            rowThatCanBePossibleToMove = actualRow + 1;
            newRowThatCanBePossibleToMove = rowThatCanBePossibleToMove + 1;
        }

        addIfCrossJumpPossible(squaresPossibleToMove, newRowThatCanBePossibleToMove, actualCol, rowThatCanBePossibleToMove);

		/* we also need to consider the case when we are dealing with a king because the king
		can move backwards as well
		 */
        if (selectedSquare.isKing())
            addIfAnyOfTwoFrontSquaresPossibleToMove(squaresPossibleToMove, rowThatCanBePossibleToMove, actualCol);

        return squaresPossibleToMove;
    }
}
