package edu.proz.checkers.infrastructure;


/**
 *Request subclass. Sent to server after a client has made a checkers move. Contains indexes of the square the move started from
 *and the square the move finished on 
 *<img src="doc-files/edu.proz.checkers.infrastructure.png" alt="Messages">
 * @author bartlomiej
 *@see Request
 */
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
