import acm.graphics.GOval;
import acm.graphics.GRect;
import acm.program.GraphicsProgram;

import java.awt.*;

public class ConnectFourTester_5SinghSawant extends GraphicsProgram {
    private static int CIRCLE_SIZE = 30;
    private static int BOARD_X = 100;
    private static int BOARD_Y = 50;
    private static int BOARD_WIDTH = 15 * CIRCLE_SIZE;
    private static int BOARD_HEIGHT = 13 * CIRCLE_SIZE;

    private static int MARGIN_SCALING_FACTOR = 2;

    private Board mBoard = new Board();
    public static void main(String[] args) {
        Board board = new Board();
        System.out.println(board);
        board.addPiece(3, PlayerColor.RED);
        System.out.println(board);
        new ConnectFourTester_5SinghSawant().start(args);

    }

    @Override
    public void run() {
        setBackground(Color.BLACK);
        GRect board = new GRect(BOARD_X, BOARD_Y, BOARD_WIDTH, BOARD_HEIGHT);
        board.setFillColor(Color.BLUE);
        board.setFilled(true);
        mBoard.addPiece(3, PlayerColor.RED);
        mBoard.addPiece(3, PlayerColor.RED);
        mBoard.addPiece(3, PlayerColor.RED);
        mBoard.addPiece(3, PlayerColor.RED);
        mBoard.addPiece(2, PlayerColor.YELLOW);
        mBoard.addPiece(1, PlayerColor.YELLOW);
        mBoard.addPiece(4, PlayerColor.YELLOW);
        mBoard.addPiece(4, PlayerColor.YELLOW);
        mBoard.addPiece(5, PlayerColor.YELLOW);
        add(board);
        int currentX = BOARD_X + CIRCLE_SIZE;
        int currentY = BOARD_Y + CIRCLE_SIZE;
        for (int i = 0; i < mBoard.getNumRows(); i+=1) {
            for (int j = 0; j < mBoard.getNumCols(); j+=1) {
                GOval circle = new GOval(currentX, currentY, CIRCLE_SIZE, CIRCLE_SIZE);
                if (mBoard.getBoard()[i][j] == PlayerColor.RED.ordinal()) {
                    circle.setFillColor(Color.RED);
                    circle.setFilled(true);
                } else if (mBoard.getBoard()[i][j] == PlayerColor.YELLOW.ordinal()) {
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
    }
}
