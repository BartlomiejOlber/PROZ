package edu.proz.checkers.client;

import java.nio.charset.StandardCharsets;

import com.fasterxml.jackson.core.json.ByteSourceJsonBootstrapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.proz.checkers.infrastructure.*;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set; 

public class Test {
	private Selector selector = null;
	private SocketChannel myClientSocketChannel= null;
	private static final int BUFFER_SIZE = 128;

	 public void run() {
		 
		ByteBuffer readBuffer=ByteBuffer.allocate(BUFFER_SIZE);
		ByteBuffer writeBuffer=ByteBuffer.allocate(BUFFER_SIZE);
		ObjectMapper mapper = new ObjectMapper();  
		Move move = new Move(10,20);
	    logger("Starting MySelectorClientExample...");
	    try {
		      int port = 9996;
		      InetAddress hostIP = InetAddress.getLocalHost();
		      InetSocketAddress myAddress =
		          new InetSocketAddress(hostIP, port);
		      myClientSocketChannel = SocketChannel.open(myAddress);
		      selector = Selector.open();
			  myClientSocketChannel.configureBlocking(false);
			  myClientSocketChannel.register(selector, SelectionKey.OP_READ);
		      logger(String.format("Trying to connect to %s:%d...",
		              myAddress.getHostName(), myAddress.getPort()));
		      
		      boolean end_loop = false;
		      Scanner scan = new Scanner(System.in);
		      System.out.println("int pls");
		      int end = scan.nextInt();
			   while ( end != 0) {
				   
				    scan.nextLine();
				    System.out.println("msg pls");
				    String msg = scan.nextLine();
			        writeBuffer.put(msg.getBytes());
			        writeBuffer.flip();
			        myClientSocketChannel.write(writeBuffer);
			        selector.select();
				    Set<SelectionKey> selectedKeys = selector.selectedKeys();
				    Iterator<SelectionKey> i = selectedKeys.iterator();
			
				    while (i.hasNext()) {
					     SelectionKey key = i.next();			
					     if (key.isReadable()) {
					    	 
							  myClientSocketChannel.read(readBuffer);
							  readBuffer.flip();
							  String data = bytes_to_string(readBuffer);
							  
							  if (data.length() > 0) {
								   logger(String.format("Message Received.....: %s  dlugosc %d\n", data,data.length()));
								   end_loop = true;
							  }
					     }
					     i.remove();
				    }
				    System.out.println("int pls 0 to end");
				    end = 0;
				    
			   }
			   scan.close();
		      String jsonString = mapper.writeValueAsString(move);
		
		      System.out.println(jsonString);
		      //myBuffer.put(jsonString.getBytes());
		      //myBuffer.flip();
		      //int bytesWritten = myClientSocketChannel.write(myBuffer);
		    
		      logger("Closing Client connection...");
		      myClientSocketChannel.close();
		      
		      Move move2 = mapper.readValue(jsonString, Move.class);
		      logger(String.format("po konwersji json do move %d,  %d", move2.getFrom(), move2.getTo()));
	    } catch (IOException e) {
		      logger(e.getMessage());
		      e.printStackTrace();
	    }
	 }
	private String bytes_to_string( ByteBuffer bb ) {
		byte[] b = new byte[bb.limit()];
		bb.get(b);
		return new String(b);
	}
	 
  public static void logger(String msg) {
    System.out.println(msg);
  }
}

