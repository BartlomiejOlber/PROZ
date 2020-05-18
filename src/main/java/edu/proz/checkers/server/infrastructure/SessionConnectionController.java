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
		
		try {
			ByteBuffer readBuffer=ByteBuffer.allocate(BUFFER_SIZE);	
			String data = null;
			System.out.print("przed select\n");
			selector.select();
		    Set<SelectionKey> selectedKeys = selector.selectedKeys();
		    Iterator<SelectionKey> i = selectedKeys.iterator();
		    while(true) {
			    while (i.hasNext()) {
				     SelectionKey key = i.next();			
				     if (key.isReadable()) {
				    	 SocketChannel clientChannel = (SocketChannel) key.channel();	
						  clientChannel.read(readBuffer);
						  readBuffer.flip();
						  data = Util.bytes_to_string(readBuffer);
						  
						  if (data.length() > 0) {
							   System.out.println(String.format("Message Received %s.....: %s \n", data,data.length()));
							   Request request = mapper.readValue(data, Request.class);
							   System.out.print(request.getPlayerId());
							   i.remove();
							   return request;
						  }
				     }
				 i.remove();    
			    }
		    }
		}catch (IOException e){
			Stop stop= closeConnections();
			return stop;
		}
	}
	
	public void sendResponse( Response response ) throws IOException {
		
		ByteBuffer writeBuffer = ByteBuffer.allocate(BUFFER_SIZE);
		String jsonString = mapper.writeValueAsString( response );
        writeBuffer.put(jsonString.getBytes());
        writeBuffer.flip();
        System.out.println(String.format("Message sent.....: %s  dlugosc %d\n", jsonString,jsonString.length()));
        clients.get(response.getPlayerId()).write(writeBuffer);  
	
	}
	
	
	public Stop closeConnections() throws IOException {
		Stop stop = null;
		clients.forEach( (k,v) ->  {
			if(v.isConnected()) {
				try {
					v.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		return stop;
	}
}
