package edu.proz.checkers.infrastructure;

/**
 *  Response subclass. Sent to the client as response for the initial message. Contains player ID assigned by the server side.
 * @author bartlomiej
 * @see Response
 */
public class StartResponse extends Response {

	public StartResponse(int playerId) {
		setPlayerId(playerId);
		// TODO Auto-generated constructor stub
	}
	
	public StartResponse() {}

}
