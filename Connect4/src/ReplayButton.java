import java.awt.event.MouseEvent;

public class ReplayButton extends Button {
    public ReplayButton(Connect4Graphics graphicsProgram, double x, double y, double w, double h) {
        super(graphicsProgram, x, y, w, h, Connect4Graphics.MENU_CORNER, "Play Again");
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        getGraphicsProgram().resetGame();
    }
}
