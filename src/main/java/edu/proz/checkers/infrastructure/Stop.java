package edu.proz.checkers.infrastructure;

/**
 *Request subclass. Sent to server as the message indicating leaving the game.
 *<img src="doc-files/edu.proz.checkers.infrastructure.png" alt="Messages">
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
