package edu.proz.checkers.server.infrastructure;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

import edu.proz.checkers.infrastructure.ConfigParams;

public class AcceptConnectionHandler {
	
	private int port; 
	private static Selector selector;
	ServerSocketChannel myServerSocketChannel;
	
	
	public AcceptConnectionHandler( ConfigParams params ) {
		try {
			init( params ) ;
		}catch(Exception e) {
			System.err.print("unable to init server connection tools");
			e.printStackTrace();
		}
	}
	
	private void init( ConfigParams params ) throws IOException {
		
		port = params.getPort();
	    InetAddress hostIP = InetAddress.getByName(params.getAddress());
	    selector = Selector.open();
	    myServerSocketChannel = ServerSocketChannel.open();
	    ServerSocket serverSocket = myServerSocketChannel.socket();
	    InetSocketAddress address = new InetSocketAddress(hostIP, port);
	    serverSocket.bind(address);
	    myServerSocketChannel.configureBlocking(false);
	    int ops = myServerSocketChannel.validOps();
	    myServerSocketChannel.register(selector, ops, null);
   
	}
	
	public SocketChannel accept() throws IOException {
		
	    selector.select();
	    Set<SelectionKey> selectedKeys = selector.selectedKeys();
	    Iterator<SelectionKey> i = selectedKeys.iterator();		
		SelectionKey key = i.next();	
		if (key.isAcceptable()) {
			SocketChannel myClient = myServerSocketChannel.accept();
			myClient.configureBlocking(false);
			return myClient;		 
		 }
		
		i.remove();
	    return null;		
	
	}


	
}
