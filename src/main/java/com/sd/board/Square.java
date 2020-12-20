package main.java.com.sd.board;

import main.java.com.sd.pieces.Piece;

import java.lang.Math;

public class Square {
    private final int row, col;
    private final int squareNum; // 0 - 63
    private final int maxRows, maxCols;
    private boolean occupied = false;
    private Piece currentPiece;

    public Square(int row, int col, int width, int height, Piece piece) {
        this.maxRows = width;
        this.maxCols = height;
        this.squareNum =   calcSquareNum(row, col);
        this.row = convertSquareNumToRow(squareNum);
        this.col = convertSquareNumToCol(squareNum);
        this.currentPiece = piece;
        setOccupied(this.currentPiece != null);
    }

    public Square(int row, int col, int width, int height) {
        this(row, col, width, height, null);
    }

    public int getSquareNum() {
        return squareNum;
    }

    public int getSquareRow() {
        return row;
    }

    public int getSquareCol() {
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
        setOccupied(this.currentPiece != null);
    }

    public int getRow() {
        return (squareNum / (maxRows));
    }

    public int getColumn() {
        return squareNum % maxCols;
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
    public String toString()
    {
        String piece;

        if (occupied) {
            piece = currentPiece.toString();
        }
        else {
            piece = "None";
        }

        return "Square (" + SquareNames.squareName(squareNum) +  ") = " + piece;
    }
}
