import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.Font;

import acm.graphics.GLabel;
import acm.graphics.GRoundRect;

/**
 * Abstract class that creates a rounded, labeled Button.
 * Subclasses are expected to override the Constructor and mouseClicker() method
 * to provide custom functionality.
 */
abstract public class Button extends GRoundRect {
    private static final Color COLOR = Color.WHITE;
    private static final Color COLOR_HOVER = Color.GRAY;
    private static final Color COLOR_CLICK = Color.DARK_GRAY;
    private Connect4Graphics graphicsProgram;

    private GLabel label;

    /**
     * Creates a new Button with the given text and dimensions.
     * 
     * @param graphicsProgram the Connect4Graphics program
     * @param x               the x-coordinate of the Button
     * @param y               the y-coordinate of the Button
     * @param width           the width of the Button
     * @param height          the height of the Button
     * @param cornerRadius    the corner radius of the Button
     * @param text            the text to display on the Button
     */
    public Button(Connect4Graphics graphicsProgram, double x, double y, double width, double height,
            double cornerRadius, String text) {
        super(x, y, width, height, cornerRadius, cornerRadius);
        this.graphicsProgram = graphicsProgram;
        setColor(COLOR);
        setFillColor(COLOR);
        setFilled(true);
        addMouseListener(new ButtonMouseListener(this));

        double fontSize = (int) (height * 0.75);
        Font font = new Font(Font.SANS_SERIF, Font.BOLD, (int) fontSize);
        label = new GLabel(text);
        label.setFont(font);
        double labelX = (graphicsProgram.getWidth() - label.getWidth()) / 2;
        label.setLocation(labelX, y + fontSize);
        label.setColor(Color.RED);
    }

    /**
     * Adds the Button to the graphics program.
     */
    public void initiate() {
        graphicsProgram.add(this);
        graphicsProgram.add(label);
    }

    /**
     * Returns the Connect4Graphics this button is associated with.
     * 
     * @return Connect4Graphics
     */
    public Connect4Graphics getGraphicsProgram() {
        return graphicsProgram;
    }

    /**
     * Abstract method that is called when the Button is clicked.
     * Subclasses must override this callback and implement functionality when the
     * Button is clicked.
     * 
     * @param e the MouseEvent that triggered the callback
     */
    abstract public void mouseClicked(MouseEvent e);

    /**
     * Darkens the color of the Button when the mouse is hovering over it.
     */
    public void mouseEntered(MouseEvent e) {
        setColor(COLOR_HOVER);
        setFillColor(COLOR_HOVER);
    }

    /**
     * Resets the color of the Button when the mouse is no longer hovering over it.
     */
    public void mouseExited(MouseEvent e) {
        setColor(COLOR);
        setFillColor(COLOR);
    }

    /**
     * Darkens the color of the Button when the mouse is clicked.
     */
    public void mousePressed(MouseEvent e) {
        setColor(COLOR_CLICK);
        setFillColor(COLOR_CLICK);
    }

    /**
     * Resets the color of the Button when the mouse is no longer clicked.
     */
    public void mouseReleased(MouseEvent e) {
        setColor(COLOR_HOVER);
        setFillColor(COLOR_HOVER);
    }
}

/**
 * MouseListener for the Button class. All methods on this class
 * call their respective identically named methods on the Button class
 * when the button is interacted with.
 */
class ButtonMouseListener implements MouseListener {
    private Button button;

    public ButtonMouseListener(Button button) {
        super();
        this.button = button;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        button.mouseClicked(e);
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        button.mouseEntered(e);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        button.mouseExited(e);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        button.mousePressed(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        button.mouseReleased(e);
    }
}
