package edu.proz.checkers.infrastructure;

/**
 * Response subclass. Sent by server after receiving an object of Move type.
 * @author bartlomiej
 *@see Response
 */
public class MoveResponse extends Response {

	public MoveResponse(int playerId) {
		setPlayerId(playerId);
		// TODO Auto-generated constructor stub
	}

	public MoveResponse() {}

}
