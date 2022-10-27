/**
 * A developer-only testing class.
 */
class TestPlayer extends Player{
    int col = 0;

    public TestPlayer(TokenColor color) {
        super(color, "Test AI");
    }

    private int randomColumn() {
        return (int) (Math.random() * 7);
    }

    public int chooseColumn() {
        return randomColumn();
    }
}