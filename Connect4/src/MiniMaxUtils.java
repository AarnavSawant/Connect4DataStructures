import java.util.ArrayList;
import java.util.Arrays;

/**
 * Class representing the MiniMax algorithm used by the AIPlayer.
 */
public class MiniMaxUtils {
    private static final int WINDOW_SIZE = 4;
    private static final int THREE_WINDOW_SIZE = 3;
    private static final int FIVE_WINDOW_SIZE = 5;
    private static final int WINNING_REWARD = 1000000000;
    private static final int THREE_PATTERN_REWARD = 8;
    private static final int TWO_PATTERN_REWARD = 4;
    private static final int CENTER_REWARD = 2;

    private static final int THREE_TRAP_PENALTY = 80;
    private static final int THREE_TRAP_REWARD = 100;
    private static final int THREE_OUT_OF_FOUR_PENALTY = 10;

    /**
     * Method to score a state of the Connect 4 Board
     * 
     * @param board board
     * @param color color to use to score
     * @return a score value evaluating the current state
     */
    public static double scoreBoard(Board board, TokenColor color) {
        int score = 0;

        // Emphasize Gaining Control of the Center
        score += scoreCenterPosition(board, color);

        // For Optimizing Horizontal Score
        for (int row = 0; row < board.getNumRows(); row++) {
            ArrayList<TokenColor> rowArr = new ArrayList<>(Arrays.asList(board.getBoard()[row]));
            score += getScore(color, rowArr);
        }

        // For Optimizing Vertical Score
        for (int col = 0; col < board.getNumCols(); col++) {
            ArrayList<TokenColor> colArr = new ArrayList<TokenColor>();
            for (int i = 0; i < board.getNumRows(); i++) {
                colArr.add(board.getBoard()[i][col]);
            }
            score += getScore(color, colArr);
        }

        // For Optimizing Positive Diagonal Slope
        for (int col = 0; col < board.getNumCols() - 3; col++) {
            for (int row = 0; row < board.getNumRows() - 3; row++) {
                ArrayList<TokenColor> window = new ArrayList<>();
                for (int i = 0; i < WINDOW_SIZE; i++) {
                    window.add(board.getBoard()[row + i][col + i]);
                }
                score += getScore(color, window);
            }
        }

        // For Optimizing Negative Diagonal Slope
        for (int col = board.getNumCols() - 1; col > 2; col--) {
            for (int row = 0; row < board.getNumRows() - 3; row++) {
                ArrayList<TokenColor> window = new ArrayList<>();
                for (int i = 0; i < WINDOW_SIZE; i++) {
                    window.add(board.getBoard()[row + i][col - i]);
                }
                score += getScore(color, window);
            }
        }

        return score;
    }

    /**
     * Score Rewarding Moves in Center Column
     * 
     * @param board current Board
     * @param color current color
     * @return reward for each token in center column
     */
    private static double scoreCenterPosition(Board board, TokenColor color) {
        ArrayList<TokenColor> centerTokens = new ArrayList<>();
        for (int i = 0; i < board.getNumRows(); i++) {
            centerTokens.add(board.getBoard()[i][board.getNumCols() / 2]);
        }
        return CENTER_REWARD * numTokensInWindow(centerTokens, centerTokens.size(), 0, color);

    }

    /**
     * Reward AI Agent Based on Window Goals
     * 
     * @param color  color to use
     * @param window a segment of the board
     * @return score
     */
    private static int getScore(TokenColor color, ArrayList<TokenColor> window) {
        int score = 0;

        // Find Opponent Color
        TokenColor oppositeColor;
        if (color == TokenColor.RED) {
            oppositeColor = TokenColor.YELLOW;
        } else {
            oppositeColor = TokenColor.RED;
        }

        for (int val = 0; val < window.size() - WINDOW_SIZE + 1; val++) {

            if (numTokensInWindow(window, WINDOW_SIZE, val, color) == 4) {
                // If we can win in the next move, add WINNING_REWARD
                score += WINNING_REWARD;
            } else if (numTokensInWindow(window, WINDOW_SIZE, val, color) == 3
                    && numTokensInWindow(window, WINDOW_SIZE, val, TokenColor.NONE) == 1) {
                // If we can get 3 out of 4 and have a chance at winning, add
                // THREE_PATTERN_REWARD
                score += THREE_PATTERN_REWARD;
            } else if (numTokensInWindow(window, WINDOW_SIZE, val, color) == 2
                    && numTokensInWindow(window, WINDOW_SIZE, val, TokenColor.NONE) == 2) {
                // If can get consecutive tokens add a smaller reard
                score += TWO_PATTERN_REWARD;
            }
            if (numTokensInWindow(window, WINDOW_SIZE, val, oppositeColor) == 3
                    && numTokensInWindow(window, WINDOW_SIZE, val, TokenColor.NONE) == 1) {
                // If there the opponent can win on the next move, take a penalty
                score -= THREE_OUT_OF_FOUR_PENALTY;
            }
        }

        // Checking for three-token trap (when there is three coins and two chances to
        // win)
        for (int val = 0; val < window.size() - FIVE_WINDOW_SIZE + 1; val++) {
            // If we can create a trap, add reward
            if (checkThreeTrap(window, color)) {
                score += THREE_TRAP_REWARD;
            }
            // If we're gonna be trapped, take a penatly
            if (checkThreeTrap(window, oppositeColor)) {
                score -= THREE_TRAP_PENALTY;
            }
        }
        // Return the cumulative score of this state
        return score;
    }

    /**
     * Checks if a window contains a three token trap
     * 
     * @param window window to look at
     * @param color  color to look at
     * @return whether this window of 5 tokens is a three-token trap
     */
    public static boolean checkThreeTrap(ArrayList<TokenColor> window, TokenColor color) {
        if (window.get(0) == TokenColor.NONE && window.get(window.size() - 1) == TokenColor.NONE) {
            int numTokens = numTokensInWindow(window, 1, THREE_WINDOW_SIZE, color);
            return numTokens == THREE_WINDOW_SIZE;
        }
        return false;
    }

    /**
     * Calculate the number of coins of a certain token color in a window
     * 
     * @param window      the window to look at
     * @param windowSize  the size of the window
     * @param windowStart the starting point of the window
     * @param color       the token color
     * @return the count
     */
    public static int numTokensInWindow(ArrayList<TokenColor> window, int windowSize, int windowStart,
            TokenColor color) {
        int count = 0;
        for (int c = windowStart; c < windowStart + windowSize; c++) {
            if (window.get(c) == color) {
                count++;
            }
        }
        return count;
    }

    /**
     * Returns the best move to take
     * 
     * @param board        a state
     * @param color        color to look at
     * @param depth        depth of the DFS
     * @param isMaximizing whether we're maximizing/minimizing objective
     * @return Node (best score and column value)
     * @throws FullColumnError
     */
    public static Node miniMax(Board board, TokenColor color, int depth, boolean isMaximizing) throws FullColumnError {
        // Recursive Base Case: if we've reached the maximum depth of the Depth First
        // Search, evaluate the score
        if (depth == 0 || board.checkWinner() != GameStatus.ONGOING) {
            if (board.checkWinner() == GameStatus.WIN) {
                // Reward for winning
                if (board.winningColor() == color) {
                    return new Node(-1, WINNING_REWARD);
                } else if (board.winningColor() != color && board.winningColor() != TokenColor.NONE) {
                    // Penalty fot Losing
                    return new Node(-1, -WINNING_REWARD);
                }
            } else {
                // Score Board using Score Function
                return new Node(-1, scoreBoard(board, color));
            }
        }
        // If we're maximing, we find the maximum child node score
        if (isMaximizing) {
            double bestScore = Double.MIN_VALUE;
            int bestCol = board.getPossibleMoves().get((int) (Math.random() * board.getPossibleMoves().size()));
            for (int col : board.getPossibleMoves()) {
                Board newBoard = new Board(board);
                newBoard.addPiece(col, color);
                Node n = miniMax(newBoard, color, depth - 1, false);
                if (n.getScore() > bestScore) {
                    bestScore = n.getScore();
                    bestCol = col;
                }
            }
            return new Node(bestCol, bestScore);
        } else {

            // If we're minimizing, we find the minimum child node score
            double bestScore = Double.MAX_VALUE;
            int bestCol = board.getPossibleMoves().get((int) (Math.random() * board.getPossibleMoves().size()));
            for (int col : board.getPossibleMoves()) {
                Board newBoard = new Board(board);
                if (color == TokenColor.RED) {
                    newBoard.addPiece(col, TokenColor.YELLOW);
                } else {
                    newBoard.addPiece(col, TokenColor.RED);
                }
                Node n = miniMax(newBoard, color, depth - 1, true);
                if (n.getScore() < bestScore) {
                    bestScore = n.getScore();
                    bestCol = col;
                }
            }

            // We want to return the column that's going to maximize our score while
            // minimizing opponents' score
            // We simulate opponents' moves by playing the move that minimizes our score
            return new Node(bestCol, bestScore);
        }

    }

    /**
     * Class representing a Node for this Depth-First Search
     */
    public static class Node {
        private int column; // Column Value for Board
        private double score; // Score by Placing this token

        Node(int column, double score) {
            this.column = column;
            this.score = score;
        }

        /**
         * returns the Score
         * 
         * @return score
         */
        public double getScore() {
            return score;
        }

        /**
         * Returns the Column
         * 
         * @return column
         */
        public int getColumn() {
            return column;
        }
    }

}
