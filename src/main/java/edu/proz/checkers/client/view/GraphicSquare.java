package edu.proz.checkers.client.view;

import edu.proz.checkers.Constants;
import edu.proz.checkers.client.PlayerIDHolder;
import edu.proz.checkers.client.model.Square;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import javax.swing.JPanel;

/**
 * Class that represents single square in GUI.
 */
public class GraphicSquare extends JPanel {

    // square from model
    private Square square;

    // boolean variable that tells if the pawn on this square changed color
    private boolean toggled;

    // font of the K letter
    private final Font kingFont = new Font("Arial", Font.BOLD, 25);

    // letter K on the king pawn
    private final String kingLetter = "K";

    /**
     * Constructor that assigns the square from model to graphic square and sets toggled flag false.
     * @param square Square from model.
     */
    public GraphicSquare(Square square) {
        this.square = square;
        this.toggled = false;
    }

    /**
     * Method used to paint the squares and pawns if the squares contain them.
     * @param g Special object that makes painting possible.
     */
    protected void paintComponent(Graphics g) {
        Graphics2D g2D = (Graphics2D)g;
        super.paintComponents(g2D);

        // drawing square
        if (square.getIsAvailableForPawns())
            g2D.setColor(Colors.AVAILABLE_FOR_PAWNS.getColor());
        else
            g2D.setColor(Colors.NOT_AVAILABLE_FOR_PAWNS.getColor());

        Rectangle2D rect = new Rectangle(0, 0, getWidth(), getHeight());
        g2D.fill(rect);

        // drawing pawn
        int ownerID = square.getPlayerID();
        if (isClicked()) {
            g2D.setColor(Colors.getToggledPawnColor(ownerID));
            drawPawn(g2D);
        }
        else {
            if (ownerID == Constants.PLAYER_ONE_ID.getValue() || ownerID == Constants.PLAYER_TWO_ID.getValue()) {
                if (toggled)
                    g2D.setColor(Colors.getToggledPawnColor(ownerID));
                else
                    g2D.setColor(Colors.getDefaultPawnColor(ownerID));

                drawPawn(g2D);
            }
        }

        // king case
        if (square.getIsAvailableForPawns() && square.isKing()) {
            // drawing gold K letter
            g2D.setColor(Colors.KING_LETTER.getColor());
            g2D.setFont(kingFont);
            g2D.drawString(kingLetter, getWidth() / 2 - 8, getHeight() / 2 + 8);
        }
    }

    /**
     * Method that sets two listeners:
     * - the listener that is responsible for mouse motions in the square area,
     * - the listener that is responsible for mouse clicks in the square area.
     * @param MyListener listener 
     */
    public void setListener(SquareMouseListener MyListener) {
        if (square.getIsPossibleToMove() || square.getPlayerID() == PlayerIDHolder.PLAYER_ID.getValue()) {
            this.removeMouseListener(MyListener);
            this.addMouseListener(MyListener);
        }
        else
            this.removeMouseListener(MyListener);
    }

    /**
     * Method that returns square from model that represents our view square.
     * @return Square from model.
     */
    public Square getSquare() {
        return this.square;
    }

    /**
     * Method that returns boolean value that indicates if the GUI square is clicked.
     * @return Boolean value that indicates if the GUI square is clicked.
     */
    public boolean isClicked() {
        return this.square.getIsSelected();
    }

    // method that draws pawns' oval shapes
    private void drawPawn(Graphics2D g2D) {
        int position = (getWidth() + getHeight()) / 8;
        g2D.fillOval(position / 2, position / 2, getWidth() - position, getHeight() - position);
    }

    /**
     * Method that sets the toggled flag in graphic square.
     * @param toggled Boolean value that will be assigned to the toggled flag in graphic square.
     */
    public void setToggled(boolean toggled) {
        this.toggled = toggled;
    }
}
