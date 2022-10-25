import java.awt.event.MouseEvent;

public class AIButton extends Button {
    public AIButton(Connect4Graphics graphicsProgram, double x, double y, double w, double h) {
        super(graphicsProgram, x, y, w, h, Connect4Graphics.MENU_CORNER, "Human vs. AI");
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        getGraphicsProgram().setGameMode(GameMode.AI);
    }
}
