class TestPlayer extends Player{
    int col = 0;

    public TestPlayer(TokenColor color) {
        super(color);
    }

    public int chooseColumn() {
        return (int) (Math.random() * 8 - 1);
    }
}