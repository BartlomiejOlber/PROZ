package edu.proz.checkers.client.view;

import edu.proz.checkers.client.model.Constants;
import java.awt.Color;

/**
 * Class that contains all the constant colors used in GUI.
 */
public enum Colors {
    /**
     * Color of pawns that belong to player number 1.
     */
    P1_PAWN_DEFAULT(Color.RED),

    /**
     * Color of pawns that belong to player number 2.
     */
    P2_PAWN_DEFAULT(Color.WHITE),

    /**
     * Color that player 1's pawns get when we hover over them with the mouse or click at them.
     */
    P1_PAWN_TOGGLED(new Color(128,0,0)),

    /**
     * Color that player 2's pawns get when we hover over them with the mouse or click at them.
     */
    P2_PAWN_TOGGLED(Color.GRAY),

    /**
     * Constant color used to paint the squares that you can move around during the game.
     */
    AVAILABLE_FOR_PAWNS(Color.BLACK),

    /**
     * Constant color used to paint the squares that you can not move around during the game.
     */
    NOT_AVAILABLE_FOR_PAWNS(new Color(245, 245, 245)),

    /**
     * Constant color used to paint the 'K' letter on the king pawns.
     */
    KING_LETTER(new Color(241, 184, 45));

    /**
     * Color that each constant represents.
     */
    private Color color;

    /**
     * Private constructor. We do not want to let someone create more constants in code.
     * @param color Color that each constant represents.
     */
    private Colors(Color color) {
        this.color = color;
    }

    /**
     * Function that can be used to get the color value that each constant represents.
     * @return Color that each constant represents.
     */
    public Color getColor() {
        return this.color;
    }

    /**
     * Function that returns default color of pawns that belong to the player.
     * @param PLAYER_ID Constant value that represents ID of the player.
     * @return Default color of pawns that belong to the player.
     */
    public static Color getDefaultPawnColor(int PLAYER_ID) {
        if (PLAYER_ID == Constants.PLAYER_ONE_ID.getValue())
            return P1_PAWN_DEFAULT.getColor();
        else if (PLAYER_ID == Constants.PLAYER_TWO_ID.getValue())
            return P2_PAWN_DEFAULT.getColor();
        return null;
    }

    /**
     * Function that returns color that pawns get when we hover over them with the mouse or click at them.
     * @param PLAYER_ID Constant value that represents ID of the player.
     * @return Color that pawns get when we hover over them with the mouse or click at them.
     */
    public static Color getToggledPawnColor(int PLAYER_ID) {
        if (PLAYER_ID == Constants.PLAYER_ONE_ID.getValue())
            return P1_PAWN_TOGGLED.getColor();
        else if (PLAYER_ID == Constants.PLAYER_TWO_ID.getValue())
            return P2_PAWN_TOGGLED.getColor();
        return null;
    }
}
