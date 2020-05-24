package edu.proz.checkers.client.controller;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import javax.swing.JOptionPane;

import java.util.Map;
import java.util.HashMap;
import java.util.LinkedList;

import edu.proz.checkers.Constants;
import edu.proz.checkers.client.model.*;
import edu.proz.checkers.client.view.GraphicBoard;
import edu.proz.checkers.infrastructure.*;


/**
 * Command pattern created to process abstract messages according to their actual class 
 * @author bartlomiej
 *
 */
interface Command {	
	
	void process( Message msg );
	
}


/**
 * Class responsible for controlling the state of the game; 
 * is runnable as thread - includes the main loop of the game;
 * includes message-processing methods and checkers logic rules;
 * synchronized with runnable ConnectionController class using BlockingQueues
 * 
 *
 */
public class GameController implements Runnable {
	
	private final static int QUEUE_CAPACITY = 16;
	private BlockingQueue<Request> requestQueue;
	private BlockingQueue<Response> responseQueue;
	private Map<String, Command> methodMap;
	private Player player;	
	private boolean waitingForAction;
	private boolean isOver;

	private GraphicBoard graphicBoard;

	private LinkedList<Square> selectedSquares;
	private LinkedList<Square> squaresPossibleToMove;

	/*
	 * 
	 *
	 */
	private class CommandStartResponse implements Command
	{
		public void process( Message m)
		{
			StartResponse start = (StartResponse) m;
			processStartResponse(start);
		}
	}

	public void setGraphicBoard(GraphicBoard graphicBoard){
		this.graphicBoard = graphicBoard;
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
	
	
	/**
	 * GameController constructor; initializes BlockingQueues, methodMap and logic variables
	 * 
	 * @param player checkers logic player
	 */
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
		waitingForAction = false;
		isOver = false;
		this.player = player;
		player.setIsMyTurn(true);
		selectedSquares = new LinkedList<Square>();
		squaresPossibleToMove = new LinkedList<Square>();
	}
	
	/**
	 * Returns initialized Request Queue - used by as an interface between both Controllers' threads
	 * @return BlockingQueue of Requests
	 * @see Request
	 */
	public BlockingQueue<Request>  getRequestQueue (){
		return this.requestQueue;
	}
	
	/**
	 * Returns initialized Response Queue - used by as an interface between both Controllers' threads
	 * @return BlockingQueue of Responses
	 * @see Response
	 */	
	public BlockingQueue<Response>  getResponseQueue (){
		return this.responseQueue;
	}
	
	private void processStartResponse( StartResponse msg) {
		
		player.setID( msg.getPlayerId() );
		if( msg.getPlayerId() ==  Constants.PLAYER_TWO_ID.getValue() ) {
			player.setIsMyTurn( true );
			waitingForAction = true;
		}else {
			player.setIsMyTurn( false);
			waitingForAction = false;
		}
	}
	
	
	private void processStopResponse( StopResponse msg) {
		
		isOver = true;
		JOptionPane.showMessageDialog(null, "You won by walkover",
				"Information", JOptionPane.INFORMATION_MESSAGE, null);
		System.exit(0);
	}

	private void processWaitResponse( WaitResponse msg) {
		
	}
	
	private void processOpponentMovedResponse( OpponentMovedResponse msg) {
		
		player.setIsMyTurn(true);
		waitingForAction = true;
		updateTheBoard(msg.getFrom(), msg.getTo());
	}
	
	private void processYouWin( YouWin msg) {
		
		isOver = true;
		JOptionPane.showMessageDialog(null, "You won the game!",
				"Congratulations", JOptionPane.INFORMATION_MESSAGE, null);
		System.exit(0);
	}
	
	private void processMoveResponse( MoveResponse msg) {
		
		player.setIsMyTurn(false);
		
	}
	
	private void processYouLose( YouLose msg) {
		
		updateTheBoard(msg.getFrom(), msg.getTo());
		isOver = true;
		JOptionPane.showMessageDialog(null, "You lost the game.",
				"Information", JOptionPane.INFORMATION_MESSAGE, null);
		System.exit(0);
	}
	
	/** 
	 * Overrides run method. Includes the main loop of the Controller's thread activity, which is repeatedly waiting for 
	 * users action then sending the message of taken action to ConnectionController through RequestQueue then asking for Response 
	 * from the server and processing it accordingly to its class
	 * @see Request
	 * @see Response
	 * @see java.lang.Runnable#run()
	 */
	@Override 
	public void run( ) {
			
		while( !isOver ) {
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
		
		JOptionPane.showMessageDialog(null, "Game is over",
				"Information", JOptionPane.INFORMATION_MESSAGE, null);
		System.exit(0);
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
	
	private void makeMove( int from, int to ) {
		Move move = new Move( player.getID(),from, to );
		requestQueue.add(move);	
	}
	
	/**
	 * Makes the initial Message to server side and places it on the queue
	 */
	public void makeStart( ) {
		
		Start start = new Start( 0 );
		requestQueue.add(start);
	}
	
	private void makeStop() {
		
		//waitforaction = false;
		Stop stop = new Stop( player.getID()  );
		requestQueue.add(stop);
	}
	
	
	/**
	 * Method responsible for taking a Response from the Response Queue and mapping its class to the proper method to process
	 * a message of this class
	 * @throws InterruptedException Polling on BlockingQueue can throw this exception
	 */
	public void processResponse() throws InterruptedException {
		
		Response msg = null;
		msg =responseQueue.poll(1, TimeUnit.SECONDS);
		if( msg != null ) {
			methodMap.get( msg.getClass().getSimpleName() ).process( msg );
			
		}


	}

	/*
	 * If move performed by the player is a cross jump (we know it because numbers of row that squares
	 * "from" and "to" have fulfill the condition that the absolute value of their difference equals 2),
	 * the function removes the pawn from the beaten square.
	 * @param from Square from which the move starts.
	 * @param to Square on which the move ends.
	 */
	private void crossJumpCaseCheck(Square from, Square to) {
		// checking if the move we make is a cross jump
		if (Math.abs(from.getRowNumber() - to.getRowNumber()) == 2) {
			int middleCol = (from.getColNumber() + to.getColNumber()) / 2;
			int middleRow = (from.getRowNumber() + to.getRowNumber()) / 2;

			Square middleSquare = graphicBoard.getSquare((middleRow * Constants.NUMBER_OF_ROWS.getValue()) +
					middleCol + 1);
			middleSquare.setPlayerID(Constants.SQUARE_NOT_OCCUPIED.getValue());
			if (middleSquare.isKing())
				middleSquare.removeKing();
		}
	}

	/*
	 * Method that is responsible for setting whether a pawn is a king.
	 * @param from Square from which we move.
	 * @param to Square to which we move.
	 */
	private void kingCaseCheck(Square from, Square to) {
		// setting whether the pawn is a king
		if (from.isKing()) {
			to.setKing();
			from.removeKing();
		}
		else if ((to.getRowNumber() == Constants.NUMBER_OF_ROWS.getValue() - 1 &&
				to.getPlayerID() == Constants.PLAYER_ONE_ID.getValue()) ||
				(to.getRowNumber() == 0 && to.getPlayerID() == Constants.PLAYER_TWO_ID.getValue()))
			to.setKing();
	}

	/*
	 * Method that updates player's board after getting infortmation from server.
	 * @param from Number that indicates the square.
	 * @param to Number that indicates the square.
	 */
	private void updateTheBoard(int from, int to) {
		Square fromSquare = graphicBoard.getSquare(from);
		Square toSquare = graphicBoard.getSquare(to);
		toSquare.setPlayerID(fromSquare.getPlayerID());
		fromSquare.setPlayerID(Constants.SQUARE_NOT_OCCUPIED.getValue());
		crossJumpCaseCheck(fromSquare, toSquare);
		kingCaseCheck(fromSquare, toSquare);
		graphicBoard.repaintGraphicBoard();
	}

	/**
	 * Method that clears the list of selected squares and squares possible to move and repaints the graphic board.
	 */
	public void clearSelectedAndPossibleToMove() {
		for (Square square : selectedSquares)
			square.setIsSelected(false);
		selectedSquares.clear();

		for (Square square : squaresPossibleToMove)
			square.setIsPossibleToMove(false);
		squaresPossibleToMove.clear();

		// repainting the board in GUI
		graphicBoard.repaintGraphicBoard();
	}

	/**
	 * Method that allows us to perform the move.
	 * @param from Square from which the move starts.
	 * @param to Square on which the move ends.
	 */
	public void move(Square from, Square to){
		to.setPlayerID(from.getPlayerID());
		from.setPlayerID(Constants.SQUARE_NOT_OCCUPIED.getValue());

		// checking cross jump
		crossJumpCaseCheck(from, to);

		// king case
		kingCaseCheck(from, to);

		// updating two lists of squares: selected and possible to move after making move in model
		// and repainting the graphic board
		clearSelectedAndPossibleToMove();

		// move performed, we don't wait for player's action
		waitingForAction = false;

		makeMove( from.getID(), to.getID()  );
	}

	/*
	 * Method that adds the square to the list of selected squares and refreshes the list of squares possible to move.
	 * @param square Squares to be added.
	 */
	private void addSquareToSelectedSquares(Square square) {
		square.setIsSelected(true);
		selectedSquares.add(square);

		// updating the list of the squares possible to move
		squaresPossibleToMove.clear();
		squaresPossibleToMove = graphicBoard.getSquaresPossibleToMove(square);

		/*DEBUG for(Square square: squaresPossibleToMove){
			System.out.println(square.getID());
		}*/
		// repainting the board in GUI
		graphicBoard.repaintGraphicBoard();
	}

	/**
	 * Method used in mouse listener to update the list of selected squares
	 * and if the player selects the pawn and makes move by choosing appropriate square possible to move,
	 * the function allows him to make move.
	 * @param square Object that represents single square in model.
	 */
	public void selectTheSquare(Square square) {

		if (selectedSquares.isEmpty())
			addSquareToSelectedSquares(square);

			//if one is already selected, check if it is possible move
		else if (selectedSquares.size() >= 1) {
			if (squaresPossibleToMove.contains(square))
				move(selectedSquares.getFirst(), square);
			else {
				clearSelectedAndPossibleToMove();
				addSquareToSelectedSquares(square);
			}
		}
	}

	/**
	 * Method used in mouse listener that allows to check if it is the player's turn now.
	 * @return Boolean value that indicates if it is the player's turn now.
	 */
	public boolean isThePlayersTurnNow() {
		return player.getIsMyTurn();
	}
}
