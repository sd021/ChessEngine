package main.java.com.sd.moves;

import main.java.com.sd.board.Board;
import main.java.com.sd.board.Square;
import main.java.com.sd.pieces.Piece;

public class EnPassantMove extends Move {
    private final int takenSquare;
    public EnPassantMove(int initialSquare, int targetSquare, int takenSquare, Piece capturedPiece) {
        super(initialSquare, targetSquare, capturedPiece);
        this.takenSquare = takenSquare;

        captureMove = true;
    }

    public Square getTakenSquare(Board board) {
        return board.getSquare(takenSquare);
    }
}
