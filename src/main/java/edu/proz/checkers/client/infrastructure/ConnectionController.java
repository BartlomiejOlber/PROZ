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
	private Selector selector;
	private SocketChannel myClientSocketChannel;
	private int port;
	private static final int BUFFER_SIZE = 128;
	//controllers' interface
	private BlockingQueue<Request> requestQueue;
	private BlockingQueue<Response> responseQueue;
	
	ObjectMapper mapper; //json parser
	private boolean endConnection;
	
	public ConnectionController( BlockingQueue<Request> requestQueue, BlockingQueue<Response> responseQueue, int port ) {
		this.requestQueue = requestQueue;
		this.responseQueue = responseQueue;
		mapper = new ObjectMapper();
		endConnection = false;
		this.port = port;
		
	}
	
	@Override
	public void run() {
		
		try {
			establishConnection();
			while( !endConnection ) {

					processRequest();
			
			}
		}catch( Exception e) {
			
		}
	}
	
	
	private void establishConnection() throws IOException {
		
	      InetAddress hostIP = InetAddress.getLocalHost();
	      InetSocketAddress myAddress = new InetSocketAddress(hostIP, port);
	      myClientSocketChannel = SocketChannel.open(myAddress);
	      selector = Selector.open();
		  myClientSocketChannel.configureBlocking(false);
		  myClientSocketChannel.register(selector, SelectionKey.OP_READ);
		  
	}
	
	private String getResponse() throws IOException{
		
		ByteBuffer readBuffer=ByteBuffer.allocate(BUFFER_SIZE);	
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
		    
	
		return new String("ERR");
	}
	
	
	private void exchangeMessages( Message msg ) throws IOException {
			
			ByteBuffer writeBuffer=ByteBuffer.allocate(BUFFER_SIZE);
			String jsonString = mapper.writeValueAsString(msg);
	        writeBuffer.put(jsonString.getBytes());
	        writeBuffer.flip();
	        myClientSocketChannel.write(writeBuffer);
	        jsonString = getResponse();
	        Response response = mapper.readValue(jsonString, Response.class);
	        responseQueue.add(response);
		
	}
	
	private void closeConnection() throws IOException{
		
		 myClientSocketChannel.close();
	}
	
	
	private void processRequest() throws IOException, InterruptedException {
		

		Request msg = null;
		msg = requestQueue.poll(1, TimeUnit.SECONDS);
		if( msg != null ) {
			//System.out.printf(" request taken id:%d \n ", msg.getId());
			exchangeMessages(msg);
		}		

	}
	
}
