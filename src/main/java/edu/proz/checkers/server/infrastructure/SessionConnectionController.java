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

import edu.proz.checkers.infrastructure.*;


/**
 * Class responsible for exchanging messages with clients.
 * Ment to be used by SessionController for sending and receiving messages.
 * @author bartlomiej
 */
public class SessionConnectionController {
	
	private Selector selector;
	private Map<Integer, SocketChannel> clients;	
	private ObjectMapper mapper; //json parser	
	private final int BUFFER_SIZE = 128;
	private final int TIMEOUT = 1000;
	
	/**
	 * Initializes selector and Jackson ObjectMapper used for serializing to JSON format and deserializing messages
	 * @throws IOException when selector initialization goes wrong
	 */
	public SessionConnectionController()  throws IOException {
		
		clients = new HashMap<Integer, SocketChannel>();
		selector = Selector.open();
		mapper = new ObjectMapper();
		
	}
	
	/**
	 * Registers client channel. Registered channels are to be used to exchange messages with clients
	 * 
	 * @param client Accepted channel to be registered in private selector
	 * @param client_number client ID
	 * @throws IOException when the channel passed cannot be registered to the selector
	 */
	public void addClient( SocketChannel client, int client_number )  throws IOException {
		
		clients.put(client_number, client);
		client.register(selector, SelectionKey.OP_READ);
		
	}
	
	/**
	 * Method performs receiving of data if any data is to be read. Receiving no data from clients means that a connection
	 * has been closed, then a Stop request is returned. 
	 * @return polymorphic Request received from a client
	 * @throws IOException when receiving messages goes wrong
	 * @see Request
	 */
	public Request getRequest( ) throws IOException {
		
		
		ByteBuffer readBuffer=ByteBuffer.allocate(BUFFER_SIZE);	
		String data = null;
		try {
		    while(true) {
				selector.select(TIMEOUT);
			    Set<SelectionKey> selectedKeys = selector.selectedKeys();
			    Iterator<SelectionKey> i = selectedKeys.iterator();
			    while (i.hasNext()) {
				     SelectionKey key = i.next();			
				     if (key.isReadable()) {
				    	 SocketChannel clientChannel = (SocketChannel) key.channel();	
						  clientChannel.read(readBuffer);
						  readBuffer.flip();
						  byte[] b = new byte[readBuffer.limit()];
						  readBuffer.get(b);
						  data = new String(b);						  
						  if (data.length() > 0) {
							   Request request = mapper.readValue(data, Request.class);
							   i.remove();
							   return request;
						  }else {
							  Stop stop= new Stop(getConnectedClient( key ));
							  return stop;
						  }					  
				     }
				 i.remove();    
			    }		
		}
	    }catch( IOException e) {
			  Stop stop= new Stop(getConnectedClient(  ));
			  return stop;
	    }
	
	}

	/**  Serializes polymorphic Response to JSON format String and writes it to a channel depending on response player ID field 
	 * @param response polymorphic response that is sent to client
	 * @throws IOException when writing to a closed channel
	 */
	public void sendResponse( Response response ) throws IOException {
		
		ByteBuffer writeBuffer = ByteBuffer.allocate(BUFFER_SIZE);
		String jsonString = mapper.writeValueAsString( response );
	    writeBuffer.put(jsonString.getBytes());
	    writeBuffer.flip();
	    clients.get(response.getPlayerId()).write(writeBuffer);  
	
	}
	
	private int getConnectedClient( ) {
		int waiting = 0;
		for(int i = 1; i < 3; ++i) {
			if( clients.get(i).isConnected() ) {
				waiting = i%2 + 1;
			}
		}
		return waiting;
	}
	
	private int getConnectedClient( SelectionKey key ) {
		int waiting = 0;
		for(int i = 1; i < 3; ++i) {
			if( key.channel().equals(clients.get(i) ) ) {
				waiting = i%2 + 1;
			}
		}
		return waiting;
	}
}
