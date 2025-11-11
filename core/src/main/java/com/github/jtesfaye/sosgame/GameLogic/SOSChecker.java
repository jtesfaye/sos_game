package com.github.jtesfaye.sosgame.GameLogic;

import com.github.jtesfaye.sosgame.GameEvent.SOSMadeEvent;
import com.github.jtesfaye.sosgame.GameObject.Piece;
import com.github.jtesfaye.sosgame.GameObject.Player;

public class SOSChecker {

    private final Piece[][] board;
    private final int boardRow;
    private final int boardCol;

    public SOSChecker(Piece[][] b) {

        board = b;
        boardRow = b.length;
        boardCol = b[0].length;

    }

    public SOSMadeEvent checkDiagonal(int row, int col, Player player) {

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

                        return new SOSMadeEvent(row1, col1, row, col, row3, col3, player.getPlayerColor());
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
                        return new SOSMadeEvent(row, col, row2, col2, row3, col3, player.getPlayerColor());
                    }
                }
            }
        }

        return null;
    }

    public SOSMadeEvent checkVertical(int row, int col, Player player) {

        if (board[row][col].equals(Piece.oPiece)) {

            if (checkBounds(row, col + 1) && checkBounds(row, col - 1)) {
                if (board[row][col + 1].equals(Piece.sPiece)
                    && board[row][col - 1].equals(Piece.sPiece)) {
                    return new SOSMadeEvent(row, col - 1, row, col, row, col + 1, player.getPlayerColor());
                }
            }
        }

        if (board[row][col].equals(Piece.sPiece)) {

            int[][] dir = {{0,1}, {0, -1}};

            for (int[] d : dir) {

                if (checkBounds(row, col + d[1]) && checkBounds(row, col + 2*d[1])) {

                    if (board[row][col + d[1]].equals(Piece.oPiece)
                        && board[row][col + 2 * d[1]].equals(Piece.sPiece)) {
                        return new SOSMadeEvent(row, col, row, col + d[1], row, col + 2*d[1], player.getPlayerColor());
                    }
                }
            }
        }

        return null;
    }

    public SOSMadeEvent checkHorizontal(int row, int col, Player player) {

        if (board[row][col].equals(Piece.oPiece)) {

            if (checkBounds(row  + 1, col) && checkBounds(row - 1, col)) {
                if (board[row + 1][col].equals(Piece.sPiece)
                    && board[row - 1][col].equals(Piece.sPiece)) {
                    return new SOSMadeEvent(row + 1, col, row, col, row - 1, col, player.getPlayerColor());
                }
            }
        }

        if (board[row][col].equals(Piece.sPiece)) {

            int[][] dir = {{1, 0}, {-1, 0}};

            for (int[] d : dir) {

                if (checkBounds(row + d[0], col) && checkBounds(row + 2*d[0], col)) {

                    if (board[row + d[0]][col].equals(Piece.oPiece)
                        && board[row + 2 * d[0]][col].equals(Piece.sPiece)) {
                        return new SOSMadeEvent(row, col, row + d[0], col, row + 2 * d[0], col, player.getPlayerColor());
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
