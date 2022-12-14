import acm.graphics.GLabel;
import acm.graphics.GOval;
import acm.graphics.GRect;
import acm.graphics.GRoundRect;
import acm.program.GraphicsProgram;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 * Graphics wrapper for Connect4Game that displays all GUI components
 * to the user.
 */
public class Connect4Graphics extends GraphicsProgram {
    private static final int CIRCLE_SIZE = 25;
    private static final int WIN_CIRCLE_SIZE = (int) (CIRCLE_SIZE * 1.5);
    private static final int BOARD_WIDTH = 15 * CIRCLE_SIZE;
    private static final int BOARD_HEIGHT = 13 * CIRCLE_SIZE;
    private static final Color BOARD_COLOR = Color.BLUE;

    private static final int MENU_OFFSET = 100;
    public static final int MENU_CORNER = 25;
    private static final double MENU_FONT_SCALE = 0.1;
    private static final double MENU_4_SCALE = 1.5;
    private static final double BUTTON_WIDTH_SCALE = 0.45;
    private static final double BUTTON_HEIGHT_SCALE = 0.1;

    private static final int MARGIN_SCALING_FACTOR = 2;
    private static double BORDER_SCALING_FACTOR = 1.5;

    private static final Color WIN_COLOR = new Color(0, 255, 0);
    private static final Color BACKGROUND_COLOR = Color.BLACK;

    private Connect4Game mGame;
    private Player mCurrentPlayer;
    private ArrayList<Double> mBorderXValues = new ArrayList<>();
    private GameMode gameMode;

    private double mMouseClickX = -1;

    @Override
    public void run() {
        startGame();
    }

    /**
     * Draws the start screen and waits for the user to click a button.
     * Then initiates the game based on which gamemode the user selected.
     */
    public void startGame() {
        drawMenu();
        while (gameMode == null) {
            pause(1);
        }
        Player redPlayer;
        Player yellowPlayer;
        switch (gameMode) {
            case HUMAN:
                redPlayer = new HumanPlayer(TokenColor.RED, "Player 1", this);
                yellowPlayer = new HumanPlayer(TokenColor.YELLOW, "Player 2", this);
                break;
            case AI:
                redPlayer = new HumanPlayer(TokenColor.RED, "Player 1", this);
                yellowPlayer = new AIPlayer(TokenColor.YELLOW);
                break;
            case TEST:
                redPlayer = new HumanPlayer(TokenColor.RED, "Player 1", this);
                yellowPlayer = new TestPlayer(TokenColor.YELLOW);
                break;
            default:
                redPlayer = new HumanPlayer(TokenColor.RED, "Player 1", this);
                yellowPlayer = new HumanPlayer(TokenColor.YELLOW, "Player 2", this);
                break;
        }
        playGame(redPlayer, yellowPlayer);
    }

    /**
     * Initiates the Connect4Game and plays turn-by-turn until a player wins or the
     * game ends.
     * Once the game ends, it displays the winner and a replay button, which calls
     * startGame() again.
     * 
     * @param redPlayer    The red player
     * @param yellowPlayer The yellow player
     */
    private void playGame(Player redPlayer, Player yellowPlayer) {
        mCurrentPlayer = redPlayer;
        addMouseListeners();
        mGame = new Connect4Game(redPlayer, yellowPlayer);
        GameStatus status = mGame.getStatus();
        updateGraphics();
        resetMouseClickX();
        while (status == GameStatus.ONGOING) {
            mCurrentPlayer = mGame.whoseTurn();
            if (mCurrentPlayer instanceof AIPlayer) {
                ((AIPlayer) mCurrentPlayer).readCurrentBoard(mGame.getBoard());
            }
            updateGraphics();
            try {
                status = mGame.playTurn(mCurrentPlayer);
            } catch (FullColumnError e) {
                throw new RuntimeException(e);
            }
        }
        updateGraphics();

        double buttonWidth = getWidth() * BUTTON_WIDTH_SCALE;
        double buttonHeight = getHeight() * BUTTON_HEIGHT_SCALE;
        double buttonX = centerWidth(buttonWidth);
        double buttonY = (centerHeight(BOARD_HEIGHT) - buttonHeight) / 2;
        ReplayButton replayButton = new ReplayButton(this, buttonX, buttonY, buttonWidth, buttonHeight);
        replayButton.initiate();
        while (mGame != null) {
            pause(1);
        }
        startGame();
    }

    /**
     * Draws the start screen of the game, with buttons to play against another
     * human or AI.
     */
    private void drawMenu() {
        removeAll();
        setBackground(BACKGROUND_COLOR);

        double menuWidth = getWidth() - MENU_OFFSET;
        double menuHeight = getHeight() - MENU_OFFSET;
        GRect menu = new GRoundRect(centerWidth(menuWidth), centerHeight(menuHeight), menuWidth, menuHeight,
                MENU_CORNER, MENU_CORNER);
        menu.setFillColor(BOARD_COLOR);
        menu.setFilled(true);
        add(menu);

        Font font = new Font(Font.SANS_SERIF, Font.BOLD, (int) (getHeight() * MENU_FONT_SCALE));
        GLabel label = new GLabel("CONNECT");
        label.setFont(font);
        label.setColor(Color.WHITE);
        Font font4 = new Font(font.getFontName(), font.getStyle(), (int) (font.getSize() * MENU_4_SCALE));
        GLabel label4 = new GLabel("4");
        label4.setFont(font4);
        label4.setColor(Color.RED);

        label.setLocation(centerWidth(label.getWidth() + label4.getWidth()),
                centerHeight(menuHeight) + label.getHeight());
        label4.setLocation(label.getX() + label.getWidth(), centerHeight(menuHeight) + label.getHeight());
        add(label);
        add(label4);

        double buttonWidth = getWidth() * BUTTON_WIDTH_SCALE;
        double buttonHeight = getHeight() * BUTTON_HEIGHT_SCALE;
        double buttonX = centerWidth(buttonWidth);
        double hButtonY = centerHeight(menuHeight) + menuHeight / 2 - buttonHeight / 2;
        HumanButton humanButton = new HumanButton(this, buttonX, hButtonY, buttonWidth, buttonHeight);
        humanButton.initiate();

        double aiButtonY = hButtonY + buttonHeight + buttonHeight / 2;
        AIButton aiButton = new AIButton(this, buttonX, aiButtonY, buttonWidth, buttonHeight);
        aiButton.initiate();
    }

    /**
     * Draws the game board and played tokens. If the game has been won,
     * it outlines the winning tokens with a green border.
     */
    private void drawBoard() {
        double boardX = centerWidth(BOARD_WIDTH);
        double boardY = centerHeight(BOARD_HEIGHT);
        GRect board = new GRect(boardX, boardY, BOARD_WIDTH, BOARD_HEIGHT);
        board.setFillColor(BOARD_COLOR);
        board.setFilled(true);
        add(board);
        double currentX = boardX + CIRCLE_SIZE;
        double currentY = boardY + CIRCLE_SIZE;
        for (int i = 0; i < mGame.getBoard().getNumRows(); i += 1) {
            for (int j = 0; j < mGame.getBoard().getNumCols(); j += 1) {
                if (mBorderXValues.size() < mGame.getBoard().getNumCols()) {
                    if (i == 0) {
                        double imaginaryBorderXValue = currentX + BORDER_SCALING_FACTOR * CIRCLE_SIZE;
                        mBorderXValues.add(imaginaryBorderXValue);
                    }
                }
                GOval circle = new GOval(currentX, currentY, CIRCLE_SIZE, CIRCLE_SIZE);
                Color fillColor = mGame.getBoard().getBoard()[i][j].getColor();
                circle.setColor(fillColor);
                circle.setFillColor(fillColor);
                circle.setFilled(true);

                if (mGame.isWinningIndex(i, j)) {
                    drawWinToken(currentX, currentY);
                }
                add(circle);
                currentX += MARGIN_SCALING_FACTOR * CIRCLE_SIZE;
            }
            currentY += MARGIN_SCALING_FACTOR * CIRCLE_SIZE;
            currentX = boardX + CIRCLE_SIZE;
        }
    }

    /**
     * Helper method for drawing a green border around a winning token.
     * 
     * @param x The x-coordinate of the token
     * @param y The y-coordinate of the token
     */
    private void drawWinToken(double x, double y) {
        double offset = (WIN_CIRCLE_SIZE - CIRCLE_SIZE) / 2;
        GOval borderCircle = new GOval(x - offset, y - offset, WIN_CIRCLE_SIZE, WIN_CIRCLE_SIZE);
        borderCircle.setColor(WIN_COLOR);
        borderCircle.setFillColor(WIN_COLOR);
        borderCircle.setFilled(true);
        add(borderCircle);
    }

    /**
     * Method for drawing the game label, which either displays the current player's
     * turn
     * or signals whether the game has been won or come to a draw. The label changes
     * color
     * based on the current player's turn.
     */
    private void drawLabel() {
        GLabel label = new GLabel("");
        double fontSize = getHeight() / 10.0;
        label.setFont(new Font(Font.SANS_SERIF, Font.BOLD, (int) Math.round(fontSize)));
        switch (mGame.getStatus()) {
            case ONGOING:
                label.setLabel(mCurrentPlayer.getName() + "'s turn");
                label.setColor(mCurrentPlayer.getAWTColor());
                break;
            case WIN:
                label.setLabel(mGame.getWinner().getName() + " wins!");
                label.setColor(mGame.getWinner().getAWTColor());
                break;
            case DRAW:
                label.setLabel("Draw!");
                label.setColor(Color.WHITE);
                break;
        }
        double lowerBound = centerHeight(BOARD_HEIGHT) + BOARD_HEIGHT;
        double lowerGap = getHeight() - lowerBound;
        double yOffset = (lowerGap - fontSize) / 2;
        double buttonY = getHeight() - yOffset;
        label.setLocation(centerWidth(label.getWidth()), buttonY);
        add(label);
    }

    /**
     * Returns the x-coordinate of an object centered horizontally.
     * 
     * @param width the width of the object
     * 
     * @return the centered x-coordinate of the object relative to the window
     */
    private double centerWidth(double width) {
        return (getWidth() - width) / 2;
    }

    /**
     * Returns the y-coordinate of an object centered vertically.
     * 
     * @param height the height of the object
     * 
     * @return the centered y-coordinate of the object relative to the window
     */
    private double centerHeight(double height) {
        return (getHeight() - height) / 2;
    }

    public Connect4Game getGame() {
        return mGame;
    }

    /**
     * Resets instance variables before beginning a new game.
     */
    public void resetGame() {
        mGame = null;
        gameMode = null;
        mCurrentPlayer = null;
        mMouseClickX = -1;
    }

    /**
     * Sets the player game mode.
     * 
     * @param gameMode GameMode
     */
    public void setGameMode(GameMode gameMode) {
        this.gameMode = gameMode;
    }

    /**
     * Clears and redraws the game board.
     */
    private void updateGraphics() {
        removeAll();
        setBackground(BACKGROUND_COLOR);
        drawBoard();
        drawLabel();
    }

    /* mouse tracking & graphics handling */
    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        if (mCurrentPlayer instanceof HumanPlayer) {
            mMouseClickX = mouseEvent.getX();
        }
    }

    /**
     * Returns the x-coordinate of the last mouse click.
     * 
     * @return the x-coordinate of the last mouse click
     */
    public double getMouseClickX() {
        return mMouseClickX;
    }

    /**
     * Resets the mouse click x-coordinate.
     */
    public void resetMouseClickX() {
        mMouseClickX = -1;
    }

    /**
     * Tracks the mouse movement and moves the current player's selection token
     * accordingly.
     */
    @Override
    public void mouseMoved(MouseEvent mouseEvent) {
        if (mGame == null || mGame.getStatus() != GameStatus.ONGOING) {
            return;
        }
        updateGraphics();
        if (!(mCurrentPlayer instanceof HumanPlayer)) {
            return;
        }
        int column = getColumnIndexFromMouseEvent(mouseEvent.getX());
        if (mGame.getBoard().isColumnFull(column)) {
            return;
        }
        double x = getColumnCoordinateFromMouseEvent(mouseEvent.getX());
        double boardTop = centerHeight(BOARD_HEIGHT) - CIRCLE_SIZE;
        GOval circle = new GOval(x, boardTop, CIRCLE_SIZE, CIRCLE_SIZE);
        circle.setFillColor(mCurrentPlayer.getAWTColor());
        circle.setFilled(true);
        add(circle);
    }

    /**
     * Helper method to determine the column x-cordinate from a mouse event's
     * x-coordinate.
     * 
     * @param x the x-coordinate of the mouse event
     * @return the x-coordinate of the column
     */
    public double getColumnCoordinateFromMouseEvent(double x) {
        for (Double d : mBorderXValues) {
            if (x <= d) {
                return d - BORDER_SCALING_FACTOR * CIRCLE_SIZE;
            }
        }
        return mBorderXValues.get(mBorderXValues.size() - 1) - BORDER_SCALING_FACTOR * CIRCLE_SIZE;
    }

    /**
     * Helper method to determine the column index from a mouse event's
     * x-coordinate.
     * 
     * @param x the x-coordinate of the mouse event
     * @return the closest column's index
     */
    public int getColumnIndexFromMouseEvent(double x) {
        for (int i = 0; i < mBorderXValues.size(); i++) {
            if (x < mBorderXValues.get(i)) {
                return i;
            }
        }
        return mBorderXValues.size() - 1;
    }
}
