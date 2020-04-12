package edu.proz.checkers.infrastructure;


//transfer data object
public class Move extends Request {
	//private String operation;
	private int from;
	private int to;
	
	public Move() {
	}
	
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
