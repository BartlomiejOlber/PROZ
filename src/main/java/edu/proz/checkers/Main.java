package edu.proz.checkers;

import javax.swing.JFrame;

import edu.proz.checkers.client.*;
import edu.proz.checkers.infrastructure.ConfigManager;
import edu.proz.checkers.infrastructure.ConfigParams;
import edu.proz.checkers.server.*;



public class Main {

	public static void main(String[] args) {
		
		ConfigManager cm = new ConfigManager();
		if( args[0].equals("client")) { //wolanie clientapp

			ConfigParams params = cm.load("/client_params.json");
			ClientApp client = new ClientApp( params );
		}else { //wolanie serverapp
			
			
			ConfigParams params = cm.load("/server_params.json");
		
			ServerApp server = new ServerApp( params );
			server.setSize(400,250);
			server.setVisible(true);
			server.setTitle("Checkers Server");
			server.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
			//start Connection
			server.start( );
				

		}
		
	}

}
