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
	
	private final static int QUEUE_CAPACITY = 16;
	private boolean endGame;
	private BlockingQueue<Request> requestQueue;
	private BlockingQueue<Response> responseQueue;
	private Map<String, Command> methodMap;
	
	private boolean waiting;
	
	private class CommandStartResponse implements Command
	{
		public void process( Message m)
		{
			StartResponse start = (StartResponse) m;
			processStartResponse(start);
		}
	}
	
	private class CommandMoveResponse implements Command
	{
		public void process( Message m)
		{
			MoveResponse move = (MoveResponse) m;
			processMoveResponse(move);
		}
	}
	
	private class CommandStopResponse implements Command
	{
		public void process( Message m)
		{
			StopResponse stop = (StopResponse) m;
			processStopResponse(stop);
		}
	}
	
	private class CommandOpponentMovedResponse implements Command
	{
		public void process( Message m)
		{
			OpponentMovedResponse response = (OpponentMovedResponse) m;
			processOpponentMovedResponse(response);
		}
	}
	
	private class CommandWaitResponse implements Command
	{
		public void process( Message m)
		{
			WaitResponse response = (WaitResponse) m;
			processWaitResponse(response);
		}
	}
	
	public GameController() {
		requestQueue = new LinkedBlockingQueue<Request>( QUEUE_CAPACITY );
		responseQueue = new LinkedBlockingQueue<Response>( QUEUE_CAPACITY );
		methodMap = new HashMap<String, Command>();
		methodMap.put(StartResponse.class.getSimpleName(), new CommandStartResponse());
		methodMap.put(MoveResponse.class.getSimpleName(), new CommandMoveResponse());
		methodMap.put(StopResponse.class.getSimpleName(), new CommandStopResponse());
		methodMap.put(WaitResponse.class.getSimpleName(), new CommandWaitResponse());
		methodMap.put(OpponentMovedResponse.class.getSimpleName(), new CommandOpponentMovedResponse());
		endGame = false;
		waiting = false;
	}
	
	public BlockingQueue<Request>  getRequestQueue (){
		return this.requestQueue;
	}
	
	public BlockingQueue<Response>  getResponseQueue (){
		return this.responseQueue;
	}
	
	private void processStartResponse( StartResponse msg) {
		
	}
	
	private void processMoveResponse( MoveResponse msg) {
		
	}
	
	private void processStopResponse( StopResponse msg) {
		
	}

	private void processWaitResponse( WaitResponse msg) {
		
	}
	
	private void processOpponentMovedResponse( OpponentMovedResponse msg) {
		
	}
	
	@Override 
	public void run( ) {
		while( !endGame ) {
			try {
				if(waiting)
					askForOpponent();
				processResponse();
			}catch(Exception e) {
				
			}
		}
	}
	
	private void askForOpponent() throws InterruptedException{
		
		GetOpponentEvent request = new GetOpponentEvent();
		requestQueue.add(request);
		
	}

	
	//called by a listener
	public void makeMove( int from, int to ) {
		Move move = new Move( from, to );
		requestQueue.add(move);	
	}
	
	public void makeStart( String player_name ) {
		
		Start start = new Start( player_name );
		requestQueue.add(start);
	}
	
	public void makeStop( ) {
		
		Stop stop = new Stop(  );
		requestQueue.add(stop);
	}
	
	private void processResponse() throws InterruptedException {
		
		Response msg = null;
		msg =responseQueue.poll(1, TimeUnit.SECONDS);
		if( msg != null ) {
			methodMap.get( msg.getClass().getSimpleName() ).process( msg );
			
		}


	}
	
}
