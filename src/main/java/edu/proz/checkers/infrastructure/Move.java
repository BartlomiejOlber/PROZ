package edu.proz.checkers.infrastructure;


//transfer data object
public class Move {
	private final int from;
	private final int to;
	public Move( int from, int to ) {
		this.from = from;
		this.to = to;
	}
	public int getFrom() {
		return from;
	}
	public int getTo() {
		return to;
	}
	
}
