package edu.proz.checkers.client.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.concurrent.BlockingQueue;

import org.junit.jupiter.api.Test;

import edu.proz.checkers.client.model.Player;
import edu.proz.checkers.infrastructure.Request;
import edu.proz.checkers.infrastructure.Start;

public class GameControllerTest {

	@Test
	void testInit( ) {
		Player player = new Player("name");
		GameController gc = new GameController(player);
		assertEquals(16, gc.getRequestQueue().remainingCapacity());
		assertEquals(16, gc.getResponseQueue().remainingCapacity());
	}
	
	@Test
	void makeStopTest( ) {
		Player player = new Player("name");
		GameController gc = new GameController(player);
		BlockingQueue<Request> requestQueue = gc.getRequestQueue();
		assertEquals(true, requestQueue.isEmpty());
		gc.makeStart();
		assertEquals(false, requestQueue.isEmpty());
		Request request = requestQueue.poll();
		assertEquals(Start.class, request.getClass());
		assertEquals(0, request.getPlayerId());
	}
}
