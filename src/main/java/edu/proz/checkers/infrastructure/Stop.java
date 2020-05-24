package edu.proz.checkers.infrastructure;

/**
 *Request subclass. Sent to server as the message indicating leaving the game.
 * @author bartlomiej
 *@see Request
 */
public class Stop extends Request {

	public Stop(int playerId) {
		setPlayerId(playerId);
		// TODO Auto-generated constructor stub
	}
	
	public Stop() {}
}
