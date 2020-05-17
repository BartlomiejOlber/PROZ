package edu.proz.checkers.server;

import java.awt.BorderLayout;
import java.io.IOException;
import java.nio.channels.SocketChannel;
import java.util.Date;

import javax.swing.*;

import edu.proz.checkers.server.controller.SessionController;
import edu.proz.checkers.server.infrastructure.AcceptConnectionHandler;
import edu.proz.checkers.server.infrastructure.SessionConnectionController;

public class ServerApp extends JFrame {

	private static final long serialVersionUID = 1L;
	
	//Frame components
	private JScrollPane scroll;
	private JTextArea information;
	private JLabel title;
	int sessionNo;
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
				sessionNo = 1;	
				SessionConnectionController scc = new SessionConnectionController();
				SessionController newSession = new SessionController( scc );
				information.append(new Date()+ ":- Session "+ sessionNo + " is started\n");
				
				SocketChannel clientOne = acceptor.accept();
				scc.addClient(clientOne, 1);
				Request startRequest = scc.getRequest();
				Response startResponse = newSession.getResponse( startRequest );
				scc.sendResponse(startResponse);
				
				information.append(new Date() + ":- player1 joined at\n");
				information.append(clientOne.toString());
				
				
				SocketChannel clientTwo = acceptor.accept();
				scc.addClient(clientTwo, 2);
				Request startSecondRequest = scc.getRequest();
				Response startSecondResponse = newSession.getResponse( startSecondRequest );
				scc.sendResponse(startSecondResponse);
				
				information.append(new Date() + ":- player2 joined at\n");
				information.append(clientTwo.toString());
				
				
				new Thread(newSession).start();
			}catch(IOException e) {
				
			}

			
		}
		
	}
}
