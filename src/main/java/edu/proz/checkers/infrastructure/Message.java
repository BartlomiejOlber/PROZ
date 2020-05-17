package edu.proz.checkers.infrastructure;

public abstract class Message {
	
	private int playerId;
	public int getPlayerId() {
		return playerId;
	}
	public void setPlayerId(int playerId) {
		this.playerId = playerId;
	}
	
}
