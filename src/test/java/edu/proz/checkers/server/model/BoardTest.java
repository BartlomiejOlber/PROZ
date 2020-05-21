package edu.proz.checkers.server.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class BoardTest {

	@Test
	void testMove() {
		Move move = new Move( 1, 2, 3 );
		assertEquals( 1 ,move.getPlayerId());
		assertEquals( 2 ,move.getFrom());
		assertEquals( 3 ,move.getTo());
	}
	
	@Test
	void testRequest() {
		
		Request request = new GetOpponentEvent( 1 );
		assertEquals( 1 ,request.getPlayerId());
		
		assertEquals( GetOpponentEvent.class, request.getClass());		

	}
	
	@Test
	void testResponse() {
		Response response = new YouWin( 2 );
		assertEquals( 2 ,response.getPlayerId());
		assertEquals( YouWin.class, response.getClass());
	}
}