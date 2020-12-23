package main.java.com.sd;

import main.java.com.sd.board.Board;
import main.java.com.sd.game.GameDirector;
import main.java.com.sd.moves.Move;
import main.java.com.sd.pieces.*;
import main.java.com.sd.pieces.colours.Colour;
import main.java.com.sd.utils.TextBoardGenerator;
import main.java.com.sd.board.boardreps.StandardBoard;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import java.util.List;

public class Main {
    private static Logger logger = LogManager.getLogger();

    public static void main(String[] args) {
        logger.info("Starting Chess Engine!");

        GameDirector gameDirector = new GameDirector(TextBoardGenerator.boardFromText(StandardBoard.board));

        logger.info(gameDirector.findPiece(Pawn.class, Colour.BLACK));

        gameDirector.move(new Move(gameDirector.getBoard().getSquare("D5"),
                gameDirector.getBoard().getSquare("E4")));

        logger.info(gameDirector.findAllPossibleMoves());

        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 50000; i++) {
            List<Move> allMoves = gameDirector.findAllPossibleMoves();
            for (Move move: allMoves) {
                gameDirector.getBoard().makePotentialMove(gameDirector.getBoard(), move);
            }
        }

        long endTime = System.currentTimeMillis();

        logger.info("Duration: " + (endTime - startTime));
       // logger.info(gameDirector.searchMovesByPiece(gameDirector.getBoard().getSquare("E4").getCurrentPiece()));
       // logger.info(gameDirector.findPiece(Pawn.class, Colour.BLACK));
//        logger.info(gameDirector.getBoard().getSquare("E4"));
//        logger.info(gameDirector.getBoard().getSquare("E4").getCurrentPiece().getLegalMoves(gameDirector.getBoard()));
    }
}
