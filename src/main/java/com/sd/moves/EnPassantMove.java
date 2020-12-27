package main.java.com.sd.moves;

import main.java.com.sd.board.Square;

public class EnPassantMove extends Move {
    private final Square takenSquare;
    public EnPassantMove(Square initialSquare, Square targetSquare, Square takenSquare) {
        super(initialSquare, targetSquare);
        this.takenSquare = takenSquare;

        captureMove = true;
        capturedPiece = takenSquare.getCurrentPiece();
    }

    public Square getTakenSquare() {
        return takenSquare;
    }
}
