package main.java.com.sd.moves;

import main.java.com.sd.board.Square;
import main.java.com.sd.pieces.*;
import main.java.com.sd.pieces.colours.Colour;

import java.util.ArrayList;
import java.util.List;

public class PawnPromotionMove extends Move {
    Piece pawnPiece;
    Piece desiredPiece;

    public PawnPromotionMove(int initialSquare, int targetSquare, Piece pawnPiece, Piece desiredPiece, Piece capturedPiece) {
        super(initialSquare, targetSquare, capturedPiece);
        this.pawnPiece = pawnPiece;
        this.desiredPiece = desiredPiece;
    }

    public static List<PawnPromotionMove> generatePromotionMoves(Colour colour, int initialSquare, int targetSquare, Piece pawnPiece, Piece capturedPiece) {
        List<PawnPromotionMove> moveList = new ArrayList<>();

        List<Piece> validPieces = new ArrayList<>();
        validPieces.add(new Bishop(colour, targetSquare));
        validPieces.add(new Rook(colour, targetSquare));
        validPieces.add(new Queen(colour, targetSquare));
        validPieces.add(new Knight(colour, targetSquare));
        validPieces.add(new Bishop(colour, targetSquare));

        for (Piece piece : validPieces) {
            moveList.add(new PawnPromotionMove(initialSquare, targetSquare, pawnPiece, piece, capturedPiece));
        }

        return moveList;
    }

    public Piece getDesiredPiece() {
        return desiredPiece;
    }

    public Piece getPawnPiece() {
        return pawnPiece;
    }
}
