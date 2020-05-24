package edu.proz.checkers.client.view;

import edu.proz.checkers.client.PlayerIDHolder;
import edu.proz.checkers.client.controller.GameController;
import edu.proz.checkers.client.model.Square;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JOptionPane;


/**
 * Class that is responsible for listening to events that happen when we click or put the cursor on squares.
 */
public class SquareMouseListener extends MouseAdapter {

    // graphic square assigned to the listener
    private GraphicSquare graphicSquare;
    private GameController controller;

    /**
     * When player clicks at square and it is his turn, the method runs another function "toggleSelectPiece".
     * If it is not his turn, the function shows message dialog that informs about it.
     * @param e Special object generated when player clicks at some of the squares.
     */
    @Override
    public void mousePressed(MouseEvent e) {
        super.mousePressed(e);
        if (controller.isThePlayersTurnNow())
            setIfPawnIsSelected(e);
        else
            JOptionPane.showMessageDialog(null, "Wait for your turn.",
                    "Move is not possible now", JOptionPane.INFORMATION_MESSAGE, null);
    }

    /**
     * Method responsible for setting the square toggled and repainting it when someone puts the cursor on
     * the square area.
     * @param e Object generated when cursor enters the square area.
     */
    @Override
    public void mouseEntered(MouseEvent e) {
        super.mouseEntered(e);
        graphicSquare = (GraphicSquare) e.getSource();
        Square square = graphicSquare.getSquare();
        if (square.getPlayerID() == PlayerIDHolder.PLAYER_ID.getValue()) {
            graphicSquare.setToggled(true);
            graphicSquare.repaint();
        }
    }

    /**
     * Method responsible for setting toggled flag false and repainting the square when someone puts the cursor on
     * the square area.
     * @param e Object generated when cursor leaves the square area.
     */
    @Override
    public void mouseExited(MouseEvent e) {
        super.mouseExited(e);
        graphicSquare = (GraphicSquare) e.getSource();
        Square square = graphicSquare.getSquare();
        if (square.getPlayerID() == PlayerIDHolder.PLAYER_ID.getValue()) {
            graphicSquare.setToggled(false);
            graphicSquare.repaint();
        }
    }

    /*
     * Method responsible for making pawn selected when player clicks on the square in which the pawn is.
     * @param e Object generated when mouse event happens.
     */
    private void setIfPawnIsSelected(MouseEvent e) {
        graphicSquare = (GraphicSquare)e.getSource();
        Square square = graphicSquare.getSquare();

        // clear if the square is already selected, else select it
        if (square.getIsSelected())
            controller.clearSelectedAndPossibleToMove();
        else
            controller.selectTheSquare(square);
    }

    /**
     * Method used to set the controller for mouse listener.
     * @param controller Controller to be set.
     */
    public void setController(GameController controller) {
        this.controller = controller;
    }
}


