import acm.program.GraphicsProgram;

public class HumanPlayer extends Player {
    private ConnectFourTester_5SinghSawant graphicsProgram;

    public HumanPlayer(TokenColor color, GraphicsProgram graphicsProgram) {
        super(color);
        this.graphicsProgram = (ConnectFourTester_5SinghSawant) graphicsProgram;
    }


    public int chooseColumn() {
        int column = -1;
        double mouseClickX = -1;
        do {
            mouseClickX = graphicsProgram.getMouseClickX();
            System.out.println(mouseClickX);
            column = graphicsProgram.getColumnIndexFromMouseEvent(graphicsProgram.getMouseClickX());
        } while (column == -1 || mouseClickX == -1);
        System.out.println(column);
        graphicsProgram.resetMouseClickX();
        return column;
    }
}
