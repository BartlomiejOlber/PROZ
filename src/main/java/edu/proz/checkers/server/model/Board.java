package edu.proz.checkers.server.model;


public class Board {
	
	private static final int[] PLAYER_ONE_TABLE = {1, 3, 5, 7, 8, 10 ,12, 14, 17, 19, 21, 23 };
	private static final int[] PLAYER_TWO_TABLE = {40, 42, 44, 46, 49, 51 ,53, 55, 56, 58, 60, 62  };
	
	private static final int SIZE = 8;
	private Square[][] board;
	
	public Board(){
		board = new Square[SIZE][SIZE];
		
		initBoard();
	}
	
	private void initBoard() {
		
		int count = 1;	
		for(int row=0;row<SIZE;row++){	
			for(int column=0;column<SIZE;column++){
				board[row][column] = new Square( row, column, 0, count);
				System.out.print("\nBOARD:" + row + column + 0 + count);
				count++;
			}
		}	
		assignSquares();
	}
	
	private void assignSquares() {
		
		for (int i : PLAYER_ONE_TABLE) {
			board[i/SIZE][i%SIZE].setPlayerId(1);
		}
		
		for (int i : PLAYER_TWO_TABLE) {
			board[i/SIZE][i%SIZE].setPlayerId(2);
		}
		
	}
	
	public Square getSquare( int index ) {
		
		return board[(index - 1)/SIZE][(index - 1)%SIZE];
	}

	
	public boolean isOn(){
		
		boolean playerOne = false;
		boolean playerTwo = false;
		for(int r=0;r<SIZE;r++){
			for(int c=0;c<SIZE;c++){
				if(board[r][c].getPlayerId()==1)
					playerOne = true;			
				if(board[r][c].getPlayerId()==2)
					playerTwo = true;
			}
		}
		return playerOne && playerTwo;
	}

}
