package main.java.com.sd.game;

import main.java.com.sd.board.Board;
import main.java.com.sd.board.Square;
import main.java.com.sd.moves.Move;
import main.java.com.sd.pieces.King;
import main.java.com.sd.pieces.Pawn;
import main.java.com.sd.pieces.Piece;
import main.java.com.sd.pieces.Rook;
import main.java.com.sd.pieces.colours.Colour;
import main.java.com.sd.pieces.colours.ColourHelper;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.stream.Collectors;

public class MoveFinder {
    private Colour colour;
    private Board board;
    private List<Move> gameHistory;
    private List<Piece> allyPieces, enemyPieces;
//
    public MoveFinder(Board board, List<Move> gameHistory, Colour colour) {
        this.board = board;
        this.gameHistory = gameHistory;
        this.colour = colour;
        this.allyPieces = board.getActiveColouredPieces(colour);
        this.enemyPieces = board.getActiveColouredPieces(ColourHelper.getOppositeColour(colour));
    }

    public  List<Move> findAllPossibleMoves() {
        List<Move> moveList = new ArrayList<>();

        moveList.addAll(findAllBasicMoves(board, allyPieces, true));

        List<Piece> kingList = PieceLocator.findPiece(allyPieces, King.class);
        List<Piece> rookList = PieceLocator.findPiece(allyPieces, Rook.class);
        List<Piece> pawnList = PieceLocator.findPiece(allyPieces, Pawn.class);

        if (kingList.toArray().length == 1) {
            King king = ((King) kingList.get(0));
            moveList.addAll(king.getCastlingMoves(board, rookList, gameHistory, getCheckedSquares(board, enemyPieces)));
        }

        for (Piece pawn : pawnList) {
            moveList.addAll(((Pawn) pawn).getEnPassantMoves(board, gameHistory));
            moveList.addAll(((Pawn) pawn).getInitialMoves(board));
        }

        ListIterator<Move> moveListIterator = moveList.listIterator();


        while(moveListIterator.hasNext()) {
            Board newBoard = board.makePotentialMove(board, moveListIterator.next());
            Piece allyKing = PieceLocator.findPiece(newBoard.getActiveColouredPieces(colour), King.class).get(0);
            Piece enemyKing = PieceLocator.findPiece(newBoard.getActiveColouredPieces(ColourHelper.getOppositeColour(colour)), King.class).get(0);
            if (isKingInCheck(newBoard, allyKing, colour)) {
                moveListIterator.remove();
            }
        }

        return moveList;
    }

    /*
Returns a list of the normal moves each piece has
Doesn't include castling, en passant, pawn promotion
Used for finding squares that are under attack and building full list of possible moves
excludeKingCapture should be true unless you are checking if a king is in check
 */
    // TODO make private?
    public  List<Move> findAllBasicMoves(Board board, List<Piece> pieces, boolean excludeKingCapture) {
        List<Move> moveList = new ArrayList<>();
        for (Piece piece : pieces) {
            List<Move> legalMoves = piece.getLegalMoves(board);
            if (excludeKingCapture) {
                legalMoves.removeIf(move -> move.getTargetSquare().getCurrentPiece() != null && move.getTargetSquare().getCurrentPiece().getClass() == King.class);
            }
            moveList.addAll(legalMoves);
        }

        return moveList;
    }



    /*
    Find all squares that the other colour can move to
    If opposite king is in that set then it is in check
    */
    public  boolean isKingInCheck(Board potentialBoard, Piece king, Colour colour) {

        // TODO use this for attacking squares on king check
//        Board boardWithoutKing = new Board(board);
//        Square newKingSquare = boardWithoutKing.getSquare(findPiece(King.class, colour).get(0).getSquareNum()).makeCopy();
//        newKingSquare.updateSquare();
//        boardWithoutKing.setSquare(newKingSquare);
            return isSquareChecked(potentialBoard, potentialBoard.getSquare(king.getSquareNum()), potentialBoard.getActiveColouredPieces(ColourHelper.getOppositeColour(colour)));
    }

    // Check if opposite colour has a square checked on you
    public  boolean isSquareChecked(Board potentialBoard, Square square, List<Piece> enemyPieces) {
        int numCheckedSquares = getCheckedSquares(potentialBoard, enemyPieces).stream()
                .filter(sq -> sq.equals(square))
                .collect(Collectors.toList()).toArray().length;

        return numCheckedSquares == 1;
    }


    // Get a list of squares that opposite colour has in check
    public  List<Square> getCheckedSquares(Board potentialBoard, List<Piece> enemyPieces) {
        List<Move> allBasicMoves = findAllBasicMoves(potentialBoard, enemyPieces, false);
        List<Square> checkedSquares = new ArrayList<>();
        LinkedHashSet<Square> hashSet = new LinkedHashSet<Square>();

        for (Move move : allBasicMoves) {
            if (hashSet.add(move.getTargetSquare())) checkedSquares.add(move.getTargetSquare());
        }

        // Pawn attacking checks
        List<Piece> pawnPieces = enemyPieces.stream()
                .filter(piece -> piece.getClass() == Pawn.class)
                .collect(Collectors.toList());

        for (Piece pawn : pawnPieces) {
            for (Move move : ((Pawn) pawn).getAttackingSquares(potentialBoard)) {
                if (hashSet.add(move.getTargetSquare())) checkedSquares.add(move.getTargetSquare());
            }
        }

        return checkedSquares;
    }

}
