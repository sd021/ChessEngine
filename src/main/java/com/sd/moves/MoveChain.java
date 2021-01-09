package main.java.com.sd.moves;

import java.util.ArrayList;
import java.util.List;

public class MoveChain implements Comparable<MoveChain> {
    List<Move> moveList = new ArrayList<>();
    List<Integer> moveScores = new ArrayList<Integer>();
    int chainScore;

    public MoveChain() {
    };

    public List<Move> getMoveList() {
        return moveList;
    }

    public void setMoveList(List<Move> moveList) {
        for (Move move : moveList) {
            this.moveList.add(move);
        }
    }

    public void addMove(Move move) {
        this.moveList.add(move);
    }

    public List<Integer> getBoardScore() {
        return moveScores;
    }

    public void addMoveScore(int moveScore) {
        this.moveScores.add(moveScore);
    }

    public int getChainScore() {
        return chainScore;
    }

    @Override
    public int compareTo(MoveChain u) {
        return u.getChainScore() - this.chainScore;
    }

    @Override
    public String toString() {
        return "MoveChain{" +
                "moveScores=" + moveScores +
                ", chainScore=" + chainScore +
                ", moveList=" + moveList +
                "}\n";
    }
}
