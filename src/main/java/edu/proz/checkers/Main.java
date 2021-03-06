package edu.proz.checkers;

import javax.swing.*;

import edu.proz.checkers.client.*;
import edu.proz.checkers.infrastructure.ConfigManager;
import edu.proz.checkers.infrastructure.ConfigParams;
import edu.proz.checkers.server.*;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


/**
 * Main class of the project
 *
 */
public class Main {

	/**
	 * Loads configuration json file to a data transfer object and starts a client or server application
	 * 
	 * @param args used to tell whether to run server or client side
	 */
	public static void main(String[] args) {
		
		ConfigManager cm = new ConfigManager();
		if( args[0].equals("client")) { 

			ConfigParams params = cm.load("/client_params.json");
			ClientApp client = new ClientApp( params );
			client.setTitle("Checkers");
			client.pack();
			client.setVisible(true);
			client.setLocation(250, 150);
			client.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			client.addWindowListener(new WindowAdapter() {
				@Override
				public void windowClosing(WindowEvent we)
				{
					int promptResult = JOptionPane.showConfirmDialog(null,
							"Are you sure that " + "you want to give up and end the game?",
							"Confirmation", JOptionPane.YES_NO_OPTION);
					if (promptResult == JOptionPane.YES_OPTION)
						System.exit(0);
				}
			});
		} else {
			
			
			ConfigParams params = cm.load("/server_params.json");
		
			ServerApp server = new ServerApp( params );
			server.setSize(400,250);
			server.setVisible(true);
			server.setTitle("Checkers Server");
			server.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
		
			server.start( );
				

		}
		
	}

}
