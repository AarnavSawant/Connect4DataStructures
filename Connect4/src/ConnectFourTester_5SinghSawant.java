public class ConnectFourTester_5SinghSawant {
    public static void main(String[] args) throws Exception {
        Board board = new Board();
        System.out.println(board);
        for (int i = 0; i < 8; i++) {
            board.addPiece(0, 1);
            System.out.println(board);
        }
    }
}
