package edu.proz.checkers.client;

import edu.proz.checkers.client.controller.GameController;
import edu.proz.checkers.client.infrastructure.ConnectionController;
import edu.proz.checkers.client.model.Player;

public class ClientApp {
	
	private int port;
	
	public ClientApp() {
		
		port = 9999;
		init();
	}

	private void init() {
		Player player = null;
		
		//---------
		GameController gc = new GameController( player );
		ConnectionController cc = new ConnectionController( gc.getRequestQueue(), gc.getResponseQueue(), port );
		//setup(gc);
		new Thread( cc ).start();
		new Thread( gc ).start();
		
	}
	
	/*
	private void setup(Controller c) {
		MyMouseListener listener = new MyMouseListener();
		listener.setController(c);

		boardPanel = new BoardPanel(listener);
		c.setBoardPanel(boardPanel);
		add(boardPanel);
	}
	*/
}
