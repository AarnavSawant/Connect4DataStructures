import acm.program.GraphicsProgram;

public class HumanPlayer extends Player {
    private GraphicsProgram graphicsProgram;

    public HumanPlayer(TokenColor color, GraphicsProgram graphicsProgram) {
        super(color);
        this.graphicsProgram = graphicsProgram;
    }
    
    public int chooseColumn() {
        return 0;
    }
}
