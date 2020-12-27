package main.java.com.sd.players;

import main.java.com.sd.moves.Move;

import java.util.List;

public class ComputerPlayer implements IPlayer {
    public ComputerPlayer() {

    }

    @Override
    public Move evaluationFunction(List<Move> moveList) {
        return moveList.get((int) (Math.random() * moveList.size()));
    }
}
