import java.util.ArrayList;
import java.util.Arrays;

public class Board {
    public static final int WIDTH = 7;
    public static final int HEIGHT = 6;

    private TokenColor[][] board;
    private int[][] winningIndices;
    private TokenColor winner;

    public Board() {
        board = new TokenColor[HEIGHT][WIDTH];
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                board[i][j] = TokenColor.NONE;
            }
        }
        winningIndices = new int[0][0];
    }

    public Board(Board b) {
        this.board = b.getBoard();
        this.winningIndices = b.getWinningIndices();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (TokenColor[] row : board) {
            for (TokenColor col : row) {
                sb.append(col.ordinal()).append(" ");
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
     * 
     * @throws FullColumnError if the column is already full
     */
    public void addPiece(int col, TokenColor color) throws FullColumnError {
        if (isColumnFull(col)) {
            throw new FullColumnError(col);
        }
        for (int i = HEIGHT - 1; i > -1; i--) {
            if (board[i][col] == TokenColor.NONE) {
                board[i][col] = color;
                return;
            }
        }
    }

    public boolean isColumnFull(int col) {
        return board[0][col] != TokenColor.NONE;
    }

    public ArrayList<Integer> getPossibleMoves() {
        ArrayList<Integer> viableColumns = new ArrayList<>();
        for (int column = 0; column < getNumCols(); column++) {
            if (!isColumnFull(column)) {
                viableColumns.add(column);
            }
        }
        return viableColumns;
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

    private boolean checkHorizontalWin() {
        for (int x = 0; x < WIDTH - 3; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                TokenColor[] row = board[y];
                TokenColor color = row[x];
                if (color != TokenColor.NONE
                        && color == row[x + 1]
                        && color == row[x + 2]
                        && color == row[x + 3]) {
                    winningIndices = new int[][] {{y, x}, {y, x + 1}, {y, x + 2}, {y, x + 3}};
                    winner = color;
                    return true;
                }
            }
        }
        return false;
    }

    private boolean checkVerticalWin() {
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT - 3; y++) {
                TokenColor color = board[y][x];
                if (color != TokenColor.NONE
                        && color == board[y + 1][x]
                        && color == board[y + 2][x]
                        && color == board[y + 3][x]) {
                    winningIndices = new int[][] {{y, x}, {y + 1, x}, {y + 2, x}, {y + 3, x}};
                    winner = color;
                    return true;
                }
            }
        }
        return false;
    }

    private boolean checkDiagonalWin() {
        for (int x = 0; x < WIDTH - 3; x++) {
            for (int y = 0; y < HEIGHT - 3; y++) {
                TokenColor color = board[y][x];
                if (color != TokenColor.NONE
                        && color == board[y + 1][x + 1]
                        && color == board[y + 2][x + 2]
                        && color == board[y + 3][x + 3]) {
                    winningIndices = new int[][] {{y, x}, {y + 1, x + 1}, {y + 2, x + 2}, {y + 3, x + 3}};
                    winner = color;
                    return true;
                }
            }
        }
        for (int x = WIDTH - 1; x > 2; x--) {
            for (int y = 0; y < HEIGHT - 3; y++) {
                TokenColor color = board[y][x];
                if (color != TokenColor.NONE
                        && color == board[y + 1][x - 1]
                        && color == board[y + 2][x - 2]
                        && color == board[y + 3][x - 3]) {
                    winningIndices = new int[][] {{y, x}, {y + 1, x - 1}, {y + 2, x - 2}, {y + 3, x - 3}};
                    winner = color;
                    return true;
                }
            }
        }
        return false;
    }

    public GameStatus checkWinner() {
        if (checkHorizontalWin() || checkVerticalWin() || checkDiagonalWin()) {
            return GameStatus.WIN;
        }
        if (isFull()) {
            return GameStatus.DRAW;
        }
        return GameStatus.ONGOING;
    }

    public TokenColor winningColor() {
        return winner;
    }

    public int[][] getWinningIndices() {
        return winningIndices;
    }

    public int getNumRows() {
        return board.length;
    }

    public int getNumCols() {
        return board[0].length;
    }
}
