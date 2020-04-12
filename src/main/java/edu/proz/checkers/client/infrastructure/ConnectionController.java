package edu.proz.checkers.client.infrastructure;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import edu.proz.checkers.Util;

import com.fasterxml.jackson.databind.ObjectMapper;

import edu.proz.checkers.infrastructure.*;

public class ConnectionController implements Runnable {
	
	//connection musthaves
	private Selector selector = null;
	private SocketChannel myClientSocketChannel= null;
	private int port;
	private static final int BUFFER_SIZE = 128;
	//controllers' interface
	private BlockingQueue<Request> requestQueue = null;
	private BlockingQueue<Response> responseQueue = null;
	
	ObjectMapper mapper = null; //json parser
	private boolean endConnection = false;
	
	public ConnectionController( BlockingQueue<Request> requestQueue, BlockingQueue<Response> responseQueue ) {
		this.requestQueue = requestQueue;
		this.responseQueue = responseQueue;
		mapper = new ObjectMapper();
		
	}
	
	@Override
	public void run() {
		
		establishConnection();
		
		while( !endConnection ) {
			processRequest();
			
		}
	}
	
	private void establishConnection() {
		
	      //int port = 9996;
		try {
	      InetAddress hostIP = InetAddress.getLocalHost();
	      InetSocketAddress myAddress = new InetSocketAddress(hostIP, port);
	      myClientSocketChannel = SocketChannel.open(myAddress);
	      selector = Selector.open();
		  myClientSocketChannel.configureBlocking(false);
		  myClientSocketChannel.register(selector, SelectionKey.OP_READ);
		  
		}catch( IOException e) {
			
		}
	}
	
	private String getResponse() {
		ByteBuffer readBuffer=ByteBuffer.allocate(BUFFER_SIZE);
    
		try{
			selector.select();
		    Set<SelectionKey> selectedKeys = selector.selectedKeys();
		    Iterator<SelectionKey> i = selectedKeys.iterator();
	
		    while (i.hasNext()) {
			     SelectionKey key = i.next();			
			     if (key.isReadable()) {
			    	 
					  myClientSocketChannel.read(readBuffer);
					  readBuffer.flip();
					  String data = Util.bytes_to_string(readBuffer);
					  
					  if (data.length() > 0) {
						   System.out.println(String.format("Message Received.....: %s  dlugosc %d\n", data,data.length()));
						   return data;
					  }
			     }
			     i.remove();
		    }
		    
		}catch(IOException e) {
			
		}
		return new String("ERR");
	}
	
	
	public void signalStart( String myName ) {
		
		Start start = new Start( myName );		
		exchangeMessages(start);
	}
	
	
	private void exchangeMessages( Message msg ) {
		try {
			
			ByteBuffer writeBuffer=ByteBuffer.allocate(BUFFER_SIZE);
			String jsonString = mapper.writeValueAsString(msg);
	        writeBuffer.put(jsonString.getBytes());
	        writeBuffer.flip();
	        myClientSocketChannel.write(writeBuffer);
	        jsonString = getResponse();
	        Response response = mapper.readValue(jsonString, Response.class);
	        responseQueue.add(response);
	        
		}catch(IOException e ) {
			
		}
		
	}
	
	private void closeConnection() {
		
		
	}
	
	
	private void processRequest() {
		
		try{
			Request msg = null;
			msg = requestQueue.poll(1, TimeUnit.SECONDS);
			if( msg != null ) {
				//System.out.printf(" request taken id:%d \n ", msg.getId());
				if( msg instanceof Move) {
					//sendMove( msg );
				}else {
				//	signalStart();
				}
			}
			
			
		}catch( InterruptedException e) {
			
		}

	}
	

}
