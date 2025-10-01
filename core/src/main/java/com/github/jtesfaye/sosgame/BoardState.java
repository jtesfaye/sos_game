package com.github.jtesfaye.sosgame;

public class BoardState {

    public enum State {
        OPEN,
        sPiece,
        oPiece
    }

    private final State[][] board;
    public final int row;
    public final int col;

    public BoardState(int r, int c) {

        row = r;
        col = c;

        board = new State[row][col];

        for (int i = 0; i < row; i ++) {
            for (int j = 0; j < col; j++) {

                board[i][j] = State.OPEN;

            }
        }
    }

    public boolean isOpen(int r, int c) {

        if (r >= row || c >= col) {
            return false;
        }

        return board[r][c] == State.OPEN;
    }

    public boolean setPiece(int r, int c, State piece) {

        if (!isOpen(r, c))
            return false;

        board[r][c] = piece;
        System.out.printf("%s, %s: %s \n", r, c, board[r][c]);
        return true;

    }

    public State getPiece(int r, int c) {

        if (r >= row || c >= col) {
            return State.OPEN;
        }

        return board[r][c];
    }
}
