package edu.proz.checkers.infrastructure;
/**
 *  Response subclass. Sent to the client as a response to his move after winning the game.
 *  <img src="doc-files/edu.proz.checkers.infrastructure.png" alt="Messages">
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
