package main.java.com.sd.players;

import main.java.com.sd.game.GameDirector;
import main.java.com.sd.game.MoveGenerator;
import main.java.com.sd.moves.Move;
import main.java.com.sd.moves.MoveChain;
import main.java.com.sd.pieces.colours.Colour;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collections;
import java.util.List;

public class ComputerPlayer implements IPlayer {
    private static Logger logger = LogManager.getLogger();

    public ComputerPlayer() {

    }

    @Override
    public Move evaluationFunction(GameDirector gameDirector) {
        List<MoveChain> moveChains = MoveGenerator.recursiveMakeAllMoves(gameDirector.getCurrentPlayerColour(), gameDirector, 4);
        Collections.sort(moveChains);
        ;
        //logger.info(moveChains);
        MoveChain bestMoveChain = moveChains.get(moveChains.size() - 1);
        Move move = bestMoveChain.getMoveList().get(0);
        //logger.info(moveChains);
        logger.info(bestMoveChain);
        logger.info(move);
        return move;
    }
}
