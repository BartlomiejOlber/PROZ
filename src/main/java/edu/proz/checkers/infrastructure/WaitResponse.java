package edu.proz.checkers.infrastructure;

/**
 *  Response subclass. Sent to the client asking for his opponent's move to continue waiting, because his opponent has not moved yet.
 *  
 * @author bartlomiej
 * @see Response
 */
public class WaitResponse extends Response {

	public WaitResponse(int playerId) {
		setPlayerId(playerId);
		// TODO Auto-generated constructor stub
	}
	
	public WaitResponse() {}

}
