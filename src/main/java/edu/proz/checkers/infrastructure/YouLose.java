package edu.proz.checkers.infrastructure;

public class YouLose extends Response {

	public YouLose(int playerId, int from, int to) {
		super(playerId);
		this.from = from;
		this.to = to;
	}
	
	private int from;
	private int to;
	

	public int getFrom() {
		return from;
	}

	public void setFrom(int from) {
		this.from = from;
	}

	public int getTo() {
		return to;
	}

	public void setTo(int to) {
		this.to = to;
	}

}
