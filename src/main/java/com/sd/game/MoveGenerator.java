package main.java.com.sd.game;

import main.java.com.sd.board.Board;
import main.java.com.sd.moves.Move;
import main.java.com.sd.moves.MoveChain;
import main.java.com.sd.pieces.colours.Colour;
import main.java.com.sd.pieces.colours.ColourHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MoveGenerator {
    private static Logger logger = LogManager.getLogger();

    public static List<MoveChain> makeAllMoves(Colour startColour, GameDirector gameDirector, MoveChain moveChain, int neededDepth) {
        List<MoveChain> returnMoveChains = new ArrayList<>();
        if (neededDepth > 0) {
            List<Move> allMoves = gameDirector.findAllPossibleMoves(startColour, false);
            for (final Move move : allMoves) {
                MoveChain newMoveChain = new MoveChain();
                newMoveChain.setMoveList(moveChain.getMoveList());

                gameDirector.move(move);

                if (move.isCaptureMove()) {
                    //logger.debug("hi");
                }
                newMoveChain.addMoveScore(computeBoardValue(gameDirector.getBoard(), startColour));
                newMoveChain.addMove(move);
                returnMoveChains.add(newMoveChain);
                returnMoveChains.addAll(makeAllMoves(gameDirector.getCurrentPlayerColour(), gameDirector, newMoveChain, neededDepth-1));

                gameDirector.reverseMove(move);
            }
        }

        return returnMoveChains;
    }

    public static List<MoveChain> recursiveMakeAllMoves(Colour startColour, GameDirector gameDirector, int neededDepth) {
        List<MoveChain> moveChainList = new ArrayList<>();

        MoveChain newMoveChain = new MoveChain();
        moveChainList = (makeAllMoves(startColour, gameDirector, newMoveChain, neededDepth));

        return moveChainList.stream().filter(moveChain -> moveChain.getMoveList().size() == neededDepth).collect(Collectors.toList());
    }

    public static int computeBoardValue(Board board, Colour colour) {
        int friendlyScore = board.getActiveColouredPieces(colour).stream().reduce(0, (sum, colouredPiece) -> sum + colouredPiece.getPieceValue(), Integer::sum);
        int enemyScore = board.getActiveColouredPieces(ColourHelper.getOppositeColour(colour)).stream().reduce(0, (sum, colouredPiece) -> sum + colouredPiece.getPieceValue(), Integer::sum);

        return friendlyScore - enemyScore;
    }

    public static int computeMoveValue(Board board, Colour colour) {
        int friendlyScore = board.getActiveColouredPieces(colour).stream().reduce(0, (sum, colouredPiece) -> sum + colouredPiece.getPieceValue(), Integer::sum);
        int enemyScore = board.getActiveColouredPieces(ColourHelper.getOppositeColour(colour)).stream().reduce(0, (sum, colouredPiece) -> sum + colouredPiece.getPieceValue(), Integer::sum);

        return friendlyScore - enemyScore;
    }
}


