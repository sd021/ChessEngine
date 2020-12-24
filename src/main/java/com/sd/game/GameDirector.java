package main.java.com.sd.game;

import main.java.com.sd.board.Board;
import main.java.com.sd.moves.Move;
import main.java.com.sd.pieces.King;
import main.java.com.sd.pieces.Piece;
import main.java.com.sd.pieces.colours.Colour;
import main.java.com.sd.pieces.colours.ColourHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GameDirector {
    private static Logger logger = LogManager.getLogger();

    private List<Move> gameHistory = new ArrayList<Move>();
    private List<Piece> allPieces = new ArrayList<Piece>();
    private List<Piece> takenPieces = new ArrayList<Piece>();
    private List<Piece> activePieces = new ArrayList<Piece>();

    private Board board;

    public GameDirector(Board board) {
        // Populate piecesList with every piece on the board
        // TODO update on pawn promotion
        for (int i = 0; i < Board.BOARD_WIDTH * Board.BOARD_HEIGHT; i++) {
            if (board.getSquare(i).getCurrentPiece() != null) {
                allPieces.add(board.getSquare(i).getCurrentPiece());
                activePieces.add(board.getSquare(i).getCurrentPiece());
            }
        }

        this.board = board;
    }

    public void move(Move move) {
        board.makeMove(move);
        gameHistory.add(move);

        if (move.isCaptureMove()) {
            takenPieces.add(move.getCapturedPiece());
            activePieces.remove(move.getCapturedPiece());
            move.getCapturedPiece().capturePiece();    // TODO taken piece record
        }

        logger.info(board);
    }

    public boolean isKingInCheck(Colour colour) {
        // find all squares that the other colour can move to
        // if opposite king is in that set then it is in check

        List<Move> allBasicMoves = findAllBasicMoves(ColourHelper.getOppositeColour(colour), false);
        List<Piece> kingPieces = findPiece(King.class, colour);

        if (kingPieces.toArray().length != 1) {
            logger.error("Found more than one king (" + kingPieces + ")");
            throw new RuntimeException("Found more than one king while checking check!");
        } else {
            Piece kingPiece = kingPieces.get(0);
            for (Move move : allBasicMoves) {
                // TODO why doesn't this work
                if (kingPiece == move.getTargetSquare().getCurrentPiece()) {
                    return true;
                }
            }
        }
        return false;
    }

    public List<Move> findAllBasicMoves(Colour colour, boolean excludeKingCapture) {
        List<Move> moveList = new ArrayList<>();

        List<Piece> colouredPieces = activePieces.stream()
                .filter(piece -> piece.getColour() == colour)
                .collect(Collectors.toList());

        return findAllBasicMoves(colouredPieces, excludeKingCapture);
    }

    public List<Move> findAllBasicMoves(List<Piece> pieces, boolean excludeKingCapture) {
        List<Move> moveList = new ArrayList<>();
        for (Piece piece : pieces) {
            List<Move> legalMoves = piece.getLegalMoves(board);
            if (excludeKingCapture) {
                legalMoves = legalMoves.stream()
                        .filter(move -> move.getTargetSquare().getCurrentPiece() != null)
                        .filter(move -> move.getTargetSquare().getCurrentPiece().getClass() != King.class)
                        .collect(Collectors.toList());
            }
            moveList.addAll(legalMoves);
        }

        return moveList;
    }

    public List<Move> findAllBasicMoves() {
        return findAllBasicMoves(activePieces, true);
    }

    public Board getBoard() {
        return board;
    }

    public List<Move> getGameHistory() {
        return gameHistory;
    }

    public List<Move> searchMovesByPiece(Piece piece) {
        return gameHistory.stream()
                .filter(move -> move.getInitialSquare().getCurrentPiece() == piece)
                .collect(Collectors.toList());
    }

    public List<Piece> findPiece(Class pieceClass, Colour pieceColour) {
        return allPieces.stream()
                .filter(piece -> piece.getClass() == pieceClass && piece.getColour() == pieceColour)
                .collect(Collectors.toList());
    }

    public List<Piece> findPiece(Class pieceClass) {
        return allPieces.stream()
                .filter(piece -> piece.getClass() == pieceClass)
                .collect(Collectors.toList());
    }
}
