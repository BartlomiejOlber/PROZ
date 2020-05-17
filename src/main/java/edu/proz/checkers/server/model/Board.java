package edu.proz.checkers.server.model;

public class Board {
	
	private static final int SIZE = 8;
	private Square[][] board;
	
	public Board(){
		board = new Square[SIZE][SIZE];
		
		initBoard();
	}
	
	private void initBoard() {
		
		int count = 0;	
		int playerId = 0;
		for(int row=0;row<SIZE;row++){	
			for(int column=0;column<SIZE;column++){
				playerId = ((count% 2 == 1) && (row < 3)) ? 1  : 0 ;
				playerId = ((count% 2 == 1) && (row > 4)) ? 2  : playerId ;
				board[row][column] = new Square( row, column, playerId, count);
				count++;
			}
		}	
	}
	
	public Square getSquare( int index ) {
		
		return board[index/SIZE][index%SIZE];
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
