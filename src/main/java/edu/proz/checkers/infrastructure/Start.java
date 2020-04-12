package edu.proz.checkers.infrastructure;

public class Start extends Request {
	
	private String myName;
	
	public Start( String name ){
		this.myName = name;
	}
	public String getName() {
		return myName;
	}

}
