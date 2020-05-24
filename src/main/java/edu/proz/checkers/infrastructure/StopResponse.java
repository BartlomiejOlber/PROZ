package edu.proz.checkers.infrastructure;

/**
 *  Response subclass. Sent to a client when his opponent has left the game.
 * @author bartlomiej
 * @see Response
 */
public class StopResponse extends Response {

	public StopResponse(int playerId) {
		setPlayerId(playerId);
		// TODO Auto-generated constructor stub
	}
	
	public StopResponse() {}
}
