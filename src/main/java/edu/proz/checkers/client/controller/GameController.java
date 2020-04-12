package edu.proz.checkers.client.controller;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.Map;
import java.util.HashMap;

import edu.proz.checkers.infrastructure.*;

interface Command {
	
	void process( Message msg );
}


public class GameController implements Runnable {
	
	private final int QUEUE_CAPACITY = 16;
	private boolean endGame = false;
	private BlockingQueue<Request> requestQueue = null;
	private BlockingQueue<Response> responseQueue = null;
	private Map<String, Command> methodMap = null;
	
	class CommandStart implements Command
	{
		public void process( Message m)
		{
			Start start = (Start) m;
			processStart(start);
		}
	}
	
	public GameController() {
		requestQueue = new LinkedBlockingQueue<Request>( QUEUE_CAPACITY );
		responseQueue = new LinkedBlockingQueue<Response>( QUEUE_CAPACITY );
		methodMap = new HashMap<String, Command>();
		methodMap.put(Start.class.getSimpleName(), new CommandStart());
	}
	
	public BlockingQueue<Request>  getRequestQueue (){
		return this.requestQueue;
	}
	
	public BlockingQueue<Response>  getResponseQueue (){
		return this.responseQueue;
	}
	
	private void processStart( Start msg) {
		
	}
	
	public void run( ) {
		while( !endGame ) {
		//	processRequest();
			processResponse();
			
		}
	}
	
/*	private void processRequest() {
		try{
			Start msg = null;
			msg =(Start) requestQueue.poll(1, TimeUnit.SECONDS);
			if( msg != null ) {
				System.out.printf(" request taken id:%d \n ", msg.getId());
			}
			
			
		}catch( InterruptedException e) {
			
		}

	}
	*/
	//called by a listener
	public void makeMove( int from, int to ) {
		Move move = new Move( from, to );
		requestQueue.add(move);	
	}
	
	private void processResponse() {
		try {
			Response msg = null;
			msg =responseQueue.poll(1, TimeUnit.SECONDS);
			if( msg != null ) {
				System.out.printf(" response taken id:%d \n ", msg.getId());
				methodMap.get( msg.getClass().getSimpleName() ).process( msg );
				//kilka sprawdzen 
				//update z reapaintem paneli
				
			}
			
			
		}catch( InterruptedException e) {
			
		}

	}
	
}
