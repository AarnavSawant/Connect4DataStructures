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
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            column = graphicsProgram.getColumnIndexFromMouseEvent(graphicsProgram.getMouseClickX());
        } while (column == -1 || mouseClickX == -1);
        graphicsProgram.resetMouseClickX();
        return column;
    }
}
