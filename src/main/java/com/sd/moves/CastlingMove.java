package main.java.com.sd.moves;

import main.java.com.sd.board.Square;
import main.java.com.sd.pieces.King;
import main.java.com.sd.pieces.Rook;

public class CastlingMove extends Move {
    private Square secondaryInitialSquare, secondaryTargetSquare;
    private boolean kingSide;

    public CastlingMove(Square primaryInitialSquare, Square primaryTargetSquare, Square secondaryInitialSquare,
                        Square secondaryTargetSquare) {
        super(primaryInitialSquare, primaryTargetSquare);
        this.secondaryInitialSquare = secondaryInitialSquare.makeCopy();
        this.secondaryTargetSquare = secondaryTargetSquare.makeCopy();

        this.captureMove = false;

        if (initialSquare.getCurrentPiece().getClass() != King.class && secondaryInitialSquare.getCurrentPiece().getClass() != Rook.class) {
            throw new IllegalArgumentException("Main moving piece must be a King and secondary piece must be a rook (Castling)");
        }

        // If the rook involved is on the 1 file it is a queenside castle
        if (secondaryInitialSquare.getColumn() == 0) kingSide = false;
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
                " for " + secondaryInitialSquare.getCurrentPiece().getColour() +
                '}';
    }
}
