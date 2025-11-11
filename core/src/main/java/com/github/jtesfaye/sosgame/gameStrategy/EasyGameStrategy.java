package com.github.jtesfaye.sosgame.gameStrategy;

import com.github.jtesfaye.sosgame.GameObject.Move;
import com.github.jtesfaye.sosgame.GameObject.Piece;

import java.util.Random;

public class EasyGameStrategy implements GameStrategy {

    @Override
    public Move makeMove(Piece[][] board) {

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
