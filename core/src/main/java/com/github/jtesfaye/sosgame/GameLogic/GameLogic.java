package com.github.jtesfaye.sosgame.GameLogic;

import com.github.jtesfaye.sosgame.util.Pair;
import lombok.Getter;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Queue;
import java.util.stream.IntStream;

public abstract class GameLogic {

    @Getter
    protected Player currentTurn;

    public int boardRow;
    public int boardCol;
    protected int capacity;

    protected final Piece[][] board;
    protected final SOSChecker checker;
    int[] scoreArr; //Keeps track of SOS count for each player

    private final Queue<Pair<Pair<Integer, Integer>, Piece>> changeQueue;

    protected abstract void checkSOS(int r, int c);

    /**
     * This function checks if there is a winner. It does not determine who the winner is.
     */
    public abstract boolean isWinner();

    protected GameLogic(int r, int c) {

        boardRow = r;
        boardCol = c;
        capacity = r * c;
        currentTurn = Player.Player1;

        board = new Piece[r][c];

        for (int i  = 0; i < r; i++) {

            for (int j = 0; j < c; j++) {
                board[i][j] = Piece.OPEN;
            }
        }

        changeQueue = new ArrayDeque<>();

        scoreArr = new int[Player.values().length];

        checker = new SOSChecker(board);
    }

    /**
     * This function determines which player won the game. It is only called if isWinner returns true
     */
    public Player getWinner() {

        int max = IntStream
            .range(0, scoreArr.length)
            .reduce((i, j) -> scoreArr[i] > scoreArr[j] ? i : j)
            .orElse(-1);

        return Player.values()[max];
    }

    public boolean setPiece(int r, int c, Piece piece) {

        if (appendBoard(r,c,piece)) {
            changeQueue.add(new Pair<>(new Pair<>(r,c), piece));
            checkSOS(r,c);
            nextTurn();
        }

        return isWinner();
    }

    protected void nextTurn() {

        int numPlayers = Player.values().length;
        int val = currentTurn.ordinal();

        val += 1;
        val = val % numPlayers;

        currentTurn = Player.values()[val];
    }

    public boolean isOpen(int r, int c) {

        if (r >= boardRow || c >= boardCol) {
            return false;
        }

        return board[r][c] == Piece.OPEN;
    }

    public Piece getPiece(int r, int c) {

        if (r >= boardRow || c >= boardCol) {
            return Piece.OPEN;
        }

        return board[r][c];
    }

    public Pair<Pair<Integer, Integer>, Piece> getChanges() {
        return changeQueue.poll();
    }

    protected boolean appendBoard(int r, int c, Piece piece) {

        if (!isOpen(r, c))
            return false;

        board[r][c] = piece;

        capacity += 1;
        return true;
    }

}
