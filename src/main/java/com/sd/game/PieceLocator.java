package main.java.com.sd.game;

import main.java.com.sd.board.Board;
import main.java.com.sd.pieces.Piece;
import main.java.com.sd.pieces.colours.Colour;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PieceLocator {
    public static List<Piece> findPiece(Board board, Class pieceClass, Colour colour) {
        List<Piece> pieceList = new ArrayList<>();

        for (int i = 0; i < Board.BOARD_WIDTH * Board.BOARD_HEIGHT; i++) {
            Piece piece = board.getSquare(i).getCurrentPiece();
            if (piece != null) {
                if (piece.getClass() == pieceClass && piece.getColour() == colour) {
                    pieceList.add(piece);
                }
            }
        }

        return pieceList;
    }

    public static List<Piece> findPiece(List<Piece> pieceList, Class pieceClass, Colour colour) {
        return pieceList.stream()
                .filter(piece -> piece.getClass() == pieceClass)
                .filter(piece -> piece.getColour() == colour)
                .collect(Collectors.toList());
    }

    public static List<Piece> findPiece(List<Piece> pieceList, Class pieceClass) {
        return pieceList.stream()
                .filter(piece -> piece.getClass() == pieceClass)
                .collect(Collectors.toList());
    }
}
