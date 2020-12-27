package main.java.com.sd.game;

import main.java.com.sd.board.Board;
import main.java.com.sd.moves.CastlingMove;
import main.java.com.sd.moves.Move;
import main.java.com.sd.pieces.*;
import main.java.com.sd.pieces.colours.Colour;
import main.java.com.sd.players.IPlayer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static main.java.com.sd.pieces.colours.Colour.BLACK;
import static main.java.com.sd.pieces.colours.Colour.WHITE;

public class GameDirector {
    private static Logger logger = LogManager.getLogger();

    private List<Move> gameHistory = new ArrayList<Move>();
    private Board board;

    private GamePhase gamePhase;
    private Colour currentPlayerColour = WHITE;
    private IPlayer whitePlayer, blackPlayer;

    public GameDirector(Board board, IPlayer whitePlayer, IPlayer blackPlayer) {
        this.board = board;
        this.whitePlayer = whitePlayer;
        this.blackPlayer = blackPlayer;

        gamePhase = GamePhase.PLAYING;
    }

    public void move(Move move) {
        board.makeMove(move);

        if (move.getClass() == CastlingMove.class) {
            board.makeMove(((CastlingMove) move).getSecondaryMove());
        }
        gameHistory.add(move);

        // Change the player who's go it is
        updateCurrentPlayer();
        logger.info(board);
    }


    private void updateCurrentPlayer() {
        if (currentPlayerColour == WHITE) currentPlayerColour = BLACK;
        else currentPlayerColour = WHITE;
    }

    private IPlayer getCurrentPlayer() {
        return currentPlayerColour == WHITE ? whitePlayer : blackPlayer;
    }



    public List<Move> findAllPossibleMoves(Colour colour) {
        MoveFinder moveFinder = new MoveFinder(board, gameHistory, colour);
        List<Move> movesList = moveFinder.findAllPossibleMoves();

        if (movesList.size() == 0) {
            if (moveFinder.isKingInCheck(board, PieceLocator.findPiece(board.getActiveColouredPieces(colour), King.class).get(0), colour)) {
                gamePhase = GamePhase.CHECKMATE;
                logger.info("GAME OVER: CHECKMATE");
            } else {
                gamePhase = GamePhase.STALEMATE;
                logger.info("GAME OVER: STALEMATE");
            }
        }

        return movesList;
    }

    public void doTurn() {
        List<Move> movesList = findAllPossibleMoves(currentPlayerColour);

        move(getCurrentPlayer().evaluationFunction(movesList));
    }

    public GamePhase getGamePhase() {
        return gamePhase;
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

}
