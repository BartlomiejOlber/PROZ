package edu.proz.checkers.server.infrastructure;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.databind.ObjectMapper;

import edu.proz.checkers.Util;
import edu.proz.checkers.infrastructure.*;

public class SessionConnectionController {
	
	Selector selector;
	private Map<Integer, SocketChannel> clients;	
	ObjectMapper mapper; //json parser	
	private static final int BUFFER_SIZE = 128;
	
	public SessionConnectionController()  throws IOException {
		
		clients = new HashMap<Integer, SocketChannel>();
		selector = Selector.open();
		mapper = new ObjectMapper();
		
	}
	
	public void addClient( SocketChannel client, int client_number )  throws IOException {
		
		clients.put(client_number, client);
		client.register(selector, SelectionKey.OP_READ);
		
	}
	
	public Request getRequest( ) throws IOException {
		
		ByteBuffer readBuffer=ByteBuffer.allocate(BUFFER_SIZE);	
		String data = null;
		selector.select();
	    Set<SelectionKey> selectedKeys = selector.selectedKeys();
	    Iterator<SelectionKey> i = selectedKeys.iterator();

	    while (i.hasNext()) {
		     SelectionKey key = i.next();			
		     if (key.isReadable()) {
		    	 SocketChannel clientChannel = (SocketChannel) key.channel();	
				  clientChannel.read(readBuffer);
				  readBuffer.flip();
				  data = Util.bytes_to_string(readBuffer);
				  
				  if (data.length() > 0) {
					   System.out.println(String.format("Message Received.....: %s  dlugosc %d\n", data,data.length()));
				  }
		     }
		     i.remove();
	    }
		Request request = mapper.readValue(data, Request.class);
		return request;
	}
	
	public void sendResponse( Response response ) throws IOException {
		
		ByteBuffer writeBuffer = ByteBuffer.allocate(BUFFER_SIZE);
		String jsonString = mapper.writeValueAsString( response );
        writeBuffer.put(jsonString.getBytes());
        writeBuffer.flip();
        clients.get(response.getPlayerId()).write(writeBuffer);  
	
	}
	
/*	private void processExchange(SelectionKey key) throws IOException {
		
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
	*/
}
