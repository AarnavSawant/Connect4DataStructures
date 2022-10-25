import java.awt.event.MouseEvent;

public class HumanButton extends Button {
    public HumanButton(ConnectFourTester_5SinghSawant graphicsProgram, double x, double y, double w, double h) {
        super(graphicsProgram, x, y, w, h, ConnectFourTester_5SinghSawant.MENU_CORNER, "Human vs. Human");
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        getGraphicsProgram().setGameMode(GameMode.HUMAN);
    }
}
