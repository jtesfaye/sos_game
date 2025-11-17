package com.github.jtesfaye.sosgame.GameLogic;

import com.badlogic.gdx.graphics.Color;
import com.github.jtesfaye.sosgame.GameEvent.*;
import com.github.jtesfaye.sosgame.GameObject.Move;
import com.github.jtesfaye.sosgame.GameObject.Piece;
import com.github.jtesfaye.sosgame.GameObject.Player;
import com.github.jtesfaye.sosgame.util.GameEventProcessor;
import com.github.jtesfaye.sosgame.util.Pair;
import lombok.Getter;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;
import java.util.stream.IntStream;

public abstract class GameLogic {

    protected int currentPlayerIndex;
    private final Player[] players;

    @Getter
    int[] scoreArr; //Keeps track of SOS count for each player

    public int boardRow;
    public int boardCol;
    protected int capacity;

    @Getter
    protected final Piece[][] board;
    protected final SOSChecker checker;

    protected final GameEventProcessor p;

    protected abstract boolean checkSOS(int r, int c);

    /**
     * This function checks if there is a winner. It does not determine who the winner is.
     */
    public abstract boolean isEndGame();

    protected GameLogic(int r, int c, Player[] players, GameEventProcessor p) {

        boardRow = r;
        boardCol = c;
        capacity = r * c;

        this.players = players;

        currentPlayerIndex = 0;

        board = new Piece[r][c];

        for (int i  = 0; i < r; i++) {

            for (int j = 0; j < c; j++) {
                board[i][j] = Piece.OPEN;
            }
        }

        this.p = p;

        scoreArr = new int[players.length];

        checker = new SOSChecker(board);
    }

    /**
     * This function determines which player won the game. It is only called if isWinner returns true
     */
    public int getWinner() {

        if (scoreArr[0] == scoreArr[1]) {
            return -1;
        }

        return IntStream
            .range(0, scoreArr.length)
            .reduce((i, j) -> scoreArr[i] > scoreArr[j] ? i : j)
            .orElse(-1);
    }

    public void applyMove(Move move) {

        if (move == null) {
            return;
        }

        int r = move.getRow();
        int c = move.getCol();
        Piece piece = move.getPiece();

        if (appendBoard(r,c,piece)) {

            p.addEvent(new PieceSetEvent(r, c, piece));

            boolean changeTurn = checkSOS(r,c);

            if (isEndGame()) {

                int playerIndex = getWinner();
                System.out.println(playerIndex);

                if (playerIndex == -1) {

                    p.addEvent(new EndGameEvent(null));
                    return;

                }

                p.addEvent(new EndGameEvent(players[playerIndex]));
                return;
            }

            if (!changeTurn) {
                nextTurn();
            }
        }
    }

    protected void nextTurn() {

        int numPlayers = players.length;
        int val = currentPlayerIndex;

        val += 1;
        val = val % numPlayers;

        currentPlayerIndex = val;

        p.addEvent(new turnChangeEvent(players[currentPlayerIndex]));
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

        return players[currentPlayerIndex];
    }

    public ArrayList<Pair<String, String>> getScores() {

        ArrayList<Pair<String, String>> arr = new ArrayList<>();

        for (int i = 0; i < scoreArr.length; i++) {

            arr.add(new Pair<>(players[i].toString(), Integer.toString(scoreArr[i])));

        }

        return arr;
    }

    public String getOpponentName() {

        return players[1].toString();
    }

    protected boolean appendBoard(int r, int c, Piece piece) {

        if (!isOpen(r, c))
            return false;

        board[r][c] = piece;

        capacity -= 1;
        return true;
    }

    protected void onSOSMade(ArrayList<Pair<Integer, Integer>> tiles) {

        scoreArr[currentPlayerIndex] += 1;

        SOSMadeEvent event = new SOSMadeEvent(tiles, getCurrentTurn().getPlayerColor());
        scoreChangeEvent newScore = new scoreChangeEvent(getScores());

        p.addEvent(event);
        p.addEvent(newScore);
    }
}
