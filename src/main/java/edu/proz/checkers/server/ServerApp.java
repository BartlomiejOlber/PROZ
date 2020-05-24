package edu.proz.checkers.server;

import java.awt.BorderLayout;
import java.io.IOException;
import java.nio.channels.SocketChannel;
import java.util.Date;

import javax.swing.*;

import edu.proz.checkers.Constants;
import edu.proz.checkers.infrastructure.ConfigParams;
import edu.proz.checkers.infrastructure.Request;
import edu.proz.checkers.infrastructure.Response;
import edu.proz.checkers.server.controller.SessionController;
import edu.proz.checkers.server.infrastructure.AcceptConnectionHandler;
import edu.proz.checkers.server.infrastructure.SessionConnectionController;

/**
 * Server application main class. Sets a graphic frame printing information logs.
 * @author bartlomiej
 *
 */
public class ServerApp extends JFrame {

	private static final long serialVersionUID = 1L;
	
	//Frame components
	private JScrollPane scroll;
	private JTextArea information;
	private JLabel title;
	int sessionNo;
	private static AcceptConnectionHandler acceptor;
	
	/**
	 * Starts a new frame and the controller responsible for accepting connections
	 * @param params configuration DTO
	 */
	public ServerApp(ConfigParams params){
		
		
		BorderLayout layout = new BorderLayout();
		setLayout(layout);
		
		title = new JLabel("Server");
		information = new JTextArea();
		scroll = new JScrollPane(information);
		
		add(title,BorderLayout.NORTH);
		add(scroll, BorderLayout.CENTER);
		
		acceptor = new AcceptConnectionHandler( params );
	}	
	
	/**
	 * Main loop of the application.
	 * Initializes new two controllers responsible for connection and game logic,
	 * accepts one client and exchanges initial messages with him, 
	 * accepts another client and performs initial messages exchange with him,
	 * starts a new session thread,
	 * repeats the former steps until is closed 
	 *
	 * 
	 */
	public void start() {
		
		while(true) {
			try {
				sessionNo = 1;	
				SessionConnectionController scc = new SessionConnectionController();
				SessionController newSession = new SessionController( scc );
				
				
				SocketChannel clientOne = acceptor.accept();
				scc.addClient(clientOne, Constants.PLAYER_ONE_ID.getValue());
				Request startRequest = scc.getRequest();
				Response startResponse = newSession.getResponse( startRequest );
				scc.sendResponse(startResponse);
				
				information.append(new Date() + ":- player1 joined\n");
				
				
				SocketChannel clientTwo = acceptor.accept();
				scc.addClient(clientTwo, Constants.PLAYER_TWO_ID.getValue());
				
				Request startSecondRequest = scc.getRequest();
				Response startSecondResponse = newSession.getResponse( startSecondRequest );
				scc.sendResponse(startSecondResponse);	
				information.append(new Date() + ":- player2 joined\n");
				information.append(new Date()+ ":- Session "+ sessionNo++ + " is started\n");
				new Thread(newSession).start();
			}catch(IOException e) {
				e.printStackTrace();
			}

			
		}
		
	}
}
