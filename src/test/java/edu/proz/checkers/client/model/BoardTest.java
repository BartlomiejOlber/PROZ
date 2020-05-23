package edu.proz.checkers.client.model;

import edu.proz.checkers.Constants;
import org.junit.jupiter.api.Assertions;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BoardTest {

    private Board testBoard = new Board();

    @Test
    public void getAllSquaresOnTheBoardAmountTest() {
        // given
        int numberOfRows = testBoard.getAllSquaresOnBoard().length;
        int numberOfCols = testBoard.getAllSquaresOnBoard()[0].length;

        // when + then
        Assertions.assertEquals(Constants.NUMBER_OF_ROWS.getValue() *
                Constants.NUMBER_OF_COLS.getValue(), numberOfRows * numberOfCols);
    }
}
