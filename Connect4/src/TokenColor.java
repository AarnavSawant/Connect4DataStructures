import java.awt.Color;

/**
 * An enumeration that representing the possible colors of a token.
 */
public enum TokenColor {
    NONE,
    RED,
    YELLOW;

    /**
     * Returns the Color object that corresponds to this TokenColor.
     * 
     * @return Color the color of this token
     */
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
