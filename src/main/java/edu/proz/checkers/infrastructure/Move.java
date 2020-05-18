package edu.proz.checkers.infrastructure;


//transfer data object
public class Move extends Request {
	
	private int from;
	private int to;
	
	public Move(int playerId, int from, int to) {
		setPlayerId(playerId);
		this.from = from;
		this.to = to;
	}

	public Move() {}
	
	public int getFrom() {
		return from;
	}
	public int getTo() {
		return to;
	}
	
}
