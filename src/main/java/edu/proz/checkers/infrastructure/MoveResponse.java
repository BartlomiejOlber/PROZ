package edu.proz.checkers.infrastructure;

/**
 * Response subclass. Sent by server after receiving an object of Move type.
 * <img src="doc-files/edu.proz.checkers.infrastructure.png" alt="Messages">
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
