package edu.proz.checkers.server.controller;

import java.io.IOException;
import java.util.HashMap;

import edu.proz.checkers.server.infrastructure.SessionConnectionController;
import edu.proz.checkers.server.model.*;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import edu.proz.checkers.client.controller.*;

import edu.proz.checkers.infrastructure.*;

interface Command {	
	Response process( Message msg );
}


public class SessionController implements Runnable{
	
	private Map<String, Command> methodMap;
	private int sessionId;
	
	private SessionConnectionController connectionContoller;
	private Board board;
	
	private boolean gameIsOn;
	private boolean eventHappened;
	private int winner;
	private int playersStarted;
	private int from;
	private int to;
	private static final int PLAYER_ONE = 1;
	private static final int PLAYER_TWO = 2;

	
	public SessionController( SessionConnectionController scc) {
		methodMap = new HashMap<String, Command>();
		methodMap.put(Start.class.getSimpleName(), new CommandStart());
		methodMap.put(Move.class.getSimpleName(), new CommandMove());
		methodMap.put(Stop.class.getSimpleName(), new CommandStop());
		methodMap.put(GetOpponentEvent.class.getSimpleName(), new CommandGetOpponentEvent());
		board = new Board();
		playersStarted = 0;
		winner = 0;
		eventHappened = false;
		
		this.connectionContoller = scc;
		
	}
	
	@Override
	public void run() {
		try {
			while( gameIsOn ) {				
				Request request = connectionContoller.getRequest( );
				Response response = getResponse( request );
				connectionContoller.sendResponse(response);
		
			}
			
		}catch( IOException e ) {
			
		}
	}
	
	private class CommandStart implements Command
	{
		public Response process( Message m)
		{
			Start start = (Start) m;
			return processStart(start);
		}
	}
	
	private class CommandMove implements Command
	{
		public Response process( Message m)
		{
			Move move = (Move) m;
			return processMove(move);
		}
	}
	
	private class CommandStop implements Command
	{
		public Response process( Message m)
		{
			Stop stop = (Stop) m;
			return processStop(stop);
		}
	}
	
	private class CommandGetOpponentEvent implements Command
	{
		public Response process( Message m)
		{
			GetOpponentEvent get = (GetOpponentEvent) m;
			return processGetOpponentEvent(get);
		}
	}
	
	private Response processMove( Move msg ) {
		
		Response response = null;
		
		updateBoard(msg.getFrom(), msg.getTo());
		eventHappened = true;
		this.from = msg.getFrom();
		this.to = msg.getTo();
		gameIsOn = board.isOn();
		if(!gameIsOn) {
			winner = msg.getPlayerId();
			response = new YouWin(msg.getPlayerId());
		}else {
			response = new MoveResponse(msg.getPlayerId());
		}
		
		return response;
		
	}
	
	private Response processStart( Start msg ) {
	
		playersStarted++;
		StartResponse response = new StartResponse(playersStarted);		
		return response;
	
	}
	
	private Response processStop( Stop msg ) {
		
		gameIsOn = false;
		eventHappened = true;
		if( msg.getPlayerId() == 1 ) {
			winner = 2;
		}else {
			winner = 1;
		}		
		StopResponse response = new StopResponse(msg.getPlayerId());	
		return response;

		
	}
	
	private Response processGetOpponentEvent( GetOpponentEvent msg ) {
		
		Response response = null;
		if(eventHappened) {
			if(gameIsOn){
				
				response = new OpponentMovedResponse(msg.getPlayerId(),this.from, this.to);
				
			}else {
				
				response = new YouLose(msg.getPlayerId(), from, to);
			}
			
		}else {
			response = new WaitResponse(msg.getPlayerId());
		}
		return response;
		
	}
	
	private Response getResponse( Request request ) {
				
		return methodMap.get( request.getClass().getSimpleName() ).process( request );
		
	}
	
	
	
	private void updateBoard(int from, int to){
		Square fromSquare = board.getSquare(from);
		Square toSquare = board.getSquare(to);
		toSquare.setPlayerId(fromSquare.getPlayerId());
	//	fromSquare.setPlayerID(ENUJM JAKIS EMPTY);		
		checkJump(from, to);
	}
	
	private void checkJump(int from, int to){	
		
		if(Math.abs(to - from)>9){				
			Square removedSquare = board.getSquare( from + (to - from)/2 );
			//removedSquare.setPlayerID(ENUM EMPTY);		
		}
	}
}
