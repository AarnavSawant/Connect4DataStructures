public class Connect4Game {
    private Board board;
    private Player[] players;
    private int turns;
    private GameStatus status;
    private Player winner;

    public Connect4Game(Player player1, Player player2) {
        board = new Board();
        players = new Player[] { player1, player2 };
        turns = 0;
        status = GameStatus.ONGOING;
    }

    public Board getBoard() {
        return board;
    }

    public Player whoseTurn() {
        return players[turns % 2];
    }

    public GameStatus playTurn(Player player) throws FullColumnError {
        board.addPiece(player.chooseColumn(), player.getColor());
        turns++;
        status = board.checkWinner();
        return status;
    }

    public GameStatus getStatus() {
        return status;
    }

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

    public int[][] getWinningIndices() {
        return board.getWinningIndices();
    }

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
