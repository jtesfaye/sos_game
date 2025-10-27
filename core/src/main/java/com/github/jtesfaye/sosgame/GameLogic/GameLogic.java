package com.github.jtesfaye.sosgame.GameLogic;

import com.github.jtesfaye.sosgame.util.Pair;
import lombok.Getter;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Queue;
import java.util.stream.IntStream;

public abstract class GameLogic {

    protected int currentTurn;
    Player[] players;

    @Getter
    int[] scoreArr; //Keeps track of SOS count for each player

    public int boardRow;
    public int boardCol;
    protected int capacity;

    protected final Piece[][] board;
    protected final SOSChecker checker;

    private final Queue<Pair<Pair<Integer, Integer>, Piece>> changeQueue;

    protected abstract void checkSOS(int r, int c);

    /**
     * This function checks if there is a winner. It does not determine who the winner is.
     */
    public abstract boolean isWinner();

    protected GameLogic(int r, int c, String opponentType) {

        boardRow = r;
        boardCol = c;
        capacity = r * c;

        players = new Player[2];

        players[0] = new Player("Player 1");

        switch (opponentType) {
            case "Human":
                players[1] = new Player("Player 2");
                break;
            case "Computer":
                players[1] = new Player("Computer");
                break;
            case "LLM":
                players[1] = new Player("LLM");
                break;
            default:
                throw new RuntimeException("Invalid opponent type");
        }

        currentTurn = 0;

        board = new Piece[r][c];

        for (int i  = 0; i < r; i++) {

            for (int j = 0; j < c; j++) {
                board[i][j] = Piece.OPEN;
            }
        }

        changeQueue = new ArrayDeque<>();

        scoreArr = new int[players.length];

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

        return players[max];
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

        int numPlayers = players.length;
        int val = currentTurn;

        val += 1;
        val = val % numPlayers;

        currentTurn = val;
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

    public Player getCurrentTurn() {
        return players[currentTurn];
    }

    public Pair<Pair<Integer, Integer>, Piece> getChanges() {

        return changeQueue.poll();
    }

    public ArrayList<Pair<String, String>> getScores() {

        ArrayList<Pair<String, String>> arr = new ArrayList<>();

        for (int i = 0; i < scoreArr.length; i++) {

            arr.add(new Pair<>(players[i].toString(), Integer.toString(scoreArr[i])));

        }

        return arr;
    }

    protected boolean appendBoard(int r, int c, Piece piece) {

        if (!isOpen(r, c))
            return false;

        board[r][c] = piece;

        capacity += 1;
        return true;
    }




}
