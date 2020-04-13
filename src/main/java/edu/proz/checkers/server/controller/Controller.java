package edu.proz.checkers.server.controller;

import java.util.HashMap;
import java.util.Map;

import edu.proz.checkers.client.controller.*;

import edu.proz.checkers.infrastructure.*;

interface Command {	
	Response process( Message msg );
}


public class Controller{
	
	private Map<String, Command> methodMap = null;
	
	public Controller( ) {
		methodMap = new HashMap<String, Command>();
		methodMap.put(StartResponse.class.getSimpleName(), new CommandStart());
		methodMap.put(MoveResponse.class.getSimpleName(), new CommandMove());
		methodMap.put(StopResponse.class.getSimpleName(), new CommandStop());
		methodMap.put(GetOpponentEvent.class.getSimpleName(), new CommandGetOpponentEvent());
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
		
	}
	
	private Response processStart( Start msg ) {
		
	}
	
	private Response processStop( Stop msg ) {
		
	}
	
	private Response processGetOpponentEvent( GetOpponentEvent msg ) {
		
	}
	
	public Response getResponse( Request request ) {
				
		return methodMap.get( request.getClass().getSimpleName() ).process( request );
		
	}
}
