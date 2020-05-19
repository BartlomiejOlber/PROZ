package edu.proz.checkers.server.controller;

import java.io.IOException;
import java.util.HashMap;

import edu.proz.checkers.server.infrastructure.SessionConnectionController;
import edu.proz.checkers.server.model.*;


import java.util.Map;

import edu.proz.checkers.Constants;
import edu.proz.checkers.infrastructure.*;

interface Command {	
	Response process( Message msg );
}


public class SessionController implements Runnable{
	
	private Map<String, Command> methodMap;
	
	private SessionConnectionController connectionContoller;
	private Board board;
	
	private boolean gameIsOn;
	private boolean[] playerMoved;
	private int playersStarted;
	private int from;
	private int to;

	
	public SessionController( SessionConnectionController scc) {
		methodMap = new HashMap<String, Command>();
		methodMap.put(Start.class.getSimpleName(), new CommandStart());
		methodMap.put(Move.class.getSimpleName(), new CommandMove());
		methodMap.put(Stop.class.getSimpleName(), new CommandStop());
		methodMap.put(GetOpponentEvent.class.getSimpleName(), new CommandGetOpponentEvent());
		board = new Board();
		playersStarted = 0;
		playerMoved = new boolean[2];
		gameIsOn = true;
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
			e.printStackTrace();
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
		from = msg.getFrom();
		to = msg.getTo();
		
		if(!board.isOn()) {
			response = new YouWin(msg.getPlayerId());
		}else {
			response = new MoveResponse(msg.getPlayerId());
		}
		playerMoved[msg.getPlayerId() - 1] = true;
		return response;
		
	}
	
	private Response processStart( Start msg ) {
	
		playersStarted++;
		StartResponse response = new StartResponse(playersStarted);		
		return response;
	
	}
	
	private Response processStop( Stop msg ) {
		gameIsOn = false;
		return new StopResponse(msg.getPlayerId());
	}
	
	private Response processGetOpponentEvent( GetOpponentEvent msg ) {
		
		Response response = null;
		if(playerMoved[msg.getPlayerId()%2]) {
			if(board.isOn()){
				
				response = new OpponentMovedResponse(msg.getPlayerId(), from, to);
				playerMoved[msg.getPlayerId()%2] = false;
				
			}else {
				
				response = new YouLose(msg.getPlayerId(), from, to );
				gameIsOn = false;
			}
			
		}else {
			response = new WaitResponse(msg.getPlayerId());
		}
		return response;
		
	}
	
	public Response getResponse( Request request ) {
				
		return methodMap.get( request.getClass().getSimpleName() ).process( request );
		
	}
	
	
	
	private void updateBoard(int from, int to){
		Square fromSquare = board.getSquare(from);
		Square toSquare = board.getSquare(to);
		toSquare.setPlayerId(fromSquare.getPlayerId());
		fromSquare.setPlayerId(Constants.SQUARE_NOT_OCCUPIED.getValue());		
		checkJump(from, to);
	}
	
	private void checkJump(int from, int to){	
		
		if(Math.abs(to - from)>9){				
			Square removedSquare = board.getSquare( from + (to - from)/2 );
			removedSquare.setPlayerId(Constants.SQUARE_NOT_OCCUPIED.getValue());			
		}
	}
}
