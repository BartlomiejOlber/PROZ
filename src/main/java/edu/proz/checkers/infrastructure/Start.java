package edu.proz.checkers.infrastructure;

/**
 *Request subclass. Sent to server as the initial message after connecting.
 * @author bartlomiej
 *@see Request
 */
public class Start extends Request {
	
	public Start(int playerId) {
		setPlayerId(playerId);
		// TODO Auto-generated constructor stub
	}

	public Start() {}

}
