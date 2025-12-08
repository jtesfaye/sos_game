package com.github.jtesfaye.sosgame.Computer;

import com.github.jtesfaye.sosgame.GameObject.Move;
import com.github.jtesfaye.sosgame.GameObject.Piece;
import com.github.jtesfaye.sosgame.util.Pair;
import com.github.jtesfaye.sosgame.util.utilFunctions;
import com.github.jtesfaye.sosgame.GameLogic.SOSChecker;

import java.util.ArrayList;
import java.util.Random;

public class MediumGameStrategy implements GameStrategy {

    /*
        Iterate through all open spots, and find a spot that can form a SOS,
        if no spot exists, select a random open spot.

     */
    @Override
    public Move makeMove(Piece[][] board) {

        //Simulate thinking
        try {
            Thread.sleep(2500);
        } catch (InterruptedException e) {
            System.out.println("Problem");
        }

        Piece[][] boardCopy = utilFunctions.copyBoard(board);
        assert(boardCopy != board);

        SOSChecker checker = new SOSChecker(boardCopy);
        ArrayList<Pair<Integer, Integer>> openSpots = utilFunctions.getOpenPositions(board);

        if (openSpots.isEmpty()) {
            return null;
        }

        for (Pair<Integer, Integer> rowCol : openSpots) {

            int row = rowCol.first();
            int col = rowCol.second();

            boardCopy[row][col] = Piece.sPiece;

            if (checkforSOS(row, col, boardCopy, checker)) {
                return new Move(row, col, Piece.sPiece);
            }

            boardCopy[row][col] = Piece.oPiece;

            if (checkforSOS(row, col, boardCopy, checker)) {
                return new Move(row, col, Piece.oPiece);
            }
        }

        Random rand = new Random();
        Pair<Integer,Integer> tile = openSpots.get(rand.nextInt(openSpots.size()));
        Piece piece = rand.nextBoolean() ? Piece.sPiece : Piece.oPiece;

        return new Move(tile.first(), tile.second(), piece);
    }

    private boolean checkforSOS(int row, int col, Piece[][] board, SOSChecker checker) {

        Object obj = checker.checkDiagonal(row, col);
        if ((obj = checker.checkDiagonal(row, col)) != null) {
            return true;
        } else if ((obj = checker.checkHorizontal(row, col)) != null) {
            return true;
        } else return (obj = checker.checkVertical(row, col)) != null;
    }
}
