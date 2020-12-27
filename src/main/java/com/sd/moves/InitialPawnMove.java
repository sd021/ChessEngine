package main.java.com.sd.moves;

import main.java.com.sd.board.Square;

public class InitialPawnMove extends Move {
    public InitialPawnMove(Square initialSquare, Square targetSquare) {
        super(initialSquare, targetSquare);
        validateMove();
    }
}
