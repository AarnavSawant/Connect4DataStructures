abstract class Player {
    private TokenColor color;
    private int currentColumn;
    private String name;
    private int numWins;

    public Player(TokenColor color) {
        this.color = color;
    }

    public String toString() {
        return getClass().getName() + "[name: " + name + ", color: " + color + ", numWins: " + numWins + "]";
    }

    public TokenColor getColor() {
        return color;
    }

    abstract public int chooseColumn();

    public int getCurrentColumn() {
        return currentColumn;
    }
}
