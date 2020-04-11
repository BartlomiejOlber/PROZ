package edu.proz.checkers.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.proz.checkers.infrastructure.*;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class Test {
 private static final int BUFFER_SIZE = 1024;
 private static String[] messages =
  {"The best way to predict the future is to create it.",
  "As you think, so shall you become.",
  "The noblest pleasure is the joy of understanding.",
  "Courage is grace under pressure.",
  "*exit*"};

  public void run() {
	  
	ObjectMapper mapper = new ObjectMapper();  
	Move move = new Move(10,20);
    logger("Starting MySelectorClientExample...");
    try {
      int port = 9999;
      InetAddress hostIP = InetAddress.getLocalHost();
      InetSocketAddress myAddress =
          new InetSocketAddress(hostIP, port);
      SocketChannel myClient = SocketChannel.open(myAddress);

      logger(String.format("Trying to connect to %s:%d...",
              myAddress.getHostName(), myAddress.getPort()));
      
      
      for (String msg: messages) {
    	ByteBuffer myBuffer=ByteBuffer.allocate(BUFFER_SIZE);
        myBuffer.put(msg.getBytes());
        myBuffer.flip();
        int bytesWritten = myClient.write(myBuffer);
        logger(String
              .format("Sending Message...: %s\nbytesWritten...: %d",
                      msg, bytesWritten));
     }
      String jsonString = mapper.writeValueAsString(move);

      System.out.println(jsonString);
      ByteBuffer myBuffer=ByteBuffer.allocate(jsonString.length() + BUFFER_SIZE);
      myBuffer.put(jsonString.getBytes());
      myBuffer.flip();
      int bytesWritten = myClient.write(myBuffer);
      logger(String
            .format("Sending Message...: %s\nbytesWritten...: %d",
                    jsonString, bytesWritten));
      
      logger("Closing Client connection...");
      myClient.close();
      
      Move move2 = mapper.readValue(jsonString, Move.class);
      logger(String.format("po konwersji json do move %d,  %d", move2.getFrom(), move2.getTo()));
    } catch (IOException e) {
      logger(e.getMessage());
      e.printStackTrace();
    }
  }
  
  public static void logger(String msg) {
    System.out.println(msg);
  }
}

