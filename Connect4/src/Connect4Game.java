import java.util.Arrays;

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
        board.addPiece(player.chooseColumn(), player);
        turns++;
        status = board.checkWinner();
        return status;
    }

    public GameStatus getStatus() {
        return status;
    }

    public Player getWinner() throws NoWinnerException{
        if (winner == null) {
            throw new NoWinnerException();
        }
        return winner;
    }

    public int[][] getWinningIndices() {
        return board.getWinningIndices();
    }
}
