public class FullColumnError extends Connect4Exception {
    public FullColumnError(int column) {
        super("Column " + (column + 1) + " is full.");
    }
}
