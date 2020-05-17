package edu.proz.checkers.infrastructure;


//transfer data object
public class Move extends Request {
	
	private int from;
	private int to;
	

	public Move() {}
	
	public int getFrom() {
		return from;
	}
	public int getTo() {
		return to;
	}
	
}
