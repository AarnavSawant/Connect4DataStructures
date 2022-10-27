/**
 * Handles the game logic for Connect 4, by holding references to the Board
 * and players.
 */
public class Connect4Game {
    private Board board;
    private Player[] players;
    private int turns;
    private GameStatus status;
    private Player winner;

    /**
     * Creates a new Connect4Game with the given players.
     * 
     * @param player1 the first player
     * @param player2 the second player
     */
    public Connect4Game(Player player1, Player player2) {
        board = new Board();
        players = new Player[] { player1, player2 };
        turns = 0;
        status = GameStatus.ONGOING;
    }

    /**
     * Get the underlying Connect4 board.
     * 
     * @return Board
     */
    public Board getBoard() {
        return board;
    }

    /**
     * Get the player whose turn it is, by checking the turns played in the game.
     * 
     * @return Player
     */
    public Player whoseTurn() {
        return players[turns % 2];
    }

    /**
     * Add a Player's token to the board, by calling the Player.chooseColumn() method.
     * 
     * @param player Player to add a token for
     * 
     * @return GameStatus the game's current status
     * 
     * @throws FullColumnError
     */
    public GameStatus playTurn(Player player) throws FullColumnError {
        board.addPiece(player.chooseColumn(), player.getColor());
        turns++;
        status = board.checkWinner();
        return status;
    }

    /**
     * Get the current game status.
     * 
     * @return GameStatus
     */
    public GameStatus getStatus() {
        return status;
    }

    /**
     * Get the winning player. Returns null if the game has not ended yet.
     * 
     * @return Player the winner
     */
    public Player getWinner() {
        if (status == GameStatus.ONGOING) {
            return null;
        }
        if (winner == null) {
            TokenColor color = board.winningColor();
            for (Player player : players) {
                if (player.getColor() == color) {
                    winner = player;
                    break;
                }
            }
        }
        return winner;
    }

    /**
     * See Board.getWinningIndices().
     */
    public int[][] getWinningIndices() {
        return board.getWinningIndices();
    }

    /**
     * Checks if a given coordinate to a token is a winning token.
     * 
     * @param row row of the token
     * @param col column of the token
     * @return true if the token is a winning token, false otherwise
     */
    public boolean isWinningIndex(int row, int col) {
        int[][] winningIndices = getWinningIndices();
        for (int[] indices : winningIndices) {
            if (indices[0] == row && indices[1] == col) {
                return true;
            }
        }
        return false;
    }
}
