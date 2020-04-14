package edu.proz.checkers.client.model;

/**
 * Class that contains all the constant values used in model.
 */
public enum Constants {
    /**
     * Constant value that represents number of rows in our board.
     */
    NUMBER_OF_ROWS(8),

    /**
     * Constant value that represents number of columns in our board.
     */
    NUMBER_OF_COLS(8),

    /**
     * Constant value that represents ID of player number 1.
     */
    PLAYER_ONE_ID(1),

    /**
     * Constant value that represents ID of player number 2.
     */
    PLAYER_TWO_ID(2),

    /**
     * It means that there is no pawn, that belongs to any player, on this square.
     */
    SQUARE_NOT_OCCUPIED(0),

    /**
     * Constant that indicates that someone has won.
     */
    YOU_WON_THE_GAME(1000),

    /**
     * Constant that indicates that someone has lost.
     */
    YOU_LOST_THE_GAME(2000);

    /**
     * Number that indicates which constant it is.
     */
    private int value;

    /**
     * Private constructor. We do not want to let someone create more constants in code.
     * @param value Number that indicates which constant it is.
     */
    private Constants(int value) {
        this.value = value;
    }

    /**
     * Function to get the number that indicates which constant it is.
     * @return Number that indicates which constant it is.
     */
    public int getValue() {
        return this.value;
    }
}
