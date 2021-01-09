package main.java.com.sd.moves;

import main.java.com.sd.board.Square;
import main.java.com.sd.pieces.King;
import main.java.com.sd.pieces.Rook;

public class CastlingMove extends Move {
    private int secondaryInitialSquare, secondaryTargetSquare;
    private boolean kingSide;

    public CastlingMove(int primaryInitialSquare, int primaryTargetSquare, int secondaryInitialSquare,
                        int secondaryTargetSquare) {
        super(primaryInitialSquare, primaryTargetSquare);
        this.secondaryInitialSquare = secondaryInitialSquare;
        this.secondaryTargetSquare = secondaryTargetSquare;

        this.captureMove = false;

        // If the rook involved is on the 1 file it is a queenside castle
        if (Square.convertSquareNumToCol(secondaryInitialSquare) == 0) kingSide = false;
        else kingSide = true;
    }

    public Move getSecondaryMove() {
        return new BasicMove(secondaryInitialSquare, secondaryTargetSquare);
    }


    private String getCastleType() {
        if (kingSide) return "Kingside";
        else return "Queenside";
    }

    @Override
    public String toString() {
        return "CastlingMove{" +
                "" + getCastleType() +
                '}';
    }
}
