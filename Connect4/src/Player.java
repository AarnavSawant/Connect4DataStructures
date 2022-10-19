import java.awt.Color;

abstract class Player {
    private TokenColor color;
    private int currentColumn;
    private String name;
    private int numWins;

    public Player(TokenColor color, String name) {
        this.color = color;
        this.name = name;
        this.numWins = 0;
    }

    public String toString() {
        return name;
    }

    public String getName() {
        return name;
    }

    public TokenColor getColor() {
        return color;
    }

    public Color getAWTColor() {
        return color == TokenColor.RED ? Color.RED : Color.YELLOW;
    }

    abstract public int chooseColumn() throws FullColumnError;

    public int getCurrentColumn() {
        return currentColumn;
    }
}
