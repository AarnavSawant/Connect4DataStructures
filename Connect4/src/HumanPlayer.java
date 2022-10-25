import acm.program.GraphicsProgram;

public class HumanPlayer extends Player {
    private ConnectFourTester_5SinghSawant graphicsProgram;

    public HumanPlayer(TokenColor color, String name, GraphicsProgram graphicsProgram) {
        super(color, name);
        this.graphicsProgram = (ConnectFourTester_5SinghSawant) graphicsProgram;
    }


    public int chooseColumn() {
        int column = -1;
        double mouseClickX = -1;
        do {
            mouseClickX = graphicsProgram.getMouseClickX();
            graphicsProgram.pause(1);
            column = graphicsProgram.getColumnIndexFromMouseEvent(graphicsProgram.getMouseClickX());
        } while (column == -1 || mouseClickX == -1);
        graphicsProgram.resetMouseClickX();
        return column;
    }
}
