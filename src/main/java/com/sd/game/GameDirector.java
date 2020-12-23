package main.java.com.sd.game;

import main.java.com.sd.board.Board;
import main.java.com.sd.moves.Move;
import main.java.com.sd.pieces.Piece;
import main.java.com.sd.pieces.colours.Colour;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Collections;
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

    public List<Move> findAllPossibleMoves() {
        List<Move> moveList = new ArrayList<>();
        for (Piece piece : activePieces) {
            moveList.addAll(piece.getLegalMoves(board));
        }

        return moveList;
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
}
