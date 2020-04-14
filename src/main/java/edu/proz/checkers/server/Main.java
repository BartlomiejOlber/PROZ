package edu.proz.checkers.server;
import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) {
		
		ServerApp server = new ServerApp();
		server.setSize(400,250);
		server.setVisible(true);
		server.setTitle("Checkers Server");
		server.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		server.start();
	}

}
