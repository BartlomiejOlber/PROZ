package edu.proz.checkers.infrastructure;

/**
 *  Response subclass. Sent to the client asking for his opponent's move after the opponent has made one. Contains 
 *  indexes describing the move.
 *  <img src="doc-files/edu.proz.checkers.infrastructure.png" alt="Messages">
 * @author bartlomiej
 * @see Response
 */
public class OpponentMovedResponse extends Response {

	private int from;
	private int to;
	
	public OpponentMovedResponse() {}
	
	public OpponentMovedResponse(int playerId, int from, int to) {
		setPlayerId(playerId);
		this.from = from;
		this.to = to;
	}

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
