package com.github.jtesfaye.sosgame.GameLogic;

public class SOSChecker {

    private final Piece[][] board;
    private final int boardRow;
    private final int boardCol;

    public SOSChecker(Piece[][] b) {

        board = b;
        boardRow = b.length;
        boardCol = b[0].length;

    }

    public boolean isDiagonal(int row, int col) {

        if (board[row][col].equals(Piece.oPiece)) {

            int[][] dir = {{1,1}, {-1, 1}};

            for (int[] d : dir) {

                if (checkBounds(row + d[0], col + d[1]) && checkBounds(row - d[0], col - d[1])) {

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

    public boolean isVertical(int row, int col) {

        if (board[row][col].equals(Piece.oPiece)) {

            if (checkBounds(row, col + 1) && checkBounds(row, col - 1)) {
                if (board[row][col + 1].equals(Piece.sPiece)
                    && board[row][col - 1].equals(Piece.sPiece)) {
                    return true;
                }
            }
        }

        if (board[row][col].equals(Piece.sPiece)) {

            int[][] dir = {{0,1}, {0, -1}};

            for (int[] d : dir) {

                if (checkBounds(row, col + d[1]) && checkBounds(row, col + 2*d[1])) {

                    if (board[row][col + d[1]].equals(Piece.oPiece)
                        && board[row][col + 2 * d[1]].equals(Piece.sPiece)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    public boolean isHorizontal(int row, int col) {

        if (board[row][col].equals(Piece.oPiece)) {

            if (checkBounds(row  + 1, col) && checkBounds(row - 1, col)) {
                if (board[row + 1][col].equals(Piece.sPiece)
                    && board[row - 1][col].equals(Piece.sPiece)) {
                    return true;
                }
            }
        }

        if (board[row][col].equals(Piece.sPiece)) {

            int[][] dir = {{1, 0}, {-1, 0}};

            for (int[] d : dir) {

                if (checkBounds(row + d[0], col) && checkBounds(row + 2*d[0], col)) {

                    if (board[row + d[0]][col].equals(Piece.oPiece)
                        && board[row + 2 * d[0]][col].equals(Piece.sPiece)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    private boolean checkBounds(int r, int c) {

        return (r >= 0 && r < boardRow) && (c >= 0 && c < boardCol);

    }
}
