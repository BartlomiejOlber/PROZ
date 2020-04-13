package edu.proz.checkers.server.infrastructure;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

import com.fasterxml.jackson.databind.ObjectMapper;

import edu.proz.checkers.Util;
import edu.proz.checkers.server.controller.*;
import edu.proz.checkers.infrastructure.Request;
import edu.proz.checkers.infrastructure.Response;

public class Connection implements Runnable{
	
	Controller controller;
	
	ObjectMapper mapper; //json parser
	
	private static final int BUFFER_SIZE = 128;
	private int port; 
	private static Selector selector;
	ServerSocketChannel myServerSocketChannel;
	private int clients_count;
	
	public Connection( ) {
		try {
			clients_count = 0;
			mapper = new ObjectMapper();
			controller = new Controller();
			init( ) ;
		}catch(Exception e) {
			
		}
	}
	
	private void init() throws IOException {
		
		   InetAddress hostIP = InetAddress.getLocalHost();
		   selector = Selector.open();
		   myServerSocketChannel = ServerSocketChannel.open();
		   ServerSocket serverSocket = myServerSocketChannel.socket();
		   InetSocketAddress address = new InetSocketAddress(hostIP, port);
		   serverSocket.bind(address);
		   myServerSocketChannel.configureBlocking(false);
		   int ops = myServerSocketChannel.validOps();
		   myServerSocketChannel.register(selector, ops, null);
		   
	}
	@Override
	public void run() {
	
		try {
			
		   while (true) {
				
			    selector.select();
			    Set<SelectionKey> selectedKeys = selector.selectedKeys();
			    Iterator<SelectionKey> i = selectedKeys.iterator();
			
			    while (i.hasNext()) {
				     SelectionKey key = i.next();
				
				     if (key.isAcceptable()) {
				    	 processAcceptEvent(  key );
				     } else if (key.isReadable()) {
				    	 processExchange(key);
				     }
				     i.remove();
			    }
		   }
			
		}catch(Exception e) {
			
		}
	}

	private void processAcceptEvent( SelectionKey key) throws IOException {

		 SocketChannel myClient = myServerSocketChannel.accept();
		 myClient.configureBlocking(false);
		 myClient.register(selector, SelectionKey.OP_READ);
	}
	
	private Response getResponse( String data ) throws IOException {
		
		Request request = mapper.readValue(data, Request.class);
		Response response = controller.getResponse( request );		
		return response;
	}
	
	private void sendResponse(  SocketChannel myClient , Response response ) throws IOException {
		
		ByteBuffer writeBuffer = ByteBuffer.allocate(BUFFER_SIZE);
		String jsonString = mapper.writeValueAsString( response );
        writeBuffer.put(jsonString.getBytes());
        writeBuffer.flip();
        myClient.write(writeBuffer);  
	
	}
	
	private void processExchange(SelectionKey key) throws IOException {
		
		System.out.println(("Inside processReadEvent..."));
		SocketChannel myClient = (SocketChannel) key.channel();
		ByteBuffer readBuffer = ByteBuffer.allocate(BUFFER_SIZE);
		myClient.read(readBuffer);
		readBuffer.flip();
		String data = Util.bytes_to_string(readBuffer);
		if (data.length() > 0) {
			System.out.println((String.format("Message Received.....: %s   dlugosc %d\n", data, data.length())));
			       
		}
		
		Response response = getResponse( data );
		sendResponse(myClient, response);
		
	}
	
	private void closeConnection( SelectionKey key ) throws IOException{
		
		SocketChannel myClient = (SocketChannel) key.channel();
		myClient.close();
	    System.out.println(("Closing Server Connection..."));
		
	}
}
