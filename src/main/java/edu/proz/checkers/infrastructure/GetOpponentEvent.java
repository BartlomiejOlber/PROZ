package edu.proz.checkers.infrastructure;

/**
 * Request subclass sent by a client awaiting his opponent's move
 * @author bartlomiej
 *@see Request
 */
public class GetOpponentEvent extends Request {

	public GetOpponentEvent(int playerId) {
		setPlayerId(playerId);

	}

	public GetOpponentEvent() {
		// TODO Auto-generated constructor stub
	}
}
