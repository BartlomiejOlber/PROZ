package edu.proz.checkers.server;

import java.awt.BorderLayout;
import java.io.IOException;
import java.nio.channels.SocketChannel;

import javax.swing.*;

import edu.proz.checkers.server.controller.SessionController;
import edu.proz.checkers.server.infrastructure.*;

public class ServerApp extends JFrame {

	private static final long serialVersionUID = 1L;
	
	//Frame components
	private JScrollPane scroll;
	private JTextArea information;
	private JLabel title;
	
	private static AcceptConnectionHandler acceptor;
	
	public ServerApp(){
		
		BorderLayout layout = new BorderLayout();
		setLayout(layout);
		
		title = new JLabel("Server");
		information = new JTextArea();
		scroll = new JScrollPane(information);
		
		add(title,BorderLayout.NORTH);
		add(scroll, BorderLayout.CENTER);
		
		acceptor = new AcceptConnectionHandler();
	}	
	
	public void start() {
		
		while(true) {
			try {
				SocketChannel clientOne = acceptor.accept();
				SocketChannel clientTwo = acceptor.accept();
				SessionConnectionController scc = new SessionConnectionController( clientOne, clientTwo );
				SessionController newSession = new SessionController( scc );
				new Thread(newSession).start();
			}catch(IOException e) {
				
			}

			
		}
		
	}
}
