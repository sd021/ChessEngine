package main.java.com.sd.moves;

import main.java.com.sd.board.Square;
import main.java.com.sd.pieces.Piece;

/*
Bare minimum move object with no move validation
Not for use as an actual move, use BasicMove instead
TODO rename / redo this
*/
public class NonBindingMove extends Move {
    public NonBindingMove(int initialSquare, int targetSquare, Piece capturedPiece) {
        super(initialSquare, targetSquare, capturedPiece);
    }

    public NonBindingMove(int initialSquare, int targetSquare) {
        this(initialSquare, targetSquare, null);
    }

}


