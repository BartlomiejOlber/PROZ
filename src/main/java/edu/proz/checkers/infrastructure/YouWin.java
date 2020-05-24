package edu.proz.checkers.infrastructure;
/**
 *  Response subclass. Sent to the client as a response to his move after winning the game.
 * @author bartlomiej
 * @see Response
 */
public class YouWin extends Response {

	public YouWin(int playerId) {
		setPlayerId(playerId);
	}
	
	public YouWin() {
		// TODO Auto-generated constructor stub
	}

}
