import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.Font;

import acm.graphics.GLabel;
import acm.graphics.GRoundRect;

abstract class Button extends GRoundRect {
    private static final Color COLOR = Color.WHITE;
    private static final Color COLOR_HOVER = Color.GRAY;
    private static final Color COLOR_CLICK = Color.DARK_GRAY;
    private ConnectFourTester_5SinghSawant graphicsProgram;

    private GLabel label;

    public Button(ConnectFourTester_5SinghSawant graphicsProgram, double x, double y, double width, double height,
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

    public void initiate() {
        graphicsProgram.add(this);
        graphicsProgram.add(label);
    }

    public ConnectFourTester_5SinghSawant getGraphicsProgram() {
        return graphicsProgram;
    }

    abstract public void mouseClicked(MouseEvent e);

    public void mouseEntered(MouseEvent e) {
        setColor(COLOR_HOVER);
        setFillColor(COLOR_HOVER);
    }

    public void mouseExited(MouseEvent e) {
        setColor(COLOR);
        setFillColor(COLOR);
    }

    public void mousePressed(MouseEvent e) {
        setColor(COLOR_CLICK);
        setFillColor(COLOR_CLICK);
    }

    public void mouseReleased(MouseEvent e) {
        setColor(COLOR_HOVER);
        setFillColor(COLOR_HOVER);
    }
}

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
