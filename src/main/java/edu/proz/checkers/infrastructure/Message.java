package edu.proz.checkers.infrastructure;

/**
 * Abstract Message class. All messages are ment to contain ID of the player that sent/receives the message
 * the information is used for the server side purposes.
 * @author bartlomiej
 *
 */
public abstract class Message {
	
	private int playerId;
	public int getPlayerId() {
		return playerId;
	}
	public void setPlayerId(int playerId) {
		this.playerId = playerId;
	}
	
}
