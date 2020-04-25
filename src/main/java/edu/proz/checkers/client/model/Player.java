package edu.proz.checkers.client.model;

import edu.proz.checkers.client.PlayerIDHolder;

/**
 * Class that represents the player in model and holds important player's data.
 */
public class Player {
    /**
     * Variable that holds player's ID.
     */
    private int ID;

    /**
     * Variable that holds player's name.
     */
    private String name;

    /**
     * Variable that indicates if now is particular player's turn.
     */
    private boolean isMyTurn;


    public Player(String name) {
        this.name = name;
		/* at the beginning when the player joins and the second one has not joined, we set the turn false so that
		   he cannot move. Only when 2 players have joined we set the turn true for player number 1 using the
		   setMyTurn () function
		 */
        setIsMyTurn(false);
    }

    /**
     * Function that returns player's ID.
     * @return Player's ID.
     */
    public int getID() {
        return ID;
    }

    /**
     * Function that sets player's ID to the appropriate value and also saves this ID in holder.
     * @param ID Player's id.
     */
    public void setID(int ID) {
        this.ID = ID;
        PlayerIDHolder.PLAYER_ID.setValue(ID);
    }


    /**
     * Function that returns player's name.
     * @return Player's name.
     */
    public String getName(){
        return this.name;
    }

    /**
     * Function that returns true if it is player's turn now and otherwise false.
     * @return Boolean value that indicates if it is player's turn now.
     */
    public boolean getIsMyTurn() {
        return isMyTurn;
    }

    /**
     * Function that sets the player's turn true or false.
     * @param isMyTurn Boolean value that will be assigned to isMyTurn variable.
     */
    public void setIsMyTurn(boolean isMyTurn) {
        this.isMyTurn = isMyTurn;
    }
}

