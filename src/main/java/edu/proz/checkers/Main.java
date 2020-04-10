package edu.proz.checkers;

import edu.proz.checkers.client.*;
import edu.proz.checkers.server.*;


public class Main {

	public static void main(String[] args) {
		System.out.println("Hello, World");
		System.out.printf("%s\n",args[0]);
		
		if( args[0].equals("client")) {
			Test client = new Test();
			client.run();
		}else {
			TestS server = new TestS();
			server.run();
		}

	}

}
