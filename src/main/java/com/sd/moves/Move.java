package main.java.com.sd.moves;

import main.java.com.sd.board.Board;
import main.java.com.sd.board.Square;
import main.java.com.sd.pieces.King;
import main.java.com.sd.pieces.Pawn;
import main.java.com.sd.pieces.Piece;
import main.java.com.sd.pieces.Rook;

public class Move {
    private final Square initialSquare, targetSquare;
    private Square secondaryInitialSquare, secondaryTargetSquare;
    private boolean captureMove;   // Is this a move which involves piece capture
    private Piece capturedPiece;

    public Move(Square initialSquare, Square targetSquare) {
        this.initialSquare = initialSquare.makeCopy();
        this.targetSquare = targetSquare.makeCopy();

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


    // For castling & en-passant
    public Move(Square primaryInitialSquare, Square primaryTargetSquare, Square secondaryInitialSquare,
                Square secondaryTargetSquare) {
        this.initialSquare = primaryInitialSquare.makeCopy();
        this.targetSquare = primaryTargetSquare.makeCopy();
        this.secondaryInitialSquare = secondaryInitialSquare.makeCopy();
        this.secondaryTargetSquare = secondaryTargetSquare.makeCopy();

        if (initialSquare.getCurrentPiece().getClass() != King.class && secondaryInitialSquare.getCurrentPiece().getClass() != Rook.class) {
            throw new IllegalArgumentException("Main moving piece must be a King and secondary piece must be a rook (Castling)");
        }

        if (initialSquare.getCurrentPiece().getClass() != Pawn.class && secondaryTargetSquare != null) {
            throw new IllegalArgumentException("Main moving piece must be a pawn when doing en passant");
        }

        //TODO finish
    }

    public Square getInitialSquare() { return initialSquare; }

    public Square getTargetSquare() { return targetSquare; }

    public Piece getCapturedPiece() { return capturedPiece; }

    public boolean isCaptureMove() { return captureMove; }

    // Since we retain a copy of the squares as they were when the move is made
    // use this method to access the actual square on the board to make the move
    public Square getBoardSquare(Board board, Square square) {
        return board.getSquare(square.getSquareNum());
    }

    private String captureString() {
        return isCaptureMove() ? " takes " : ", ";
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
