package main.java.com.sd.board;

import main.java.com.sd.pieces.Piece;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.Math;
import java.util.Objects;

public class Square {
    private static Logger logger = LogManager.getLogger();

    private final int row, col;
    private final int squareNum; // 0 - 63
    private final int maxRows= Board.BOARD_HEIGHT;
    private final int maxCols= Board.BOARD_WIDTH;
    private boolean occupied = false;
    private Piece currentPiece;

    public Square(int row, int col, Piece piece) {
        if (row < 0 || row >= Board.BOARD_HEIGHT || col < 0 || col > Board.BOARD_WIDTH) {
            logger.error("Cannot create square with provided row/column values (" + row + ", " + col + ")");
        }

        this.squareNum =  calcSquareNum(row, col);
        this.row = row;
        this.col = col;
        this.currentPiece = piece;
        setOccupied(this.currentPiece != null);
    }

    public Square(int row, int col) {
        this(row, col, null);
    }

    // Copy constructor
    public Square makeCopy() {

        Piece pieceCopy = getCurrentPiece() == null ? null : this.getCurrentPiece().makeCopy();

        // TODO square piece can be null here
        return new Square(this.getRow(), this.getColumn(), pieceCopy);
    }

    public int getSquareNum() {
        return squareNum;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return col;
    }

    public boolean isOccupied() {
        return occupied;
    }

    private void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }

    public Piece getCurrentPiece() {
        return currentPiece;
    }

    public void setCurrentPiece(Piece currentPiece) {
        this.currentPiece = currentPiece;

    }

    public void updateSquare(Piece piece) {
        setCurrentPiece(piece);
        setOccupied(this.currentPiece != null);
    }

    public void updateSquare() {
        setCurrentPiece(null);
        setOccupied(this.currentPiece != null);
    }

    public String getSquareName() {
        return SquareNames.squareNameFromNumber(this.squareNum);
    }

    private int calcSquareNum(int row, int col) {
        return Math.min(((row * maxCols) + col), maxRows * maxCols);
    }

    public static int convertRowColToSquareNum(int row, int col) {
        if (row < 0 || row > Board.BOARD_HEIGHT - 1 || col < 0 || col > Board.BOARD_WIDTH -1) {
            return -1;
        }

        return Math.min(((row * Board.BOARD_HEIGHT) + col), (Board.BOARD_WIDTH * Board.BOARD_HEIGHT));
    }

    // TODO add tests for these
    public static int convertSquareNumToRow(int squareNum) {
        return ((int) Math.floor(squareNum / Board.BOARD_HEIGHT));
    }

    public static int convertSquareNumToCol(int squareNum) {
        return squareNum % Board.BOARD_WIDTH;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Square square = (Square) o;
        return row == square.row &&
                col == square.col &&
                squareNum == square.squareNum &&
                maxRows == square.maxRows &&
                maxCols == square.maxCols;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, col, squareNum, maxRows, maxCols, occupied, currentPiece);
    }

    @Override
    public String toString()
    {
        String piece;

        if (occupied) {
            piece = currentPiece.toString();
        }
        else {
            piece = "None";
        }

        return "Square{" + SquareNames.squareNameFromNumber(squareNum) +
                ", " + piece +
                "}";
    }
}
