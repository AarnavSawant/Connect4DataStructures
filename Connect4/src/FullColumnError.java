/**
 * Exception called when a Player tries to drop a piece into a full column.
 */
public class FullColumnError extends Connect4Exception {
    public FullColumnError(int column) {
        super("Column " + (column + 1) + " is full.");
    }
}
