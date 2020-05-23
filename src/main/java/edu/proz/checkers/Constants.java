package edu.proz.checkers;

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
    SQUARE_NOT_OCCUPIED(0);

    // number that indicates which constant it is.
    private int value;


    // private constructor. We do not want to let someone create more constants in code.
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
