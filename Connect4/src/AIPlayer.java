public class AIPlayer extends Player {
    private Board mBoard;

    public AIPlayer(TokenColor color) {
        super(color);
    }

    /**
     * Read the Current State of the Game
     * @param currentBoard
     */
    public void readCurrentBoard(Board currentBoard) {
        mBoard = currentBoard;
    }

    /**
     * Use Simple Logic + MiniMax to determine best move
     * @return the column to play
     * @throws FullColumnError
     */
    @Override
    public int chooseColumn() throws FullColumnError {
        //Find Opposing Color
        TokenColor opposingColor = TokenColor.RED;
        if (this.getColor() == TokenColor.RED) {
            opposingColor = TokenColor.YELLOW;
        }

        //Sanity Check to see if we can win/block on next move
        for (int col : mBoard.getPossibleMoves()) {
            Board newBoard = new Board(mBoard);
            newBoard.addPiece(col, this.getColor());
            if ((newBoard.checkWinner() == GameStatus.WIN) && (newBoard.winningColor() == this.getColor())) {
                return col;
            }
            Board opponentBoard = new Board(mBoard);
            opponentBoard.addPiece(col, opposingColor);
            if ((opponentBoard.checkWinner() == GameStatus.WIN) && (opponentBoard.winningColor() == opposingColor)) {
                return col;
            }
        }

        //Use Mini-Max to calculate next move
        MiniMaxUtils.Node node = MiniMaxUtils.miniMax(mBoard, getColor(), 5, true);
        return node.getColumn();
    }
}
