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

import javax.swing.JOptionPane;

import com.fasterxml.jackson.databind.ObjectMapper;

import edu.proz.checkers.infrastructure.*;

/**
 * Runnable Connection Controller class responsible for taking requests from the GameController and exchanging messages with the 
 * server side
 * 
 * @author bartlomiej
 *
 */
public class ConnectionController implements Runnable {
	
	//connection musthaves
	private Selector selector;
	private SocketChannel myClientSocketChannel;
	private int port;
	private String address;
	private final int BUFFER_SIZE = 128;
	//controllers' interface
	private BlockingQueue<Request> requestQueue;
	private BlockingQueue<Response> responseQueue;
	
	private ObjectMapper mapper; //json parser
	private boolean endConnection;
	
	/**
	 * Initializes its fields
	 * @param requestQueue interface between controllers
	 * @param responseQueue interface between controllers
	 * @param params configuration settings of INet address and port
	 */
	public ConnectionController( BlockingQueue<Request> requestQueue, BlockingQueue<Response> responseQueue, ConfigParams params ) {
		this.requestQueue = requestQueue;
		this.responseQueue = responseQueue;
		mapper = new ObjectMapper();
		endConnection = false;
		this.port = params.getPort();
		this.address = params.getAddress();
		
	}
	
	/**
	 * ConnectionController's thread loop - takes pending messages on RequestQueue, sends them one by one to the server and awaits
	 * a response message from the server, after receiving it, the controller puts the message on the ResponseQueue and repeats 
	 * the operation until the end of the game
	 *
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		
		try {
			while( !endConnection ) {

				processRequest();
		
			}
		}catch( Exception e) {
			JOptionPane.showMessageDialog(null, "Server connection error.",
					"Information", JOptionPane.INFORMATION_MESSAGE, null);
			System.exit(0);
			e.printStackTrace();
				
		}
	}
	
	
	/**
	 * Connects to the server side
	 * @throws IOException when the initialization goes wrong
	 */
	public void establishConnection() throws IOException {
		
	      InetAddress hostIP = InetAddress.getByName(address);
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
				  byte[] b = new byte[readBuffer.limit()];
				  readBuffer.get(b);
				  String data = new String(b);				  
				  if (data.length() > 0) {
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
	
	
	/**
	 * Essential method of the controller - takes a message from the RequestQueue (waits one second for it), sends it to server
	 * and puts a response on ResponseQueue
	 * 
	 * @throws IOException when the messages exchange goes ill
	 * @throws InterruptedException when interrupted on polling on the BlockingQueue
	 */
	public void processRequest() throws IOException, InterruptedException {
		

		Request msg = null;
		msg = requestQueue.poll(1, TimeUnit.SECONDS);
		if( msg != null ) {
			exchangeMessages(msg);
		}		

	}
	
}
