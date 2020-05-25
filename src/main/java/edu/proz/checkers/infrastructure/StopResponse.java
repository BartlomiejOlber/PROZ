package edu.proz.checkers.infrastructure;

/**
 *  Response subclass. Sent to a client when his opponent has left the game.
 *  <img src="doc-files/edu.proz.checkers.infrastructure.png" alt="Messages">
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
