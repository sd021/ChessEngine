package main.java.com.sd.board;

public class Board {
    public static final int BOARD_WIDTH = 8;
    public static final int BOARD_HEIGHT = 8;

    private Square[] boardArray = new Square[64];

    public Board() {
        for (int i = 0; i < BOARD_WIDTH; i++) {
            for (int j = 0; j < BOARD_HEIGHT; j++) {
                Square square = new Square(i, j, BOARD_WIDTH, BOARD_HEIGHT);
                boardArray[square.getSquareNum()] = square;
            }
        }
    }

    public Square getSquare(int row, int col) {
        int squareNum = Square.convertRowColToSquareNum(row, col);

        if (squareNum == -1) {
            return null;
        } else {
            return boardArray[squareNum];
        }
    }

    public Square[] getBoardArray() {
        return boardArray;
    }
}
