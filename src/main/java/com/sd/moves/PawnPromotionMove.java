package main.java.com.sd.moves;

import main.java.com.sd.board.Square;
import main.java.com.sd.pieces.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PawnPromotionMove extends Move {
    public PawnPromotionMove(Square initialSquare, Square targetSquare) {
        super(initialSquare, targetSquare);
    }

    public static List<PawnPromotionMove> generatePromotionMoves(Square initialSquare, Square targetSquare) {
        List<PawnPromotionMove> moveList = new ArrayList<>();

        List<Piece> validPieces = new ArrayList<>();
        validPieces.add(new Bishop(initialSquare.getCurrentPiece().getColour(), targetSquare.getSquareNum()));
        validPieces.add(new Rook(initialSquare.getCurrentPiece().getColour(), targetSquare.getSquareNum()));
        validPieces.add(new Queen(initialSquare.getCurrentPiece().getColour(), targetSquare.getSquareNum()));
        validPieces.add(new Knight(initialSquare.getCurrentPiece().getColour(), targetSquare.getSquareNum()));
        validPieces.add(new Bishop(initialSquare.getCurrentPiece().getColour(), targetSquare.getSquareNum()));

        for (Piece piece : validPieces) {
            moveList.add(new PawnPromotionMove(new Square(initialSquare.getRow(), initialSquare.getColumn(), piece), targetSquare));
        }

        return moveList;
    }
}
