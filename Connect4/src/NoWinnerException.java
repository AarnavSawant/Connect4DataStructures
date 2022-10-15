public class NoWinnerException extends Connect4Exception {
    public NoWinnerException() {
        super("The game does not have a winner yet.");
    }
}
