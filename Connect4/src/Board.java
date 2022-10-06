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

    public int[][] getBoard() {
        int[][] copy = new int[HEIGHT][WIDTH];
        for (int i = 0; i < HEIGHT; i++) {
            copy[i] = Arrays.copyOf(board[i], WIDTH);
        }
        return copy;
    }

    public void addPiece(int col, int player) {
        for (int i = HEIGHT - 1; i > -1; i++) {
            if (board[i][col] == 0) {
                board[i][col] = player;
                return;
            }
        }
    }
}
