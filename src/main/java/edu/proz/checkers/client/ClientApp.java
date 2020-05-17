package edu.proz.checkers.client;

import edu.proz.checkers.client.controller.GameController;
import edu.proz.checkers.client.infrastructure.ConnectionController;
import edu.proz.checkers.client.model.Player;

public class ClientApp {
	
	private int port;
	Player player;
	
	public ClientApp() {
		
		port = 9999;
		init();
	}

	private void init() {
		
		
		//---------
		GameController gc = new GameController( player );
		ConnectionController cc = new ConnectionController( gc.getRequestQueue(), gc.getResponseQueue(), port );
		try {
			gc.makeStart();
			cc.establishConnection();
			cc.processRequest();
			gc.processResponse();
		}catch(Exception e ) {
			
		}
		
		setListeners(gc);
		new Thread( cc ).start();
		new Thread( gc ).start();
		
	}
	
	
	private void setListeners(GameController gc) {
	
	}
	
}
