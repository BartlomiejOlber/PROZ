package edu.proz.checkers.client;

import javax.swing.JFrame;

import edu.proz.checkers.client.controller.GameController;
import edu.proz.checkers.client.infrastructure.ConnectionController;
import edu.proz.checkers.client.model.Player;
import edu.proz.checkers.client.view.GraphicBoard;
import edu.proz.checkers.client.view.SquareMouseListener;
import edu.proz.checkers.infrastructure.ConfigParams;

import javax.swing.*;

/**
 * Client application class
 *
 */
public class ClientApp extends JFrame {

	private static final long serialVersionUID = 1L;


	// model
	private Player player;

	// view
	private GraphicBoard graphicBoard;

	/**
	 * Starts the client's application - initializes controllers, makes initial message exchange and starts both controllers threads
	 * @param params contain address and port configuration
	 */
	public ClientApp( ConfigParams params ) {

		try {

			String name = (String) JOptionPane.showInputDialog(null, "Enter your name to Connect", "Connect to Server",
					JOptionPane.OK_CANCEL_OPTION);

			if (name != null && name.length() > 0) {
				player = new Player(name);

				connect( params );

			} else {
				JOptionPane.showMessageDialog(null, "Please enter valid name", "Error", JOptionPane.ERROR_MESSAGE,
						null);
				System.exit(0);
			}
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, "Please enter valid IPv4-Address", "Error", JOptionPane.ERROR_MESSAGE,
					null);
			System.exit(0);
		}

	}

	private void connect( ConfigParams params ) {

		GameController gc = new GameController( player );
		ConnectionController cc = new ConnectionController( gc.getRequestQueue(), gc.getResponseQueue(), params );
		try {
			gc.makeStart();
			cc.establishConnection();
			cc.processRequest();
			gc.processResponse();
		}
		catch(Exception e) {
			System.err.print("Unable to start game");
			e.printStackTrace();
		}

		setup(gc);
		new Thread( cc ).start();
		new Thread( gc ).start();

	}

	private void setup(GameController c) {
		SquareMouseListener listener = new SquareMouseListener();
		listener.setController(c);

		graphicBoard = new GraphicBoard(listener);
		c.setGraphicBoard(graphicBoard);
		add(graphicBoard);
	}
}

