package main.java.com.sd;

import main.java.com.sd.events.EventHandler;
import main.java.com.sd.game.GameDirector;
import main.java.com.sd.game.GamePhase;
import main.java.com.sd.moves.BasicMove;
import main.java.com.sd.moves.InitialPawnMove;
import main.java.com.sd.moves.Move;
import main.java.com.sd.pieces.*;
import main.java.com.sd.pieces.colours.Colour;
import main.java.com.sd.players.ComputerPlayer;
import main.java.com.sd.utils.TextBoardGenerator;
import main.java.com.sd.board.boardreps.StandardBoard;
import javax.swing.*;

import main.java.com.sd.view.GameView;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import java.util.List;

public class Main {
    private static Logger logger = LogManager.getLogger();

    public static void main(String[] args) throws InterruptedException {

        logger.info("Starting Chess Engine!");

        GameDirector gameDirector = new GameDirector(TextBoardGenerator.boardFromText(StandardBoard.standardBoard),
                new ComputerPlayer(), new ComputerPlayer());

        GameView gameView = new GameView(gameDirector.getBoard());

        //EventHandler eventHandler = new EventHandler(gameView);

        // Creates a new thread so our GUI can process itself
//        Thread t = new Thread(eventHandler);
//        t.start();



        while (gameDirector.getGamePhase() == GamePhase.PLAYING) {
            gameDirector.doTurn();
            gameView.updateBoard(gameDirector.getBoard());
            gameView.repaint();
            Thread.sleep(1000);
        }

//        long startTime = System.currentTimeMillis();
//        for (int i = 0; i < 100000; i++) {
//            List<Move> allMoves = gameDirector.findAllPossibleMoves(Colour.WHITE);
//            for (Move move: allMoves) {
//                gameDirector.getBoard().makePotentialMove(gameDirector.getBoard(), move);
//            }
//        }
//        long endTime = System.currentTimeMillis();
//        logger.info("Duration: " + (endTime - startTime));

        // logger.info(gameDirector.searchMovesByPiece(gameDirector.getBoard().getSquare("E4").getCurrentPiece()));
        // logger.info(gameDirector.findPiece(Pawn.class, Colour.BLACK));
//        logger.info(gameDirector.getBoard().getSquare("E4"));
//        logger.info(gameDirector.getBoard().getSquare("E4").getCurrentPiece().getLegalMoves(gameDirector.getBoard()));
    }
}
