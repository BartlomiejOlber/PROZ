package edu.proz.checkers.client;

/**
 * Class to keep player ID in order to be able to recognize what belongs to which player or what is empty.
 */
public enum PlayerIDHolder {
    /**
     * Constant that holds player's ID.
     */
    PLAYER_ID(0);

    // value of player's ID.
    private int value;

    private PlayerIDHolder(int value) {
        this.setValue(value);
    }

    /**
     * Method to get player's ID.
     * @return Player's ID.
     */
    public int getValue() {
        return value;
    }

    /**
     * Method to set player's ID.
     * @param value Player's ID.
     */
    public void setValue(int value) {
        this.value = value;
    }
}



