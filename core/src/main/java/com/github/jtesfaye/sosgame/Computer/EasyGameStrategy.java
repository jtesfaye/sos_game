package com.github.jtesfaye.sosgame.Computer;

import com.github.jtesfaye.sosgame.GameObject.Move;
import com.github.jtesfaye.sosgame.GameObject.Piece;

import java.util.Random;

public class EasyGameStrategy implements GameStrategy {

    @Override
    public Move makeMove(Piece[][] board) {

        //Simulate thinking
        try {
            Thread.sleep(2500);
        } catch (InterruptedException e) {
            System.out.println("Problem");
        }

        Random random = new Random();
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                if (board[row][col] == Piece.OPEN) {
                    Piece piece = random.nextBoolean() ? Piece.sPiece : Piece.oPiece;
                    return new Move(row, col, piece);
                }
            }
        }
        return null;

    }
}
