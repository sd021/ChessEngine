package com.sd;

import main.java.com.sd.board.Square;
import main.java.com.sd.events.EventHandler;
import main.java.com.sd.game.GameDirector;
import main.java.com.sd.game.GamePhase;
import main.java.com.sd.game.MoveGenerator;
import main.java.com.sd.moves.*;
import main.java.com.sd.pieces.*;
import main.java.com.sd.pieces.colours.Colour;
import main.java.com.sd.players.ComputerPlayer;
import main.java.com.sd.utils.TextBoardGenerator;
import main.java.com.sd.board.boardreps.StandardBoard;

import javax.swing.*;

import main.java.com.sd.view.GameView;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import java.util.Collections;
import java.util.List;

public class Main {
    private static Logger logger = LogManager.getLogger();

    public static void main(String[] args) throws InterruptedException {
        logger.info("Starting Chess Engine!");

        GameDirector gameDirector = new GameDirector(TextBoardGenerator.boardFromText(StandardBoard.standardBoard),
                new ComputerPlayer(), new ComputerPlayer());

        boolean drawGraphics = true;
        boolean doPerfTest = false;
        boolean doSingleTurn = false;
        boolean doRecurrsivePerf = false;


        if (doSingleTurn) {
            gameDirector.doTurn();
        }

        if (drawGraphics) {
            GameView gameView = new GameView(gameDirector.getBoard());

            while (gameDirector.getGamePhase() == GamePhase.PLAYING) {
                gameDirector.doTurn();
                gameView.updateBoard(gameDirector.getBoard());
                gameView.repaint();
                Thread.sleep(500);
            }
        }

        if (doPerfTest) {
            long startTime = System.currentTimeMillis();
            for (int i = 0; i < 100000; i++) {
                List<Move> allMoves = gameDirector.findAllPossibleMoves(Colour.WHITE, true);
                for (Move move : allMoves) {
                    gameDirector.move(move);
                    gameDirector.reverseMove(move);
                    //gameDirector.getBoard().makePotentialMove(gameDirector.getBoard(), move);
                }
            }
            long endTime = System.currentTimeMillis();
            logger.info("Duration: " + (endTime - startTime));
        }

        if (doRecurrsivePerf) {
            for (int i = 1; i < 6; i++) {
                long startTime = System.currentTimeMillis();
                List<MoveChain> moveChains = MoveGenerator.recursiveMakeAllMoves(gameDirector.getCurrentPlayerColour(), gameDirector.makeCopy(), i);

                long endTime = System.currentTimeMillis();
                long duration = endTime - startTime;

                logger.info("Nummber of moves at depth " + i + ": " + moveChains.size() + "   (took " + duration +"ms)");
            }

        }

    }
}
