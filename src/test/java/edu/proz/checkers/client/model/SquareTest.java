package edu.proz.checkers.client.model;

import edu.proz.checkers.Constants;
import edu.proz.checkers.client.PlayerIDHolder;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SquareTest {
    @BeforeEach
    public void initializePlayerID() {
        PlayerIDHolder.PLAYER_ID.setValue(Constants.PLAYER_TWO_ID.getValue());
    }

    @Test
    public void doesTheSquareBelongToTheOpponentTestShouldReturnTrue() {
        // given
        Square testSquare = new Square(2, 0, 1, true);
        testSquare.setPlayerID(Constants.PLAYER_ONE_ID.getValue());

        // when + then
        assertTrue(testSquare.doesTheSquareBelongToTheOpponent());
    }

    @Test
    public void doesTheSquareBelongToTheOpponentTestShouldReturnFalseNotOccupiedSquareCase() {
        // given
        Square testSquare = new Square(2, 0, 1, true);
        testSquare.setPlayerID(Constants.SQUARE_NOT_OCCUPIED.getValue());

        // when + then
        assertFalse(testSquare.doesTheSquareBelongToTheOpponent());
    }

    @Test
    public void doesTheSquareBelongToTheOpponentTestShouldReturnFalseTheSamePlayerCase() {
        // given
        Square testSquare = new Square(2, 0, 1, true);
        testSquare.setPlayerID(Constants.PLAYER_TWO_ID.getValue());

        // when + then
        assertFalse(testSquare.doesTheSquareBelongToTheOpponent());
    }
}
