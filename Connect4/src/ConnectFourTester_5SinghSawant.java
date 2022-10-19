import acm.graphics.GLabel;
import acm.graphics.GOval;
import acm.graphics.GRect;
import acm.program.GraphicsProgram;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class ConnectFourTester_5SinghSawant extends GraphicsProgram {
    private static int CIRCLE_SIZE = 25;
    private static int WIN_CIRCLE_SIZE = (int) (CIRCLE_SIZE * 1.5);
    private static int BOARD_X = 175;
    private static int BOARD_Y = 50;
    private static int BOARD_WIDTH = 15 * CIRCLE_SIZE;
    private static int BOARD_HEIGHT = 13 * CIRCLE_SIZE;

    private static int MARGIN_SCALING_FACTOR = 2;
    private static double BORDER_SCALING_FACTOR = 1.5;

    private static final Color WIN_COLOR = new Color(0, 255, 0);
    private Connect4Game mGame;

    private Player mCurrentPlayer;

    private ArrayList<Double> mBorderXValues = new ArrayList<>();

    private double mMouseClickX = -1;
    public static void main(String[] args) {
        new ConnectFourTester_5SinghSawant().start(args);
    }

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
        updateGraphics();
        if (mCurrentPlayer instanceof HumanPlayer) {
            GOval circle = new GOval(getColumnCoordinateFromMouseEvent(mouseEvent.getX()), BOARD_Y - CIRCLE_SIZE, CIRCLE_SIZE, CIRCLE_SIZE);
            circle.setFillColor(mCurrentPlayer.getColor() == TokenColor.RED ? Color.RED : Color.YELLOW);
            circle.setFilled(true);
            add(circle);    
        }
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
        for (int i = 0; i < mBorderXValues.size(); i++){
            if (x  < mBorderXValues.get(i)) {
                return i;
            }
        }
        return mBorderXValues.size() - 1;
    }

    private void playGame(Player redPlayer, Player yellowPlayer) {
        mCurrentPlayer = redPlayer;
        addMouseListeners();
        mGame = new Connect4Game(redPlayer, yellowPlayer);
        GameStatus status = GameStatus.ONGOING;
        updateGraphics();
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
        try {
            System.out.println(mGame.getWinner());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        HumanPlayer redPlayer = new HumanPlayer(TokenColor.RED, "Player 1", this);
        // HumanPlayer yellowPlayer = new HumanPlayer(TokenColor.YELLOW, "Player 2", this);
        // AIPlayer redPlayer = new AIPlayer(TokenColor.RED);
        AIPlayer yellowPlayer = new AIPlayer(TokenColor.YELLOW);
        playGame(redPlayer, yellowPlayer);
    }

    private void drawBoard() {
        GRect board = new GRect(BOARD_X, BOARD_Y, BOARD_WIDTH, BOARD_HEIGHT);
        board.setFillColor(Color.BLUE);
        board.setFilled(true);
        add(board);
        int currentX = BOARD_X + CIRCLE_SIZE;
        int currentY = BOARD_Y + CIRCLE_SIZE;
        for (int i = 0; i < mGame.getBoard().getNumRows(); i+=1) {
            for (int j = 0; j < mGame.getBoard().getNumCols(); j+=1) {
                if (mBorderXValues.size() < mGame.getBoard().getNumCols()) {
                    if (i == 0) {
                        double imaginaryBorderXValue = currentX + BORDER_SCALING_FACTOR * CIRCLE_SIZE;
                        mBorderXValues.add(imaginaryBorderXValue);
                    }
                }
                GOval circle = new GOval(currentX, currentY, CIRCLE_SIZE, CIRCLE_SIZE);
                if (mGame.getBoard().getBoard()[i][j] == TokenColor.RED) {
                    circle.setFillColor(Color.RED);
                    circle.setFilled(true);
                } else if (mGame.getBoard().getBoard()[i][j] == TokenColor.YELLOW) {
                    circle.setFillColor(Color.YELLOW);
                    circle.setFilled(true);
                } else {
                    circle.setFillColor(Color.BLACK);
                    circle.setFilled(true);
                }
                if (mGame.isWinningIndex(i, j)) {
                    double coordOffset = (WIN_CIRCLE_SIZE - CIRCLE_SIZE) / 2;
                    GOval borderCircle = new GOval(currentX - coordOffset, currentY - coordOffset, WIN_CIRCLE_SIZE, WIN_CIRCLE_SIZE);
                    borderCircle.setFillColor(WIN_COLOR);
                    borderCircle.setFilled(true);
                    add(borderCircle);
                }
                add(circle);
                currentX += MARGIN_SCALING_FACTOR * CIRCLE_SIZE;
            }
            currentY += MARGIN_SCALING_FACTOR * CIRCLE_SIZE;
            currentX = BOARD_X + CIRCLE_SIZE;
        }
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
        label.setLocation((getWidth() - label.getWidth()) / 2, getHeight() - label.getHeight() / 2);
        add(label);
    }

    private void updateGraphics() {
        removeAll();
        setBackground(Color.BLACK);
        drawBoard();
        drawLabel();
    }
}
