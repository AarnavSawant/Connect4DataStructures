import java.awt.event.MouseEvent;

/**
 * Subclass of Button that replays the game when clicked.
 */

public class ReplayButton extends Button {
    public ReplayButton(Connect4Graphics graphicsProgram, double x, double y, double w, double h) {
        super(graphicsProgram, x, y, w, h, Connect4Graphics.MENU_CORNER, "Play Again");
    }

    /**
     * Resets the Connect4Graphics program when the button is clicked.
     * 
     * @param e the mouse event
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        getGraphicsProgram().resetGame();
    }
}
