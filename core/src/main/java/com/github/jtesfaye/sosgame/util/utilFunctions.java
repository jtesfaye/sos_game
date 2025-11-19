package com.github.jtesfaye.sosgame.util;

import com.github.jtesfaye.sosgame.GameObject.Piece;

import java.util.ArrayList;
import java.util.Arrays;

public class utilFunctions {

    public static Pair<Integer, Integer> getBoardDimensions(String dim) {

        String[] vals = dim.split("x");

        int width = Integer.parseInt(vals[0]);
        int height = Integer.parseInt(vals[1]);

        return new Pair<> (width, height);
    }

    public static char[][] boardToCharArr(Piece[][] board) {

        assert(board != null);

        int row = board.length;
        int col = board[0].length;

        char[][] newBoard = new char[row][col];

        for (int i = 0; i < row; i++){
            for (int j = 0; i < col; j++) {
                newBoard[i][j] = board[i][j].toString().charAt(0);
            }
        }

        return newBoard;

    }

    public static ArrayList<Pair<Integer, Integer>> getOpenPositions(Piece[][] board) {

        assert(board != null);

        ArrayList<Pair<Integer, Integer>> arr = new ArrayList<>();
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j].equals(Piece.OPEN)) {
                    arr.add(new Pair<>(i,j));
                }
            }
        }

        return arr;

    }

    public static Piece[][] copyBoard(Piece[][] board) {


        assert(board != null);


        int row = board.length;
        int col = board[0].length;

        assert(col > 0);

        Piece[][] boardCopy = new Piece[row][col];

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                boardCopy[i][j] = board[i][j];
            }
        }

        return boardCopy;

    }
}
