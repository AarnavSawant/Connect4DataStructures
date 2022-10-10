abstract class Player {
    private TokenColor color;
    private int currentColumn;
    private String name;
    private int numWins;

    public Player(TokenColor color) {
        this.color = color;
    }

    public TokenColor getColor() {
        return color;
    }

    abstract public int chooseColumn();

    public int getCurrentColumn() {
        return currentColumn;
    }
}
