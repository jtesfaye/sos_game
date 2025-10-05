package com.github.jtesfaye.sosgame.GameLogic;

import com.github.jtesfaye.sosgame.util.Pair;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;

public abstract class GameLogic {

    public enum Piece {

        OPEN("_"),
        sPiece("S"),
        oPiece("O");

        private final String description;

        Piece(String desc) {
            description = desc;
        }

        @Override
        public String toString() {
            return description;
        }
    }

    public enum Player {
        Player1, Computer
    }

    protected int player1SOSCount;
    protected int computerSOSCount;

    protected int boardRow;
    protected int boardCol;
    protected final Piece[][] board;
    protected int capacity;

    protected Pair<Integer, Integer> mostRecentPiece;

    protected GameLogic(int r, int c) {

        boardRow = r;
        boardCol = c;
        capacity = r * c;

        board = new Piece[r][c];

        for (int i  = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                board[i][j] = Piece.OPEN;
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

        if (r >= boardRow || c >= boardCol) {
            return false;
        }

        return board[r][c] == Piece.OPEN;
    }

    public boolean setPiece(int r, int c, Piece piece) {

        if (!isOpen(r, c))
            return false;

        board[r][c] = piece;

        mostRecentPiece = new Pair<>(r,c);

        checkSOS(r, c);

        capacity += 1;
        System.out.printf("%s, %s: %s \n", r, c, board[r][c]);
        return true;

    }

    public Piece getPiece(int r, int c) {

        if (r >= boardRow || c >= boardCol) {
            return Piece.OPEN;
        }

        return board[r][c];
    }

    private boolean checkBounds(int r, int c) {

        return (r >= 0 && r < boardRow) && (c >= 0 && c < boardCol);

    }

    protected boolean checkSOS(int r, int c) {

        if (this.isDiagonal(r, c))
            return true;

        if (this.isHorizontal(r, c))
            return true;

        return this.isVertical(r, c);
    }


    protected boolean isDiagonal(int row, int col) {

        if (board[row][col].equals(Piece.oPiece)) {

            int[][] dir = {{1,1}, {-1, 1}};

            for (int[] d : dir) {

                if (checkBounds( row + d[0], col + d[1]) && checkBounds(row - d[0], col - d[1])) {

                    if(board[row + d[0]][col + d[1]].equals(Piece.sPiece)
                        && board[row - d[0]][col - d[1]].equals(Piece.sPiece)) {
                        return true;
                    }
                }
            }
        }

        if (board[row][col].equals(Piece.sPiece)) {

            int[][] dir = {{1,1}, {-1, 1}, {1, -1}, {-1, -1}};

            for (int[] d : dir) {

                if (checkBounds(row + 2 * d[0], col + 2 * d[1])) {

                    if (board[row + d[0]][col + d[0]].equals(Piece.oPiece)
                        && board[row + 2 * d[0]][col + 2 * d[0]].equals(Piece.sPiece)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    protected boolean isVertical(int r, int c) {
        return false;
    }

    protected  boolean isHorizontal(int r, int c) {
        return false;
    }

}
