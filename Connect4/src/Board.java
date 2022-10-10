import java.util.Arrays;

public class Board {
    public static final int WIDTH = 7;
    public static final int HEIGHT = 6;

    private int[][] board;

    public Board() {
        board = new int[HEIGHT][WIDTH];
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int[] row : board) {
            for (int col : row) {
                sb.append(col).append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    /**
     * Retrieve a deep copy of the underlying 2D array representing the board.
     * 
     * @return int[][] representing the board
     */
    public int[][] getBoard() {
        int[][] copy = new int[HEIGHT][WIDTH];
        for (int i = 0; i < HEIGHT; i++) {
            copy[i] = Arrays.copyOf(board[i], WIDTH);
        }
        return copy;
    }

    /**
     * Drop a player's chip into a certain column, at the lowest possible slot.
     * 
     * @param col index of column to drop piece into
     * @param player player id
     */
    public void addPiece(int col, int player) throws FullColumnError {
        if (isColumnFull(col)) {
            throw new FullColumnError(col);
        }
        for (int i = HEIGHT - 1; i > -1; i--) {
            if (board[i][col] == 0) {
                board[i][col] = player;
                return;
            }
        }
    }

    public boolean isColumnFull(int col) {
        return board[0][col] != 0;
    }

    public boolean isFull() {
        for (int[] row : board) {
            for (int col : row) {
                if (col == 0) {
                    return false;
                }
            }
        }
        return true;
    }
}
