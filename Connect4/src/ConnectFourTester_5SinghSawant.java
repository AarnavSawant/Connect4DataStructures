import acm.graphics.GLabel;
import acm.graphics.GOval;
import acm.graphics.GRect;
import acm.graphics.GRoundRect;
import acm.program.GraphicsProgram;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class ConnectFourTester_5SinghSawant extends GraphicsProgram {
    private static final int CIRCLE_SIZE = 25;
    private static final int WIN_CIRCLE_SIZE = (int) (CIRCLE_SIZE * 1.5);
    private static final int BOARD_Y = 60;
    private static final int BOARD_WIDTH = 15 * CIRCLE_SIZE;
    private static final int BOARD_HEIGHT = 13 * CIRCLE_SIZE;
    private static final Color BOARD_COLOR = Color.BLUE;

    private static final int MENU_OFFSET = 100;
    public static final int MENU_CORNER = 25;
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

    public static void main(String[] args) {
        new ConnectFourTester_5SinghSawant().start(args);
    }

    @Override
    public void run() {
        startGame();
    }

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
        double buttonY = (BOARD_Y - buttonHeight) / 2;
        ReplayButton replayButton = new ReplayButton(this, buttonX, buttonY, buttonWidth, buttonHeight);
        replayButton.initiate();
        while (mGame != null) {
            pause(1);
        }
        startGame();
    }

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

        Font font = new Font(Font.SANS_SERIF, Font.BOLD, getHeight() / 10);
        GLabel label = new GLabel("CONNECT");
        label.setFont(font);
        label.setColor(Color.WHITE);
        Font font4 = new Font(font.getFontName(), font.getStyle(), (int) (font.getSize() * 1.5));
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

    private void drawBoard() {
        double boardX = centerWidth(BOARD_WIDTH);
        GRect board = new GRect(boardX, BOARD_Y, BOARD_WIDTH, BOARD_HEIGHT);
        board.setFillColor(BOARD_COLOR);
        board.setFilled(true);
        add(board);
        double currentX = boardX + CIRCLE_SIZE;
        double currentY = BOARD_Y + CIRCLE_SIZE;
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

    private void drawWinToken(double x, double y) {
        double offset = (WIN_CIRCLE_SIZE - CIRCLE_SIZE) / 2;
        GOval borderCircle = new GOval(x - offset, y - offset, WIN_CIRCLE_SIZE, WIN_CIRCLE_SIZE);
        borderCircle.setColor(WIN_COLOR);
        borderCircle.setFillColor(WIN_COLOR);
        borderCircle.setFilled(true);
        add(borderCircle);
    }

    private void drawLabel() {
        GLabel label = new GLabel("");
        label.setFont(new Font(Font.SANS_SERIF, Font.BOLD, getHeight() / 10));
        switch (mGame.getStatus()) {
            case ONGOING:
                label.setLabel(mCurrentPlayer.getName() + "'s turn");
                label.setColor(mCurrentPlayer.getAWTColor());
                break;
            case WIN, FORFEIT:
                label.setLabel(mGame.getWinner().getName() + " wins!");
                label.setColor(mGame.getWinner().getAWTColor());
                break;
            case DRAW:
                label.setLabel("Draw!");
                label.setColor(Color.WHITE);
                break;
        }
        label.setLocation(centerWidth(label.getWidth()), getHeight() - label.getHeight() / 2);
        add(label);
    }
    
    private double centerWidth(double width) {
        return (getWidth() - width) / 2;
    }

    private double centerHeight(double height) {
        return (getHeight() - height) / 2;
    }

    public Connect4Game getGame() {
        return mGame;
    }

    public void resetGame() {
        mGame = null;
        gameMode = null;
        mCurrentPlayer = null;
        mMouseClickX = -1;
    }

    public void setGameMode(GameMode gameMode) {
        this.gameMode = gameMode;
    }

    private void updateGraphics() {
        removeAll();
        setBackground(BACKGROUND_COLOR);
        drawBoard();
        drawLabel();
    }

    /* mouse tracking & graphics handling */
    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        mMouseClickX = mouseEvent.getX();
    }

    public double getMouseClickX() {
        return mMouseClickX;
    }

    public void resetMouseClickX() {
        mMouseClickX = -1;
    }

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
        GOval circle = new GOval(x, BOARD_Y - CIRCLE_SIZE, CIRCLE_SIZE, CIRCLE_SIZE);
        circle.setFillColor(mCurrentPlayer.getAWTColor());
        circle.setFilled(true);
        add(circle);
    }

    public double getColumnCoordinateFromMouseEvent(double x) {
        for (Double d : mBorderXValues) {
            if (x <= d) {
                return d - BORDER_SCALING_FACTOR * CIRCLE_SIZE;
            }
        }
        return mBorderXValues.get(mBorderXValues.size() - 1) - BORDER_SCALING_FACTOR * CIRCLE_SIZE;
    }

    public int getColumnIndexFromMouseEvent(double x) {
        for (int i = 0; i < mBorderXValues.size(); i++) {
            if (x < mBorderXValues.get(i)) {
                return i;
            }
        }
        return mBorderXValues.size() - 1;
    }
}
