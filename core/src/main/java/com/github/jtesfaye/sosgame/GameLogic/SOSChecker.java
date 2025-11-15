package com.github.jtesfaye.sosgame.GameLogic;

import com.github.jtesfaye.sosgame.GameEvent.SOSMadeEvent;
import com.github.jtesfaye.sosgame.GameObject.Piece;
import com.github.jtesfaye.sosgame.GameObject.Player;
import com.github.jtesfaye.sosgame.util.Pair;

import java.util.ArrayList;
import java.util.Arrays;

public class SOSChecker {

    private final Piece[][] board;
    private final int boardRow;
    private final int boardCol;

    public SOSChecker(Piece[][] b) {

        board = b;
        boardRow = b.length;
        boardCol = b[0].length;

    }

    public ArrayList<Pair<Integer, Integer>> checkDiagonal(int row, int col) {

        if (board[row][col].equals(Piece.oPiece)) {

            int[][] dir = {{1,1}, {-1, 1}};

            for (int[] d : dir) {

                int row1 = row + d[0];
                int col1 = col + d[1];
                int row3 = row - d[0];
                int col3 = col - d[1];

                if (checkBounds(row1, col1) && checkBounds(row3, col3)) {

                    if(board[row1][col1].equals(Piece.sPiece)
                        && board[row3][col3].equals(Piece.sPiece)) {

                        return new ArrayList<>(Arrays.asList(
                            new Pair<>(row1, col1),
                            new Pair<>(row, col),
                            new Pair<>(row3, col3)
                            ));
                    }
                }
            }
        }

        if (board[row][col].equals(Piece.sPiece)) {

            int[][] dir = {{1,1}, {-1, 1}, {1, -1}, {-1, -1}};

            for (int[] d : dir) {

                int row2 = row + d[0];
                int col2 = col + d[1];
                int row3 = row + 2 * d[0];
                int col3 = col + 2 * d[1];

                if (checkBounds(row3, col3)) {

                    if (board[row2][col2].equals(Piece.oPiece)
                        && board[row3][col3].equals(Piece.sPiece)) {

                        return new ArrayList<>(Arrays.asList(
                            new Pair<>(row, col),
                            new Pair<>(row2, col2),
                            new Pair<>(row3, col3)
                        ));
                    }
                }
            }
        }

        return null;
    }

    public ArrayList<Pair<Integer, Integer>> checkVertical(int row, int col) {

        if (board[row][col].equals(Piece.oPiece)) {

            if (checkBounds(row, col + 1) && checkBounds(row, col - 1)) {
                if (board[row][col + 1].equals(Piece.sPiece)
                    && board[row][col - 1].equals(Piece.sPiece)) {

                    return new ArrayList<>(Arrays.asList(
                        new Pair<>(row, col - 1),
                        new Pair<>(row, col),
                        new Pair<>(row, col + 1)
                    ));
                }
            }
        }

        if (board[row][col].equals(Piece.sPiece)) {

            int[][] dir = {{0,1}, {0, -1}};

            for (int[] d : dir) {

                if (checkBounds(row, col + d[1]) && checkBounds(row, col + 2*d[1])) {

                    if (board[row][col + d[1]].equals(Piece.oPiece)
                        && board[row][col + 2 * d[1]].equals(Piece.sPiece)) {

                        return new ArrayList<>(Arrays.asList(
                            new Pair<>(row, col),
                            new Pair<>(row, col + d[1]),
                            new Pair<>(row, col + 2*d[1])
                        ));

                    }
                }
            }
        }

        return null;
    }

    public ArrayList<Pair<Integer, Integer>> checkHorizontal(int row, int col) {

        if (board[row][col].equals(Piece.oPiece)) {

            if (checkBounds(row  + 1, col) && checkBounds(row - 1, col)) {
                if (board[row + 1][col].equals(Piece.sPiece)
                    && board[row - 1][col].equals(Piece.sPiece)) {

                    return new ArrayList<>(Arrays.asList(
                        new Pair<>(row + 1, col),
                        new Pair<>(row, col),
                        new Pair<>(row - 1, col)
                    ));
                }
            }
        }

        if (board[row][col].equals(Piece.sPiece)) {

            int[][] dir = {{1, 0}, {-1, 0}};

            for (int[] d : dir) {

                if (checkBounds(row + d[0], col) && checkBounds(row + 2*d[0], col)) {

                    if (board[row + d[0]][col].equals(Piece.oPiece)
                        && board[row + 2 * d[0]][col].equals(Piece.sPiece)) {

                        return new ArrayList<>(Arrays.asList(
                            new Pair<>(row, col),
                            new Pair<>(row + d[0], col),
                            new Pair<>(row + 2 * d[0], col)
                        ));

                    }
                }
            }
        }

        return null;
    }

    private boolean checkBounds(int r, int c) {

        return (r >= 0 && r < boardRow) && (c >= 0 && c < boardCol);

    }
}
