package edu.proz.checkers.infrastructure;

public class Response extends Message {

	private int id;  //optionally enum field for some types of response
	private int moveStart;
	private int moveEnd;
	
	public Response( int id, int moveStart, int moveEnd ){
		this.id = id;
		this.moveStart = moveStart;
		this.moveEnd = moveEnd;
	}
	
	public int getMoveStart() {
		return moveStart;
	}
	public void setMoveStart( int moveStart ) {
		this.moveStart = moveStart;
	}
	
	public int getMoveEnd() {
		return moveEnd;
	}
	public void setMoveEnd( int moveEnd ) {
		this.moveEnd = moveEnd;
	}

	
	public int getId() {
		return id;
	}
	public void setId( int id ) {
		this.id = id;
	}
}
