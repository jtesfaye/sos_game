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

    /**
     * This function checks if there is a winner. It does not determine who the winner is.
     */
    public abstract boolean isWinner();

    /**
     * This function determines which player won the game. It is only called if isWinner returns true
     */
    public abstract Player getWinner();


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

                    if (board[row + d[0]][col + d[1]].equals(Piece.oPiece)
                        && board[row + 2 * d[0]][col + 2 * d[1]].equals(Piece.sPiece)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    protected boolean isVertical(int row, int col) {


        if (board[row][col].equals(Piece.oPiece)) {

            if (checkBounds(row + 1, col) && checkBounds(row - 1, col)) {
                System.out.printf("Here %s %s \n", row + 1, col);
                if (board[row + 1][col].equals(Piece.sPiece)
                    && board[row - 1][col].equals(Piece.sPiece)) {
                    System.out.println("Bar");
                    return true;
                }
            }
        }

        if (board[row][col].equals(Piece.sPiece)) {

            int[][] dir = {{1,0}, {-1, 0}};

            for (int[] d : dir) {

                if (checkBounds(row + d[0], col) && checkBounds(row + 2*d[0], col)) {

                    if (board[row + d[0]][col].equals(Piece.oPiece)
                        && board[row + 2 * d[0]][col].equals(Piece.sPiece)) {
                        System.out.println("Foo");
                        return true;
                    }
                }
            }
        }
        return false;
    }

    protected  boolean isHorizontal(int r, int c) {
        return false;
    }

}
