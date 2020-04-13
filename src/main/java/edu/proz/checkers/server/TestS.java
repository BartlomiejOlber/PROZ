package edu.proz.checkers.server;

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

public class TestS {
 
	private static final int BUFFER_SIZE = 128;
 
	private static Selector selector = null;
	private int clients_count = 0;
 
	public void run() {
			logger("Starting MySelectorExample...");
		  try {
			   InetAddress hostIP = InetAddress.getLocalHost();
			   int port = 9996;
			   logger(String.format("Trying to accept connections on %s:%d...",
			     hostIP.getHostAddress(), port));
			   selector = Selector.open();
			   ServerSocketChannel myServerSocketChannel = ServerSocketChannel.open();
			   ServerSocket serverSocket = myServerSocketChannel.socket();
			   InetSocketAddress address = new InetSocketAddress(hostIP, port);
			   serverSocket.bind(address);
			
			   myServerSocketChannel.configureBlocking(false);
			   int ops = myServerSocketChannel.validOps();
			   myServerSocketChannel.register(selector, ops, null);
			   while (true) {
				
				    selector.select();
				    Set<SelectionKey> selectedKeys = selector.selectedKeys();
				    Iterator<SelectionKey> i = selectedKeys.iterator();
				
				    while (i.hasNext()) {
					     SelectionKey key = i.next();
					
					     if (key.isAcceptable()) {
					    	 processAcceptEvent(myServerSocketChannel, key);
					     } else if (key.isReadable()) {
					    	 processReadEvent(key);
					     }
					     i.remove();
				    }
			   }
		  }catch (IOException e) {
			   logger(e.getMessage());
			   e.printStackTrace();
		  }
}

	 private void processAcceptEvent(ServerSocketChannel mySocket,
	                  SelectionKey key) throws IOException {
	
		  logger("Connection Accepted...");
		  // Accept the connection and make it non-blocking
		  SocketChannel myClient = mySocket.accept();
		  myClient.configureBlocking(false);
		
		  // Register interest in reading this channel
		  myClient.register(selector, SelectionKey.OP_READ);
	 }
	
	 private void processReadEvent(SelectionKey key)
	                      throws IOException {
		 String msg = new String("standardowa odpowiedz servwera");
		  logger("Inside processReadEvent...");
		  // create a ServerSocketChannel to read the request
		  SocketChannel myClient = (SocketChannel) key.channel();
		
		  // Set up out 1k buffer to read data into
		  
		  ByteBuffer readBuffer = ByteBuffer.allocate(BUFFER_SIZE);
		  ByteBuffer writeBuffer = ByteBuffer.allocate(BUFFER_SIZE);
		  myClient.read(readBuffer);
		  readBuffer.flip();
		  String data = bytes_to_string(readBuffer);
		  if (data.length() > 0) {
			   logger(String.format("Message Received.....: %s   dlugosc %d\n", data, data.length()));
			   if(data.equals("potwierdz"))
			   		msg = new String("potwiedzam");
			   if(data.equals("zaprzecz"))
		   			msg = new String("zaprzecam");
			   
			   
		        writeBuffer.put(msg.getBytes());
		        writeBuffer.flip();
		        myClient.write(writeBuffer);   
			   if (data.equalsIgnoreCase("*exit*")) {
				    myClient.close();
				    logger("Closing Server Connection...");
			   }
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
