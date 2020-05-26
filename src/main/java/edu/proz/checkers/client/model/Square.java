package edu.proz.checkers.client.model;

import edu.proz.checkers.Constants;
import edu.proz.checkers.client.PlayerIDHolder;
/**
 * Class that represents single square on the board.
 */
public class Square {

    // square number on the board.
    private int ID;

    // ID of player to whom the pawn on this square belongs.
    private int playerID;

    // number of row in which the square is.
    private int rowNumber;

    // number of column in which the square is.
    private int colNumber;

    // boolean value that indicates if it is a type of square on which we can put pawns.
    private boolean isAvailableForPawns;

    // boolean value that indicates if we clicked mouse on this square.
    private boolean isSelected;

    // boolean value that indicates if we can put our pawn on this square.
    private boolean isPossibleToMove;

    // boolean value that indicated if there is a king on the square.
    private boolean isKing;

    /**
     * Constructor.
     * @param ID Square number on the board.
     * @param rowNumber Number of row in which the square is.
     * @param colNumber Number of column in which the square is.
     * @param isAvailableForPawns Boolean value that indicates if it is a type of square on which we can put pawns.
     */
    public Square(int ID, int rowNumber, int colNumber, boolean isAvailableForPawns) {
        this.ID = ID;
        this.rowNumber = rowNumber;
        this.colNumber = colNumber;
        this.setIsAvailableForPawns(isAvailableForPawns);

		/* on the beginning, if the square is available for pawns, we assign player ID 0 to the square
		   and it means that the square does not belong to any user
		 */
        if (this.isAvailableForPawns)
            this.playerID = Constants.SQUARE_NOT_OCCUPIED.getValue();

        this.isSelected = false;
        this.isPossibleToMove = false;
        this.isKing = false;
    }

    /**
     * Method that returns square number on the board.
     * @return Square number on the board.
     */
    public int getID() {
        return this.ID;
    }

    /**
     * Method that returns ID of player to whom the pawn on this square belongs.
     * @return ID of player to whom the pawn on this square belongs.
     */
    public int getPlayerID() {
        return this.playerID;
    }

    /**
     * Method that sets ID of player to whom the pawn on this square belongs.
     * @param ID ID of player to whom the pawn on this square belongs.
     */
    public void setPlayerID(int ID) {
        this.playerID = ID;
    }

    /**
     * Method that returns the number of row in which the square is.
     * @return Number of row in which the square is.
     */
    public int getRowNumber() {
        return this.rowNumber;
    }

    /**
     * Method that returns the number of column in which the square is.
     * @return Number of column in which the square is.
     */
    public int getColNumber() {
        return this.colNumber;
    }

    /**
     * Method that returns boolean value that indicates if the square is available to put pawns on it.
     * @return Boolean value that indicates if it is a type of square on which we can put pawns.
     */
    public boolean getIsAvailableForPawns() {
        return isAvailableForPawns;
    }

    /*
     * Method that sets boolean value that indicates if the square is available to put pawns on it.
     * @param isAvailableForPawns Boolean value that indicates if it is a type of square on which we can put pawns.
     */
    private void setIsAvailableForPawns(boolean isAvailableForPawns) {
        this.isAvailableForPawns = isAvailableForPawns;
    }

    /**
     * Method that returns boolean value that indicates if we clicked mouse on this square.
     * @return Boolean value that indicates if we clicked mouse on this square.
     */
    public boolean getIsSelected() {
        return isSelected;
    }

    /**
     * Method that sets boolean value that indicates if we clicked mouse on this square.
     * @param isSelected Boolean value that indicates if we clicked mouse on this square.
     */
    public void setIsSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    /**
     * Method that returns boolean value that indicates if we can put our pawn on this square.
     * @return Boolean value that indicates if we can put our pawn on this square.
     */
    public boolean getIsPossibleToMove() {
        return isPossibleToMove;
    }

    /**
     * Method that sets boolean value that indicates if we can put our pawn on this square.
     * @param isPossibleToMove Boolean value that indicates if we can put our pawn on this square.
     */
    public void setIsPossibleToMove(boolean isPossibleToMove) {
        this.isPossibleToMove = isPossibleToMove;
    }

    /**
     * Method that returns boolean value that indicates if there is a king on the square.
     * @return Boolean value that indicated if there is a king on the square.
     */
    public boolean isKing() {
        return isKing;
    }

    /**
     * Method that sets boolean value, that indicates if there is a king on the square, true.
     */
    public void setKing() {
        this.isKing = true;
    }

    /**
     * Method that sets boolean value, that indicates if there is a king on the square, false.
     */
    public void removeKing() {
        this.isKing = false;
    }

    /**
     * Method that returns boolean value that indicates if the square belongs to player's opponent.
     * @return Boolean value that indicates if the square belongs to player's opponent.
     */
    public boolean doesTheSquareBelongToTheOpponent() {
        if (playerID != Constants.SQUARE_NOT_OCCUPIED.getValue() && playerID != PlayerIDHolder.PLAYER_ID.getValue())
            return true;
        else
            return false;
    }
}


