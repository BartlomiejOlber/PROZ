package edu.proz.checkers.server.model;

import static edu.proz.checkers.Constants.*;

/**
 * Model checkers board. 8x8 Board that holds information of current game state.
 * 
 * @author bartlomiej
 *
 */
public class Board {
	
	private final int[] PLAYER_ONE_TABLE = {1, 3, 5, 7, 8, 10 ,12, 14, 17, 19, 21, 23 };
	private final int[] PLAYER_TWO_TABLE = {40, 42, 44, 46, 49, 51 ,53, 55, 56, 58, 60, 62  };
	
	private Square[][] board;
	
	/**
	 * Initializes the board; assigns internal squares to players according to checkers logic
	 */
	public Board(){
		board = new Square[NUMBER_OF_ROWS.getValue()][NUMBER_OF_COLS.getValue()];
		
		initBoard();
	}
	
	private void initBoard() {
		
		int count = 1;	
		for(int row=0;row<NUMBER_OF_ROWS.getValue();row++){	
			for(int column=0;column<NUMBER_OF_COLS.getValue();column++){
				board[row][column] = new Square( row, column, SQUARE_NOT_OCCUPIED.getValue(), count);
				count++;
			}
		}	
		assignSquares();
	}
	
	private void assignSquares() {
		
		for (int i : PLAYER_ONE_TABLE) {
			board[i/NUMBER_OF_ROWS.getValue()][i%NUMBER_OF_COLS.getValue()].setPlayerId(PLAYER_ONE_ID.getValue());
		}
		
		for (int i : PLAYER_TWO_TABLE) {
			board[i/NUMBER_OF_ROWS.getValue()][i%NUMBER_OF_COLS.getValue()].setPlayerId(PLAYER_TWO_ID.getValue());
		}
		
	}
	
	/**Square of given index getter
	 * @param index square index
	 * @return square 
	 */
	public Square getSquare( int index ) {
		
		return board[(index - 1)/NUMBER_OF_ROWS.getValue()][(index - 1)%NUMBER_OF_COLS.getValue()];
	}

	
	/**
	 * Checks whether there are still both players' pawns on the board
	 * @return boolean indicating if the game is on or over
	 */
	public boolean isOn(){
		
		boolean playerOne = false;
		boolean playerTwo = false;
		for(int r=0;r<NUMBER_OF_ROWS.getValue();r++){
			for(int c=0;c<NUMBER_OF_COLS.getValue();c++){
				if(board[r][c].getPlayerId()==PLAYER_ONE_ID.getValue())
					playerOne = true;			
				if(board[r][c].getPlayerId()==PLAYER_TWO_ID.getValue())
					playerTwo = true;
			}
		}
		return playerOne && playerTwo;
	}

}
