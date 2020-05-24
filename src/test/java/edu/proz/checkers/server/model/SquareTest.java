package edu.proz.checkers.server.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class SquareTest {
	@Test
	void testSquare() {
		
		Square square = new Square( 1, 2, 3 , 4 );
		assertEquals(3,square.getPlayerId() );
		assertEquals(1,square.getRow() );
		assertEquals(2,square.getColumn() );
		assertEquals(4,square.getIndex() );
		
	}
}
