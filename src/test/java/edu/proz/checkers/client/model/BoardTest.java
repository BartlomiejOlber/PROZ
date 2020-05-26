package edu.proz.checkers.client.model;

import edu.proz.checkers.Constants;
import edu.proz.checkers.client.PlayerIDHolder;

import java.util.LinkedList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
public class BoardTest {

    private Board testBoard = new Board();

    @BeforeEach
    public void initializePlayerID() {
        PlayerIDHolder.PLAYER_ID.setValue(Constants.PLAYER_ONE_ID.getValue());
    }

    @Test
    public void getAllSquaresOnTheBoardTest_Amount() {
        // given
        int numberOfRows = testBoard.getAllSquaresOnBoard().length;
        int numberOfCols = testBoard.getAllSquaresOnBoard()[0].length;

        // when + then
        assertEquals(Constants.NUMBER_OF_ROWS.getValue() *
                Constants.NUMBER_OF_COLS.getValue(), numberOfRows * numberOfCols);
    }

    @Test
    public void getAllSquaresOnTheBoardTest_NumberOfRowsAndCols() {
        // given
        int numberOfRows = testBoard.getAllSquaresOnBoard().length;
        int numberOfCols = testBoard.getAllSquaresOnBoard()[0].length;

        // when + then
        assertEquals(Constants.NUMBER_OF_ROWS.getValue(), numberOfRows);
        assertEquals(Constants.NUMBER_OF_COLS.getValue(), numberOfCols);
    }

    @Test
    public void returnSquaresPossibleToMoveTest_TwoFrontSquaresEmpty() {
        // given
        Square testArr[][] = testBoard.getAllSquaresOnBoard();

        // when
        LinkedList<Square> returnedSquares = testBoard.returnSquaresPossibleToMove(testArr[2][1]);

        // then
        // the first returned square should have ID = 27, rowNumber = 3, colNumber = 2, isAvailableForPawns = true
        assertEquals(27, returnedSquares.getFirst().getID());
        assertEquals(3, returnedSquares.getFirst().getRowNumber());
        assertEquals(2, returnedSquares.getFirst().getColNumber());
        assertEquals(true, returnedSquares.getFirst().getIsAvailableForPawns());

        // the second returned square should have ID = 25, rowNumber = 3, colNumber = 0, isAvailableForPawns = true
        assertEquals(25, returnedSquares.get(1).getID());
        assertEquals(3, returnedSquares.get(1).getRowNumber());
        assertEquals(0, returnedSquares.get(1).getColNumber());
        assertEquals(true, returnedSquares.get(1).getIsAvailableForPawns());
    }

    @Test
    public void returnSquaresPossibleToMoveTest_TwoFrontSquaresOccupiedByTheSamePlayer() {
        // given
        Square testArr[][] = testBoard.getAllSquaresOnBoard();

        // when + then
        assertTrue(testBoard.returnSquaresPossibleToMove(testArr[1][2]).isEmpty());
    }

    @Test
    public void returnSquaresPossibleToMoveTest_TwoFrontSquaresOccupiedByTheOpponent() {
        // given
        Square testArr[][] = testBoard.getAllSquaresOnBoard();

        testArr[4][1].setPlayerID(Constants.PLAYER_ONE_ID.getValue());

        // when + then
        assertTrue(testBoard.returnSquaresPossibleToMove(testArr[4][1]).isEmpty());
    }

    @Test
    public void returnSquaresPossibleToMoveTest_TwoFrontCrossJumpsPossible() {
        // given
        Square testArr[][] = testBoard.getAllSquaresOnBoard();

        testArr[3][2].setPlayerID(Constants.PLAYER_TWO_ID.getValue());
        testArr[3][4].setPlayerID(Constants.PLAYER_TWO_ID.getValue());

        // when
        LinkedList<Square> returnedSquares = testBoard.returnSquaresPossibleToMove(testArr[2][3]);

        // then
        // the first returned square should have ID = 38, rowNumber = 4, colNumber = 5, isAvailableForPawns = true
        assertEquals(38, returnedSquares.getFirst().getID());
        assertEquals(4, returnedSquares.getFirst().getRowNumber());
        assertEquals(5, returnedSquares.getFirst().getColNumber());
        assertEquals(true, returnedSquares.getFirst().getIsAvailableForPawns());

        // the second returned square should have ID = 34, rowNumber = 4, colNumber = 1, isAvailableForPawns = true
        assertEquals(34, returnedSquares.get(1).getID());
        assertEquals(4, returnedSquares.get(1).getRowNumber());
        assertEquals(1, returnedSquares.get(1).getColNumber());
        assertEquals(true, returnedSquares.get(1).getIsAvailableForPawns());
    }

    @Test
    public void returnSquaresPossibleToMoveTest_TwoBackCrossJumpsPossible() {
        // given
        Square testArr[][] = testBoard.getAllSquaresOnBoard();

        testArr[4][3].setPlayerID(Constants.PLAYER_ONE_ID.getValue());
        testArr[2][1].setPlayerID(Constants.SQUARE_NOT_OCCUPIED.getValue());
        testArr[2][5].setPlayerID(Constants.SQUARE_NOT_OCCUPIED.getValue());

        testArr[3][2].setPlayerID(Constants.PLAYER_TWO_ID.getValue());
        testArr[3][4].setPlayerID(Constants.PLAYER_TWO_ID.getValue());

        // when
        LinkedList<Square> returnedSquares = testBoard.returnSquaresPossibleToMove(testArr[4][3]);

        // then
        // the first returned square should have ID = 22, rowNumber = 2, colNumber = 5, isAvailableForPawns = true
        assertEquals(22, returnedSquares.getFirst().getID());
        assertEquals(2, returnedSquares.getFirst().getRowNumber());
        assertEquals(5, returnedSquares.getFirst().getColNumber());
        assertEquals(true, returnedSquares.getFirst().getIsAvailableForPawns());

        // the second returned square should have ID = 18, rowNumber = 2, colNumber = 1, isAvailableForPawns = true
        assertEquals(18, returnedSquares.get(1).getID());
        assertEquals(2, returnedSquares.get(1).getRowNumber());
        assertEquals(1, returnedSquares.get(1).getColNumber());
        assertEquals(true, returnedSquares.get(1).getIsAvailableForPawns());
    }

    @Test
    public void returnSquaresPossibleToMoveTest_KingCanMoveInFourAdjacentEmptySquares() {
        // given
        Square testArr[][] = testBoard.getAllSquaresOnBoard();

        testArr[4][3].setPlayerID(Constants.PLAYER_ONE_ID.getValue());
        testArr[4][3].setKing();

        testArr[5][2].setPlayerID(Constants.SQUARE_NOT_OCCUPIED.getValue());
        testArr[5][4].setPlayerID(Constants.SQUARE_NOT_OCCUPIED.getValue());

        // when
        LinkedList<Square> returnedSquares = testBoard.returnSquaresPossibleToMove(testArr[4][3]);

        // then
        // the first returned square should have ID = 45, rowNumber = 5, colNumber = 4, isAvailableForPawns = true
        assertEquals(45, returnedSquares.getFirst().getID());
        assertEquals(5, returnedSquares.getFirst().getRowNumber());
        assertEquals(4, returnedSquares.getFirst().getColNumber());
        assertEquals(true, returnedSquares.getFirst().getIsAvailableForPawns());

        // the second returned square should have ID = 43, rowNumber = 5, colNumber = 2, isAvailableForPawns = true
        assertEquals(43, returnedSquares.get(1).getID());
        assertEquals(5, returnedSquares.get(1).getRowNumber());
        assertEquals(2, returnedSquares.get(1).getColNumber());
        assertEquals(true, returnedSquares.get(1).getIsAvailableForPawns());

        // the third returned square should have ID = 29, rowNumber = 3, colNumber = 4, isAvailableForPawns = true
        assertEquals(29, returnedSquares.get(2).getID());
        assertEquals(3, returnedSquares.get(2).getRowNumber());
        assertEquals(4, returnedSquares.get(2).getColNumber());
        assertEquals(true, returnedSquares.get(2).getIsAvailableForPawns());

        // the fourth returned square should have ID = 27, rowNumber = 3, colNumber = 2, isAvailableForPawns = true
        assertEquals(27, returnedSquares.get(3).getID());
        assertEquals(3, returnedSquares.get(3).getRowNumber());
        assertEquals(2, returnedSquares.get(3).getColNumber());
        assertEquals(true, returnedSquares.get(3).getIsAvailableForPawns());
    }

    @Test
    public void returnSquaresPossibleToMoveTest_DefaultPawnCanOnlyMoveInTwoSquaresWhenFourAdjacentEmpty() {
        // given
        Square testArr[][] = testBoard.getAllSquaresOnBoard();

        testArr[4][3].setPlayerID(Constants.PLAYER_ONE_ID.getValue());

        testArr[5][2].setPlayerID(Constants.SQUARE_NOT_OCCUPIED.getValue());
        testArr[5][4].setPlayerID(Constants.SQUARE_NOT_OCCUPIED.getValue());

        // when
        LinkedList<Square> returnedSquares = testBoard.returnSquaresPossibleToMove(testArr[4][3]);

        // then
        // the first returned square should have ID = 45, rowNumber = 5, colNumber = 4, isAvailableForPawns = true
        assertEquals(45, returnedSquares.getFirst().getID());
        assertEquals(5, returnedSquares.getFirst().getRowNumber());
        assertEquals(4, returnedSquares.getFirst().getColNumber());
        assertEquals(true, returnedSquares.getFirst().getIsAvailableForPawns());

        // the second returned square should have ID = 43, rowNumber = 5, colNumber = 2, isAvailableForPawns = true
        assertEquals(43, returnedSquares.get(1).getID());
        assertEquals(5, returnedSquares.get(1).getRowNumber());
        assertEquals(2, returnedSquares.get(1).getColNumber());
        assertEquals(true, returnedSquares.get(1).getIsAvailableForPawns());

        // only two possible squares to move
        assertEquals(2, returnedSquares.size());
    }

    @Test
    public void returnSquaresPossibleToMoveTest_PawnsCannotMoveOutsideTheBoardOpponentSide() {
        // given
        Square testArr[][] = testBoard.getAllSquaresOnBoard();

        testArr[7][2].setPlayerID(Constants.PLAYER_ONE_ID.getValue());

        // when + then
        assertTrue(testBoard.returnSquaresPossibleToMove(testArr[7][2]).isEmpty());
    }

    @Test
    public void returnSquaresPossibleToMoveTest_PawnsCannotMoveOutsideTheBoardPlayersSide() {
        // given
        Square testArr[][] = testBoard.getAllSquaresOnBoard();

        testArr[0][3].setKing();

        // when + then
        assertTrue(testBoard.returnSquaresPossibleToMove(testArr[0][3]).isEmpty());
    }

    @Test
    public void returnSquaresPossibleToMoveTest_PawnsCannotCrossJumpOutsideTheBoardPlayersSide() {
        // given
        Square testArr[][] = testBoard.getAllSquaresOnBoard();

        testArr[0][3].setPlayerID(Constants.PLAYER_TWO_ID.getValue());

        // when + then
        assertTrue(testBoard.returnSquaresPossibleToMove(testArr[1][2]).isEmpty());
        assertTrue(testBoard.returnSquaresPossibleToMove(testArr[1][4]).isEmpty());
    }

    @Test
    public void returnSquaresPossibleToMoveTest_PawnsCannotCrossJumpOutsideTheBoardOpponentsSide() {
        // given
        Square testArr[][] = testBoard.getAllSquaresOnBoard();

        testArr[6][3].setPlayerID(Constants.PLAYER_ONE_ID.getValue());
        testArr[4][5].setPlayerID(Constants.PLAYER_TWO_ID.getValue());
        testArr[4][1].setPlayerID(Constants.PLAYER_TWO_ID.getValue());

        // when + then
        assertTrue(testBoard.returnSquaresPossibleToMove(testArr[6][3]).isEmpty());
    }
}