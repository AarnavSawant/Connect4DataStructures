import java.awt.event.MouseEvent;

public class ReplayButton extends Button {
    public ReplayButton(ConnectFourTester_5SinghSawant graphicsProgram, double x, double y, double w, double h) {
        super(graphicsProgram, x, y, w, h, ConnectFourTester_5SinghSawant.MENU_CORNER, "Play Again");
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        getGraphicsProgram().resetGame();
    }
}
