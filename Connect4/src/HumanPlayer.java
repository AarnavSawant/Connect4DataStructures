/**
 * Player subclass that uses a human's mouse input to select a move.
 */
public class HumanPlayer extends Player {
    private Connect4Graphics graphicsProgram;

    public HumanPlayer(TokenColor color, String name, Connect4Graphics graphicsProgram) {
        super(color, name);
        this.graphicsProgram = graphicsProgram;
    }

    /**
     * Waits for the user to click on a column and returns the column number.
     */
    public int chooseColumn() {
        int column = -1;
        double mouseClickX = -1;
        do {
            mouseClickX = graphicsProgram.getMouseClickX();
            graphicsProgram.pause(1);
            column = graphicsProgram.getColumnIndexFromMouseEvent(graphicsProgram.getMouseClickX());
        } while (column == -1 || mouseClickX == -1);
        if (graphicsProgram.getGame().getBoard().isColumnFull(column)) {
            return chooseColumn();
        }
        graphicsProgram.resetMouseClickX();
        return column;
    }
}
