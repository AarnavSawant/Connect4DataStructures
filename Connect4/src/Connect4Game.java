public class Connect4Game {
    private Board board;
    private Player[] players;
    private int turns;

    public Connect4Game(Player player1, Player player2) {
        board = new Board();
        players = new Player[] { player1, player2 };
        turns = 0;
    }

    public Board getBoard() {
        return board;
    }

    public Player whoseTurn() {
        return players[turns++ % 2];
    }

    public GameStatus playTurn(Player player) throws FullColumnError {
        board.addPiece(player.chooseColumn(), player);
        System.out.println("TURNS, " + turns);
        return GameStatus.ONGOING;
    }

    public int[][] getWinningIndices() {
        return null;
    }
}
