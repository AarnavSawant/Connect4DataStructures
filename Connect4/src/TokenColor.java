import java.awt.Color;

public enum TokenColor {
    NONE,
    RED,
    YELLOW;

    public Color getColor() {
        switch (this) {
            case RED:
                return Color.RED;
            case YELLOW:
                return Color.YELLOW;
            default:
                return Color.BLACK;
        }
    }
}
