import java.awt.event.MouseEvent;

/**
 * Subclass of Button that sets the gamemode to play against a human when clicked.
 */
public class HumanButton extends Button {
    public HumanButton(Connect4Graphics graphicsProgram, double x, double y, double w, double h) {
        super(graphicsProgram, x, y, w, h, Connect4Graphics.MENU_CORNER, "Human vs. Human");
    }

    /**
     * Sets the gamemode to play against a human when the button is clicked.
     * 
     * @param e the mouse event
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        getGraphicsProgram().setGameMode(GameMode.HUMAN);
    }
}
