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

    public MoveFinder(Board board, List<Move> gameHistory, Colour colour) {
        this.board = board;
        this.gameHistory = gameHistory;
        this.colour = colour;
        this.allyPieces = board.getActiveColouredPieces(colour);
        this.enemyPieces = board.getActiveColouredPieces(ColourHelper.getOppositeColour(colour));
    }

    public MoveFinder(Board board, List<Move> gameHistory) {
        this(board, gameHistory, Colour.WHITE);
    }

    public void updateGameEvents(Board board, List<Move> gameHistory, Colour colour) {
        this.board = board;
        this.gameHistory = gameHistory;
        this.colour = colour;
        this.allyPieces = board.getActiveColouredPieces(colour);
        this.enemyPieces = board.getActiveColouredPieces(ColourHelper.getOppositeColour(colour));
    }

    public List<Move> findAllPossibleMoves(Board board, boolean excludeKingCapture) {
        List<Move> moveList = new ArrayList<>();

        moveList.addAll(findAllBasicMoves(board, allyPieces, excludeKingCapture, false));

        List<Piece> kingList = PieceLocator.findPiece(allyPieces, King.class);
        List<Piece> rookList = PieceLocator.findPiece(allyPieces, Rook.class);
        List<Piece> pawnList = PieceLocator.findPiece(allyPieces, Pawn.class);

        List<Square> checkedSquares = getCheckedSquares(board, enemyPieces, true);

        if (kingList.toArray().length == 1) {
            King king = ((King) kingList.get(0));
            moveList.addAll(king.getCastlingMoves(board, rookList, gameHistory, checkedSquares));
        }

        for (Piece pawn : pawnList) {
            moveList.addAll(((Pawn) pawn).getEnPassantMoves(board, gameHistory));
            moveList.addAll(((Pawn) pawn).getInitialMoves(board));
        }

        ListIterator<Move> moveListIterator = moveList.listIterator();


        while (moveListIterator.hasNext()) {
            // TODO
            // actually make move here and then unmake it
            // don't have moves made up of squares
            // just instructions and let them be fully reversible
            // everytime you access a move.getinitialmove.getcurrentpiece has to be redone

            Move testMove = moveListIterator.next();

            board.makeMove(testMove);
            Piece allyKing = PieceLocator.findPiece(board.getActiveColouredPieces(colour), King.class).get(0);
            Piece enemyKing = PieceLocator.findPiece(board.getActiveColouredPieces(ColourHelper.getOppositeColour(colour)), King.class).get(0);
            if (isKingInCheck(board, allyKing, colour)) {
                moveListIterator.remove();
            }
            board.reverseMove(testMove);
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
    public List<Move> findAllBasicMoves(Board board, List<Piece> pieces, boolean excludeKingCapture, boolean includePawnChecks) {
        List<Move> moveList = new ArrayList<>();
        for (Piece piece : pieces) {
            List<Move> legalMoves;

            if (piece.getClass() == Pawn.class) {
                legalMoves = ((Pawn) piece).getLegalMoves(board, includePawnChecks);
            } else {
                legalMoves = piece.getLegalMoves(board);
            }
            if (excludeKingCapture) {
                legalMoves.removeIf(move -> move.getTargetSquare(board).getCurrentPiece() != null && move.getTargetSquare(board).getCurrentPiece().getClass() == King.class);
            }
            moveList.addAll(legalMoves);
        }

        return moveList;
    }


    /*
    Find all squares that the other colour can move to
    If opposite king is in that set then it is in check
    */
    public boolean isKingInCheck(Board potentialBoard, Piece king, Colour colour) {

        //TODO
        // put each kind of piece on this square and test if its there

        if (getCheckedSquares(potentialBoard, potentialBoard.getActiveColouredPieces(ColourHelper.getOppositeColour(colour)), false).contains(potentialBoard.getSquare(king.getSquareNum()))) {
            return true;
        } else {
            return false;
        }
    }


    // Get a list of squares that opposite colour has in check
    public List<Square> getCheckedSquares(Board potentialBoard, List<Piece> enemyPieces, boolean includePawnChecks) {
        List<Move> allBasicMoves = findAllBasicMoves(potentialBoard, enemyPieces, false, includePawnChecks);
        List<Square> checkedSquares = new ArrayList<>();
        LinkedHashSet<Square> hashSet = new LinkedHashSet<Square>();

        for (Move move : allBasicMoves) {
            if (hashSet.add(move.getTargetSquare(board))) checkedSquares.add(move.getTargetSquare(board));
        }

        return checkedSquares;
    }

}
