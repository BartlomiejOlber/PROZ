package edu.proz.checkers.client.controller;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.Map;
import java.util.HashMap;

import edu.proz.checkers.client.model.Player;
import edu.proz.checkers.infrastructure.*;

interface Command {	
	
	void process( Message msg );
	
}


public class GameController implements Runnable {
	
	private final static int QUEUE_CAPACITY = 16;
	private BlockingQueue<Request> requestQueue;
	private BlockingQueue<Response> responseQueue;
	private Map<String, Command> methodMap;
	//--------------------------------
	private Player player;	
	private boolean waitingForAction;
	private boolean gameOver;

	
	private class CommandStartResponse implements Command
	{
		public void process( Message m)
		{
			StartResponse start = (StartResponse) m;
			processStartResponse(start);
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
	
	private class CommandMoveResponse implements Command
	{
		public void process( Message m)
		{
			MoveResponse response = (MoveResponse) m;
			processMoveResponse(response );
		}
	}
	
	private class CommandYouWin implements Command
	{
		public void process( Message m)
		{
			YouWin response = (YouWin) m;
			processYouWin(response);
		}
	}
	
	private class CommandYouLose implements Command
	{
		public void process( Message m)
		{
			YouLose response = (YouLose) m;
			processYouLose(response);
		}
	}
	
	public GameController( Player player ) {
		requestQueue = new LinkedBlockingQueue<Request>( QUEUE_CAPACITY );
		responseQueue = new LinkedBlockingQueue<Response>( QUEUE_CAPACITY );
		methodMap = new HashMap<String, Command>();
		methodMap.put(StartResponse.class.getSimpleName(), new CommandStartResponse());
		methodMap.put(StopResponse.class.getSimpleName(), new CommandStopResponse());
		methodMap.put(WaitResponse.class.getSimpleName(), new CommandWaitResponse());
		methodMap.put(OpponentMovedResponse.class.getSimpleName(), new CommandOpponentMovedResponse());
		methodMap.put(MoveResponse.class.getSimpleName(), new CommandMoveResponse());
		methodMap.put(YouWin.class.getSimpleName(), new CommandYouWin());
		methodMap.put(YouLose.class.getSimpleName(), new CommandYouLose());
		//--------------------------
		waitingForAction = false;
		gameOver=false;
		player.setIsMyTurn(true);
		this.player = player;
	}
	
	public BlockingQueue<Request>  getRequestQueue (){
		return this.requestQueue;
	}
	
	public BlockingQueue<Response>  getResponseQueue (){
		return this.responseQueue;
	}
	
	private void processStartResponse( StartResponse msg) {
		
		player.setID( msg.getPlayerId() );
		if( msg.getPlayerId() == 2 ) {
			player.setIsMyTurn( true );
			waitingForAction = true;
			//można tu wyświetlić jakaś wiadomość żeby się ruszył
		}else {
			player.setIsMyTurn( false);
			waitingForAction = false;
		}
	}
	
	
	private void processStopResponse( StopResponse msg) {
		
		gameOver = true;
	}

	private void processWaitResponse( WaitResponse msg) {
		
	}
	
	private void processOpponentMovedResponse( OpponentMovedResponse msg) {
		
		player.setIsMyTurn(true);
		waitingForAction = true;
		updateReceivedInfo(msg.getFrom(), msg.getTo());
	}
	
	private void processYouWin( YouWin msg) {
		
		gameOver = true;
		//wyświetlić że wygrał
	}
	
	private void processMoveResponse( MoveResponse msg) {
		
		player.setIsMyTurn(false);
		
	}
	
	private void processYouLose( YouLose msg) {
		
		updateReceivedInfo(msg.getFrom(), msg.getTo());
		gameOver = true;
		//można wyświetlić mu że przegrał
	}
	
	@Override 
	public void run( ) {

		while( !gameOver ) {
			try {
				
				if(waitingForAction)
					waitForPlayerAction();
				
				if(!player.getIsMyTurn())
					askForOpponent();
				
				processResponse();
			}catch(Exception e) {
				e.printStackTrace();
				System.exit(-1);
			}
		}
	}
	
	private void askForOpponent() throws InterruptedException{
		
		if( requestQueue.isEmpty() ) {
			GetOpponentEvent request = new GetOpponentEvent(player.getID());
			requestQueue.add(request);
		}
		
	}

	private void waitForPlayerAction() throws InterruptedException {
		
		while(waitingForAction){
			Thread.sleep(100);
		}
		
	}
	
	//called by a listener
	private void makeMove( int from, int to ) {
		Move move = new Move( player.getID(),from, to );
		requestQueue.add(move);	
	}
	
	public void makeStart( ) {
		
		Start start = new Start( 0 );
		requestQueue.add(start);
	}
	
	private void makeStop() {
		
		//waitforaction = false;
		Stop stop = new Stop( player.getID()  );
		requestQueue.add(stop);
	}
	
	public void processResponse() throws InterruptedException {
		
		Response msg = null;
		msg =responseQueue.poll(1, TimeUnit.SECONDS);
		if( msg != null ) {
			methodMap.get( msg.getClass().getSimpleName() ).process( msg );
			
		}


	}
	
	//------------------------------
	private void updateReceivedInfo( int from, int to ) {
		
	}
	
}
