package main.java.com.sd.moves;

import main.java.com.sd.board.Board;
import main.java.com.sd.board.Square;
import main.java.com.sd.board.SquareNames;
import main.java.com.sd.pieces.Piece;

import java.util.Objects;

public abstract class Move {
    protected final int initialSquare, targetSquare;

    protected boolean captureMove = false;   // Is this a move which involves piece capture
    protected Piece capturedPiece;

    public Move(int initialSquare, int targetSquare, Piece capturedPiece) {
        this.initialSquare = initialSquare;
        this.targetSquare = targetSquare;
        this.capturedPiece = capturedPiece;

        if (capturedPiece == null) {
            this.captureMove = false;
        } else {
            this.captureMove = true;
        }
    }

    public Move(int initialSquare, int targetSquare) {
        this(initialSquare, targetSquare, null);
    }

    // Since we retain a copy of the squares as they were when the move is made
    // use this method to access the actual square on the board to make the move
    public Square getBoardSquare(Board board, Square square) {
        return board.getSquare(square.getSquareNum());
    }

    public Square getInitialSquare(Board board) { return board.getSquare(initialSquare); }

    public Square getTargetSquare(Board board) { return board.getSquare(targetSquare); }

    public int getInitialSquare() {
        return initialSquare;
    }

    public int getTargetSquare() {
        return targetSquare;
    }

    public Piece getCapturedPiece() { return capturedPiece; }

    public boolean isCaptureMove() { return captureMove; }

    public void setCaptureMove(boolean isCaptureMove) {
        this.captureMove = isCaptureMove;
    }

    private String captureString() {
        return isCaptureMove() ? ", PieceTaken: " + capturedPiece.getColour() + " " +capturedPiece.getPieceName() : "";
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        else return false;
//        Move move = (Move) o;
//        return captureMove == move.captureMove &&
//                Objects.equals(initialSquare, move.initialSquare) &&
//                Objects.equals(targetSquare, move.targetSquare) &&
//                Objects.equals(capturedPiece, move.capturedPiece);
    }

    @Override
    public int hashCode() {
        return Objects.hash(initialSquare, targetSquare, captureMove, capturedPiece);
    }

    // TODO proper notation
    @Override
    public String toString() {
        return "Move{" +
                SquareNames.squareNameFromNumber(initialSquare) +
                ", " +
                SquareNames.squareNameFromNumber(targetSquare) +
                captureString() +
                '}';
    }
}
