package edu.proz.checkers.client;

import javax.swing.JFrame;

import edu.proz.checkers.client.controller.GameController;
import edu.proz.checkers.client.infrastructure.ConnectionController;
import edu.proz.checkers.client.model.Player;
import edu.proz.checkers.client.view.GraphicBoard;
import edu.proz.checkers.client.view.MyMenuBar;
import edu.proz.checkers.client.view.SquareMouseListener;
import edu.proz.checkers.infrastructure.ConfigParams;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Client application class
 * @see JFrame
 * @see ActionListener
 */
public class ClientApp extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;


	// model
	private Player player;

	// view
	private GraphicBoard graphicBoard;

	// menu options
	private MyMenuBar menuBar;

	/**
	 * Starts the client's application - initializes controllers, makes initial message exchange and starts both controllers threads
	 * @param params contain address and port configuration
	 */
	public ClientApp( ConfigParams params ) {

		try {

			String name = (String) JOptionPane.showInputDialog(null, "Enter your nickname to " +
							"connect to server","Connect", JOptionPane.OK_CANCEL_OPTION);

			if (name != null && name.length() > 0) {
				player = new Player(name);

				connect( params );

			} else {
				JOptionPane.showMessageDialog(null, "Please enter correct name",
						"Incorrect name", JOptionPane.ERROR_MESSAGE, null);
				System.exit(0);
			}
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, "Please enter correct IPv4 address",
					"Incorrect IPv4 address", JOptionPane.ERROR_MESSAGE, null);
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
		menuBar = new MyMenuBar();
		menuBar.setActionListener(this);
		menuBar.setKeyboardShortcuts();
		setJMenuBar(menuBar);
	}

	/**
	 * Method that shows appropriate dialog window when someone clicks at one of the menu items.
	 * @param e Object generated when action event occurs.
	 * @see ActionListener#actionPerformed(ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		int answer;
		if (source == this.menuBar.getExit()) {
			answer = JOptionPane.showConfirmDialog(null, "Are you sure that " +
					"you want to give up and end the game?", "Confirmation", JOptionPane.YES_NO_OPTION);
			if (answer == JOptionPane.YES_OPTION) {
				System.exit(0);
			}
		}
		else if (source == this.menuBar.getKeyboardShortcuts()) {
			JOptionPane.showMessageDialog(null, "Exit - ALT + F4\n" +
							"Keyboard shortcuts - CTRL + K\nAbout the game - CTRL + A",
					"Keyboard shortcuts", JOptionPane.INFORMATION_MESSAGE, null);
		}
		else {
			JOptionPane.showMessageDialog(null, "This is a classic multiplayer " +
							"checkers game.\nYour goal is to beat all the opponent's pawns.\nWhen your" +
							" opponent has no pawn left, you win the game.\nGood luck!",
					"About the game", JOptionPane.INFORMATION_MESSAGE, null);
		}
	}
}

