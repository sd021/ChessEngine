package main.java.com.sd.game;

import main.java.com.sd.board.Board;
import main.java.com.sd.moves.CastlingMove;
import main.java.com.sd.moves.Move;
import main.java.com.sd.moves.MoveChain;
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
    private MoveFinder moveFinder;

    private GamePhase gamePhase;
    private Colour currentPlayerColour = WHITE;
    private IPlayer whitePlayer, blackPlayer;

    public GameDirector(Board board, IPlayer whitePlayer, IPlayer blackPlayer) {
        this.board = board;
        this.whitePlayer = whitePlayer;
        this.blackPlayer = blackPlayer;

        gamePhase = GamePhase.PLAYING;


        moveFinder = new MoveFinder(board, gameHistory, currentPlayerColour);
    }

    public GameDirector(Board board, List<Move> gameHistory) {
        this.board = board;
        gamePhase = GamePhase.PLAYING;

        moveFinder = new MoveFinder(board, gameHistory, currentPlayerColour);
    }


    public GameDirector makeCopy() {
        Board boardCopy = new Board(this.board);
        GameDirector gd = new GameDirector(boardCopy, gameHistory);
        gd.setGamePhase(this.gamePhase);
        gd.setCurrentPlayerColour(this.currentPlayerColour);
        gd.moveFinder.updateGameEvents(boardCopy, this.gameHistory, this.currentPlayerColour);

        return gd;
    }

    public void move(Move move) {
        board.makeMove(move);

        // Castling requires two piece to be moved
        if (move.getClass() == CastlingMove.class) {
            board.makeMove(((CastlingMove) move).getSecondaryMove());
        }

        // Add move to gameHistory
        gameHistory.add(move);

        // Change the player who's go it is
        updateCurrentPlayer();

        // Update the moveFinder object
        moveFinder.updateGameEvents(board, gameHistory, currentPlayerColour);
    }

    public void reverseMove(MoveChain moveChain) {
        for (int i = moveChain.getMoveList().size() - 1; i >= 0; i--) {
            reverseMove(moveChain.getMoveList().get(i));
        }
    }

    public void reverseMove(Move move) {
        if (gameHistory.get(gameHistory.size() - 1) != move) {
            throw new RuntimeException("Trying to reverse move when moves have been made since");
        }

        board.reverseMove(move);

        // Castling requires two piece to be moved
        if (move.getClass() == CastlingMove.class) {
            board.reverseMove(((CastlingMove) move).getSecondaryMove());
        }

        // Add move to gameHistory
        gameHistory.remove(move);

        // Change the player who's go it is
        updateCurrentPlayer();

        // Update the moveFinder object
        moveFinder.updateGameEvents(board, gameHistory, currentPlayerColour);
        //logger.info(gameHistory);
        //logger.info(board);
    }


    private void updateCurrentPlayer() {
        if (currentPlayerColour == WHITE) currentPlayerColour = BLACK;
        else currentPlayerColour = WHITE;
    }

    public void setCurrentPlayerColour(Colour currentPlayerColour) {
        this.currentPlayerColour = currentPlayerColour;
    }

    private IPlayer getCurrentPlayer() {
        return currentPlayerColour == WHITE ? whitePlayer : blackPlayer;
    }

    public Colour getCurrentPlayerColour() {
        return currentPlayerColour;
    }

    private void setGamePhase(GamePhase gamePhase) {
        this.gamePhase = gamePhase;
    }

    public List<Move> findAllPossibleMoves(Colour colour, boolean excludeKingCapture) {
        List<Move> movesList = moveFinder.findAllPossibleMoves(board, excludeKingCapture);

        if (movesList.size() == 0) {
            if (moveFinder.isKingInCheck(board, PieceLocator.findPiece(board.getActiveColouredPieces(colour), King.class).get(0), colour)) {
                setGamePhase(GamePhase.CHECKMATE);
                logger.info("GAME OVER: CHECKMATE");
            } else {
                setGamePhase(GamePhase.STALEMATE);
                logger.info("GAME OVER: STALEMATE");
            }
        }

        return movesList;
    }

    public void doTurn() {
        //List<Move> movesList = findAllPossibleMoves(currentPlayerColour);

        move(getCurrentPlayer().evaluationFunction(this.makeCopy()));
        logger.debug(board);
    }

    public GamePhase getGamePhase() {
        return this.gamePhase;
    }

    public Board getBoard() {
        return board;
    }

    public List<Move> getGameHistory() {
        return gameHistory;
    }

}
