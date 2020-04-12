package edu.proz.checkers.client;

import edu.proz.checkers.client.controller.GameController;
import edu.proz.checkers.client.infrastructure.ConnectionController;

public class TestQueue {
	
	public void start() {
		
		GameController gameController = new GameController();
		ConnectionController connectionController = new ConnectionController(gameController.getRequestQueue(),
				gameController.getResponseQueue());
		
	}
}
