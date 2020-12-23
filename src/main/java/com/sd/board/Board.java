package main.java.com.sd.board;

import main.java.com.sd.moves.Move;
import main.java.com.sd.pieces.Bishop;
import main.java.com.sd.pieces.Piece;
import main.java.com.sd.pieces.Rook;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;

public class Board {
    private static Logger logger = LogManager.getLogger();

    public static final int BOARD_WIDTH = 8;
    public static final int BOARD_HEIGHT = 8;

    private Square[] boardArray = new Square[BOARD_WIDTH * BOARD_HEIGHT];

    public Board() {
        for (int i = 0; i < BOARD_WIDTH; i++) {
            for (int j = 0; j < BOARD_HEIGHT; j++) {
                Square square = new Square(i, j);
                boardArray[square.getSquareNum()] = square;
            }
        }
    }

    // Copy constructor
    public Board(Board board) {
        for (int i = 0; i < Board.BOARD_WIDTH * Board.BOARD_HEIGHT; i++) {
            this.setSquare(board.getSquare(i).makeCopy());
        }
    }

    public Square getSquare(int squareNum) {
        if (squareNum == -1) {
            return null;
        } else {
            return boardArray[squareNum];
        }
    }

    public Square getSquare(int row, int col) {
        int squareNum = Square.convertRowColToSquareNum(row, col);
        return getSquare(squareNum);
    }

    public Square getSquare(String squareName) {
        int squareNum = SquareNames.squareNumberFromName(squareName);
        return getSquare(squareNum);
    }

    public void setSquare(Square square) {
        // TODO set constraints here on valid square
        if (square.getSquareNum() < 0 || square.getSquareNum() > BOARD_HEIGHT * BOARD_WIDTH) {
            logger.error("Can't set square with invalid square number.");
        } else {
            boardArray[square.getSquareNum()] = square;
        }
    }

    public boolean checkValidPosition(Move move) {
        return true;
    }

    public void makeMove(Move move) {
        makeMove(this, move);
    }

    public void makeMove(Board board, Move move) {
        String takenString = "";

        // Set moving piece square to new square
        move.getBoardSquare(board, move.getInitialSquare()).getCurrentPiece().updatePieceSquare(move.getTargetSquare().getSquareNum());

        // Set target square piece to moving piece
        move.getBoardSquare(board, move.getTargetSquare()).updateSquare(move.getInitialSquare().getCurrentPiece());

        // Remove the moving piece from initial square
        move.getBoardSquare(board, move.getInitialSquare()).updateSquare();
    }

    public Board makePotentialMove(Board board, Move move) {
        Board returnBoard = new Board(board);

        makeMove(returnBoard, move);

        return returnBoard;
    }

    @Override
    public String toString() {
        String outStr = "";

        for (int i = BOARD_HEIGHT - 1; i >= 0; i--) {
            for (int j = 0; j < BOARD_WIDTH; j++) {
                if (getSquare(i, j).getCurrentPiece() == null) {
                    outStr += ".";
                } else {
                    outStr += getSquare(i, j).getCurrentPiece().getSymbol(true);
                }
            }
            outStr += "\n";
        }

        return "Board{\n" +
                outStr +
                '}';
    }
}
