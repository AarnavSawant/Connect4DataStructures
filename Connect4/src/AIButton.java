import java.awt.event.MouseEvent;

public class AIButton extends Button {
    public AIButton(ConnectFourTester_5SinghSawant graphicsProgram, double x, double y, double w, double h) {
        super(graphicsProgram, x, y, w, h, ConnectFourTester_5SinghSawant.MENU_CORNER, "Human vs. AI");
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        getGraphicsProgram().setGameMode(GameMode.AI);
    }
}
