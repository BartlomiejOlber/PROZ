package edu.proz.checkers.client.view;

import edu.proz.checkers.client.PlayerIDHolder;
import edu.proz.checkers.client.model.Board;
import edu.proz.checkers.client.model.Square;
import edu.proz.checkers.Constants;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.LinkedList;

import javax.swing.*;

/**
 * Class that represents the whole checker board in GUI.
 */
public class GraphicBoard extends JPanel {

    // size of board in GUI
    private Dimension panelSize = new Dimension(450,450);

    // board from model.
    private Board boardModel;

    // listening mouse clicks for squares.
    private SquareMouseListener listener;

    // list of square GUI panels.
    private LinkedList<GraphicSquare> allGraphicSquaresOnGraphicBoard;

    // 2D array that is containing all the squares from model.
    private Square[][] allSquaresOnBoard;

    public GraphicBoard(SquareMouseListener listener) {

        setPreferredSize(panelSize);

        setLayout(new GridLayout(Constants.NUMBER_OF_ROWS.getValue(),Constants.NUMBER_OF_COLS.getValue()));

        boardModel = new Board();

        // saving all the squares from model
        allSquaresOnBoard = boardModel.getAllSquaresOnBoard();

        this.listener = listener;

        allGraphicSquaresOnGraphicBoard = new LinkedList<GraphicSquare>();

        initializeGraphicSquares();
    }

    /**
     * Method that initializes all the square panels in our GUI board.
     */
    private void initializeGraphicSquares() {
        for (int y = 0; y < Constants.NUMBER_OF_ROWS.getValue(); ++y) {
            for (int x = 0; x < Constants.NUMBER_OF_COLS.getValue(); ++x) {
                GraphicSquare gSquare = new GraphicSquare(allSquaresOnBoard[y][x]);
                // adding listener to the squares on which we can move and which belong to us
                if (gSquare.getSquare().getIsPossibleToMove() || gSquare.getSquare().getPlayerID() == PlayerIDHolder.PLAYER_ID.getValue())
                    gSquare.addMouseListener(listener);

                allGraphicSquaresOnGraphicBoard.add(gSquare);
                add(gSquare);
            }
        }
    }

    /**
     * Method that refreshes listeners and repaints graphic board.
     */
    public void repaintGraphicBoard() {
        for(GraphicSquare graphicSquare : allGraphicSquaresOnGraphicBoard)
            graphicSquare.setListener(listener);

        repaint();
    }

    /**
     * Method that returns all playable squares for given square.
     * @param square Square for which we want to get playable squares.
     * @return List of playable squares for square given as an argument.
     */
    public LinkedList<Square> getSquaresPossibleToMove(Square square) {
        return boardModel.returnSquaresPossibleToMove(square);
    }


    /**
     * Method that returns square with given ID number on the board.
     * @param ID Number of square on the board.
     * @return The square with given ID number on the board.
     */
    public Square getSquare(int ID) {
        // id - 1 because square IDs start from 1 and numeration of squares in list starts from 0
        return allGraphicSquaresOnGraphicBoard.get(ID - 1).getSquare();
    }
}


