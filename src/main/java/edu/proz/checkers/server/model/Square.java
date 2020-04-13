package edu.proz.checkers.server.model;

public class Square {

	private int row;
	private int column;	
	private int playerId;
	private int index;
	
	public Square(int row, int column, int playerId, int index) {
		
		this.row = row;
		this.column = column;
		this.playerId = playerId;
		this.index = index;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getColumn() {
		return column;
	}

	public void setColumn(int column) {
		this.column = column;
	}

	public int getPlayerId() {
		return playerId;
	}

	public void setPlayerId(int playerId) {
		this.playerId = playerId;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}
		
	
}
