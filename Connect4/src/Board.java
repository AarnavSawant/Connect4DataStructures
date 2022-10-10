import acm.graphics.GRect;

import java.util.Arrays;

public class Board {
    public static final int WIDTH = 7;
    public static final int HEIGHT = 6;

    private TokenColor[][] board;

    public Board() {
        board = new TokenColor[HEIGHT][WIDTH];
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                board[i][j] = TokenColor.NONE;
            }
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (TokenColor[] row : board) {
            for (TokenColor col : row) {
                sb.append(col).append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    /**
     * Retrieve a deep copy of the underlying 2D array representing the board.
     * 
     * @return TokenColor[][] representing the board
     */
    public TokenColor[][] getBoard() {
        TokenColor[][] copy = new TokenColor[HEIGHT][WIDTH];
        for (int i = 0; i < HEIGHT; i++) {
            copy[i] = Arrays.copyOf(board[i], WIDTH);
        }
        return copy;
    }

    /**
     * Drop a player's chip into a certain column, at the lowest possible slot.
     *
     * @param col index of column to drop piece into
     * @param player Player object representing the player dropping the piece
     */
    public void addPiece(int col, Player player) {
        for (int i = HEIGHT - 1; i > -1; i--) {
            if (board[i][col] == TokenColor.NONE) {
                board[i][col] = player.getColor();
                return;
            }
        }
    }

    public boolean isColumnFull(int col) {
        return board[0][col] != TokenColor.NONE;
    }

    public boolean isFull() {
        for (TokenColor[] row : board) {
            for (TokenColor col : row) {
                if (col == TokenColor.NONE) {
                    return false;
                }
            }
        }
        return true;
    }

    public int[][] checkWinner() {
        return new int[0][0];
    }

    public int getNumRows() {
        return board.length;
    }

    public int getNumCols() {
        return board[0].length;
    }
}
