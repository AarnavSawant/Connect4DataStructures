import acm.graphics.GLine;
import acm.graphics.GOval;
import acm.graphics.GRect;
import acm.program.GraphicsProgram;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Arrays;

public class ConnectFourTester_5SinghSawant extends GraphicsProgram {
    private static int CIRCLE_SIZE = 30;
    private static int BOARD_X = 100;
    private static int BOARD_Y = 50;
    private static int BOARD_WIDTH = 15 * CIRCLE_SIZE;
    private static int BOARD_HEIGHT = 13 * CIRCLE_SIZE;

    private static int MARGIN_SCALING_FACTOR = 2;

    private static double BORDER_SCALING_FACTOR = 1.5;
    private Connect4Game mGame;

    private Player mCurrentPlayer;

    private ArrayList<Double> mBorderXValues = new ArrayList<>();

    private double mMouseClickX = -1;
    public static void main(String[] args) {
        new ConnectFourTester_5SinghSawant().start(args);
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        mMouseClickX = mouseEvent.getLocationOnScreen().getX();
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
        GOval circle = new GOval(getColumnCoordinateFromMouseEvent(mouseEvent.getX()), BOARD_Y - CIRCLE_SIZE, CIRCLE_SIZE, CIRCLE_SIZE);
        circle.setFillColor(mCurrentPlayer.getColor() == TokenColor.RED ? Color.RED : Color.YELLOW);
        circle.setFilled(true);
        add(circle);
//        System.out.println(mCurrentPlayer.getColor());
//        System.out.println(mouseEvent.getLocationOnScreen());
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
            if (x <= mBorderXValues.get(i)) {
                return i;
            }
        }
        return mBorderXValues.size() - 1;
    }

    @Override
    public void run() {
        HumanPlayer redPlayer = new HumanPlayer(TokenColor.RED, this);
        TestPlayer yellowPlayer = new TestPlayer(TokenColor.YELLOW);
        mCurrentPlayer = redPlayer;
        addMouseListeners();
        mGame = new Connect4Game(redPlayer, yellowPlayer);
        GameStatus status = GameStatus.ONGOING;

//        while (status == GameStatus.ONGOING) {

        for (int i = 0; i < 20 && mGame.getStatus() == GameStatus.ONGOING; i++) {
            mCurrentPlayer = mGame.whoseTurn();
            System.out.println(mCurrentPlayer.getColor());
            try {
                System.out.println(status);
                status = mGame.playTurn(mCurrentPlayer);
                updateGraphics();
            } catch (FullColumnError e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println(status);
        try {
            System.out.println(mGame.getWinner());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

//        }

    }


    private void updateGraphics() {
        removeAll();
        setBackground(Color.BLACK);
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
                add(circle);
                currentX += MARGIN_SCALING_FACTOR * CIRCLE_SIZE;
            }
            currentY += MARGIN_SCALING_FACTOR * CIRCLE_SIZE;
            currentX = BOARD_X + CIRCLE_SIZE;
        }
//        System.out.println(mBorderXValues);
    }
}
