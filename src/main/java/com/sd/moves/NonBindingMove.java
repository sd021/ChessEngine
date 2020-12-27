package main.java.com.sd.moves;

import main.java.com.sd.board.Square;

/*
Bare minimum move object with no move validation
Not for use as an actual move, use BasicMove instead
TODO rename / redo this
*/
public class NonBindingMove extends Move {
    public NonBindingMove(Square initialSquare, Square targetSquare) {
        super(initialSquare, targetSquare);
    }
}
