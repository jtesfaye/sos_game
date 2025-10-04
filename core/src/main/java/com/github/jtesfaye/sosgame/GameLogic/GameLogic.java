package com.github.jtesfaye.sosgame.GameLogic;

import com.github.jtesfaye.sosgame.util.Pair;

import java.util.ArrayList;

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

    protected boolean checkSOS(int r, int c) {

        if (this.isDownDiagonal(r, c))
            return true;

        if (this.isHorizontal(r, c))
            return true;

        return this.isVertical(r, c);
    }


    protected boolean isDownDiagonal(int row, int col) {

        int r = row;
        int c = col;

        ArrayList<String> pieces = new ArrayList<>();

        while (r < boardRow && c < boardCol) {
            pieces.add(board[r][c].toString());
            r++;
            c++;
        }

        String res = String.join("", pieces);

        if (res.equals("SOS")) {
            return true;
        }

        pieces.clear();

        while (r > 0 && c > 0) {

            pieces.add(board[r][c].toString());
            r--;
            c--;
        }


        res = String.join("", pieces);

        if (res.equals("SOS")) {
            return true;
        }

        pieces.clear();


        if (r > 0 && col > 0) {
            pieces.add(board[row-1][col-1].toString());
        }

        pieces.add(board[row][col].toString());

        if (r < boardRow && col < boardRow) {
            pieces.add(board[r+1][c+1].toString());
        }

        res = String.join("", pieces);

        return res.equals("SOS");
    }

    protected boolean isUpDiagonal(int row, int col) {

        int r = row;
        int c = col;

        ArrayList<String> pieces = new ArrayList<>();

        while (r > 0 && c < boardCol) {
            pieces.add(board[r][c].toString());
            r--;
            c++;
        }

        String res = String.join("", pieces);

        if (res.equals("SOS")) {
            return true;
        }

        pieces.clear();

        while (r < boardRow && c > 0) {

            pieces.add(board[r][c].toString());
            r++;
            c--;
        }


        res = String.join("", pieces);

        if (res.equals("SOS")) {
            return true;
        }

        pieces.clear();


        if (r-1 > 0 && col + 1 < boardCol) {
            pieces.add(board[row-1][col+1].toString());
        }

        pieces.add(board[row][col].toString());

        if (r + 1 < boardRow && col - 1 > 0) {
            pieces.add(board[r+1][c-1].toString());
        }

        res = String.join("", pieces);

        return res.equals("SOS");

    }

    protected boolean isVertical(int r, int c) {
        return false;
    }

    protected  boolean isHorizontal(int r, int c) {
        return false;
    }

}
