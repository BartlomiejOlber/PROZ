package edu.proz.checkers.client;

import javax.swing.JFrame;

import edu.proz.checkers.client.controller.GameController;
import edu.proz.checkers.client.infrastructure.ConnectionController;
import edu.proz.checkers.client.model.Player;
import edu.proz.checkers.infrastructure.ConfigParams;

public class ClientApp extends JFrame {
	
	private static final long serialVersionUID = 1L;
	Player player;
	
	public ClientApp( ConfigParams params ) {
		init( params );
	}

	private void init( ConfigParams params ) {
		
		
		//---------
		GameController gc = new GameController( player );
		ConnectionController cc = new ConnectionController( gc.getRequestQueue(), gc.getResponseQueue(), params );
		try {
			gc.makeStart();
			cc.establishConnection();
			cc.processRequest();
			gc.processResponse();
		}catch(Exception e ) {
			System.err.print("unable to start game");
			e.printStackTrace();
		}
		
		setListeners(gc);
		new Thread( cc ).start();
		new Thread( gc ).start();
		
	}
	
	
	private void setListeners(GameController gc) {
	
	}
	
}
