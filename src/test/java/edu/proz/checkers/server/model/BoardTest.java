package edu.proz.checkers.server.model;

import  edu.proz.checkers.Constants;
import static org.junit.jupiter.api.Assertions.assertEquals;

import edu.proz.checkers.infrastructure.*;
import org.junit.jupiter.api.Test;



public class BoardTest {

	//private static final int[] PLAYER_ONE_TABLE = {1, 3, 5, 7, 8, 10 ,12, 14, 17, 19, 21, 23 };
	private static final int[] PLAYER_TWO_TABLE = {40, 42, 44, 46, 49, 51 ,53, 55, 56, 58, 60, 62  };
	@Test
	void testInit() {
		Board board = new Board();
		assertEquals( true ,board.isOn());
		assertEquals( Constants.PLAYER_ONE_ID.getValue(), board.getSquare(2).getPlayerId());
		assertEquals( Constants.PLAYER_TWO_ID.getValue(), board.getSquare(41).getPlayerId());
	}
	
	@Test
	void testEnd() {
		
		Board board = new Board();
		assertEquals( true, board.isOn());
		for (int i : PLAYER_TWO_TABLE) {
			board.getSquare(i+1).setPlayerId(Constants.SQUARE_NOT_OCCUPIED.getValue());
		}
		assertEquals(false, board.isOn());
	}
	

}