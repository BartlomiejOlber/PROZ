package edu.proz.checkers.client.model;

/**
 * Class that represents the whole board in model.
 */
public class Board {
    /**
     * 2D array that holds all the squares the board consists of.
     */
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

    /**
     * Function that sets which squares are possible to put the pawn on them.
     */
    private void setSquaresAvailability() {
        /**
         * Variable that tells if you can move around this square during the game.
         */
        boolean isAvailableForPawns;

        /**
         * Variable that holds the number of square.
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

    /**
     * Function that assigns player's ID to appropriate squares.
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
}