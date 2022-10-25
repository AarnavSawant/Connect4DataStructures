import acm.program.GraphicsProgram;

public class HumanPlayer extends Player {
    private Connect4Graphics graphicsProgram;

    public HumanPlayer(TokenColor color, String name, GraphicsProgram graphicsProgram) {
        super(color, name);
        this.graphicsProgram = (Connect4Graphics) graphicsProgram;
    }


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
