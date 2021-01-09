package main.java.com.sd.players;

import main.java.com.sd.game.GameDirector;
import main.java.com.sd.moves.Move;

import java.util.List;

public interface IPlayer {
    Move evaluationFunction(GameDirector gameDirector);
}
