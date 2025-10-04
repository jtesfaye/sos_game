package com.github.jtesfaye.sosgame.GameLogic;

import com.github.jtesfaye.sosgame.util.Pair;

public abstract class GameLogic {

    public enum State {
        OPEN,
        sPiece,
        oPiece
    }

    public enum Player {
        Player1, Computer
    }

    protected int player1SOSCount;
    protected int computerSOSCount;

    protected int boardRow;
    protected int boardHeight;
    protected final State[][] board;
    protected int capacity;

    protected Pair<Integer, Integer> mostRecentPiece;

    protected GameLogic(int r, int h) {

        boardRow = r;
        boardHeight = h;
        capacity = r * h;

        board = new State[r][h];

        for (int i  = 0; i < r; i++) {
            for (int j = 0; j < h; j++) {
                board[i][j] = State.OPEN;
            }
        }

    }

    /**
     * This function checks if there is a winner. It does not determine who the winner is.
     */
    public abstract boolean isWinner();

    /**
     * This function determines which player won the game. It is only called if isWinner returns true
     */
    public abstract Player getWinner();

    public boolean isOpen(int r, int c) {

        if (r >= boardRow || c >= boardHeight) {
            return false;
        }

        return board[r][c] == State.OPEN;
    }

    public boolean setPiece(int r, int c, State piece) {

        if (!isOpen(r, c))
            return false;

        board[r][c] = piece;

        mostRecentPiece = new Pair<>(r,c);

        checkSOS(r, c);

        capacity += 1;
        System.out.printf("%s, %s: %s \n", r, c, board[r][c]);
        return true;

    }

    public State getPiece(int r, int c) {

        if (r >= boardRow || c >= boardHeight) {
            return State.OPEN;
        }

        return board[r][c];
    }

    protected boolean checkSOS(int r, int c) {

        if (this.isDiagonal(r, c))
            return true;

        if (this.isHorizontal(r, c))
            return true;

        return this.isVertical(r, c);
    }


    protected boolean isDiagonal(int r, int c) {


        return false;
    }

    protected boolean isVertical(int r, int c) {
        return false;
    }

    protected  boolean isHorizontal(int r, int c) {
        return false;
    }

}
