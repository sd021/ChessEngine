package main.java.com.sd.moves;

import main.java.com.sd.board.Board;
import main.java.com.sd.board.Square;
import main.java.com.sd.pieces.Piece;

import java.util.Objects;

public abstract class Move {
    protected final Square initialSquare, targetSquare;

    protected boolean captureMove = false;   // Is this a move which involves piece capture
    protected Piece capturedPiece;

    public Move(Square initialSquare, Square targetSquare) {
        this.initialSquare = initialSquare.makeCopy();
        this.targetSquare = targetSquare.makeCopy();
    }

    public void validateMove() {
        // Make sure the initial square has a piece to move
        if (!initialSquare.isOccupied()) {
            throw new IllegalArgumentException("Initial move square must be occupied by a piece! (" + initialSquare.getSquareName() + ", " + initialSquare.getCurrentPiece());
        }

        // If the target square isn't empty, make sure the piece occupying it is a different colour
        if (targetSquare.isOccupied()) {
            if (initialSquare.getCurrentPiece().getColour() == targetSquare.getCurrentPiece().getColour()){
                throw new IllegalArgumentException("Cannot move to a square occupied by a piece of the same colour!");
            }
            captureMove = true;
            capturedPiece = targetSquare.getCurrentPiece();
        } else {
            captureMove = false;
            capturedPiece = null;
        }
    }

    // Since we retain a copy of the squares as they were when the move is made
    // use this method to access the actual square on the board to make the move
    public Square getBoardSquare(Board board, Square square) {
        return board.getSquare(square.getSquareNum());
    }

    public Square getInitialSquare() { return initialSquare; }

    public Square getTargetSquare() { return targetSquare; }

    public Piece getCapturedPiece() { return capturedPiece; }

    public boolean isCaptureMove() { return captureMove; }

    private String captureString() {
        return isCaptureMove() ? " takes " : ", ";
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        Move move = (Move) o;
        return captureMove == move.captureMove &&
                Objects.equals(initialSquare, move.initialSquare) &&
                Objects.equals(targetSquare, move.targetSquare) &&
                Objects.equals(capturedPiece, move.capturedPiece);
    }

    @Override
    public int hashCode() {
        return Objects.hash(initialSquare, targetSquare, captureMove, capturedPiece);
    }

    // TODO proper notation
    @Override
    public String toString() {
        return "Move{" +
                initialSquare.getSquareName() +
                captureString() +
                targetSquare.getSquareName() +
                '}';
    }
}
