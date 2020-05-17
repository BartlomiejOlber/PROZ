package edu.proz.checkers.infrastructure;

public class GetOpponentEvent extends Request {

	public GetOpponentEvent(int playerId) {
		setPlayerId(playerId);

	}

	public GetOpponentEvent() {
		// TODO Auto-generated constructor stub
	}
}
