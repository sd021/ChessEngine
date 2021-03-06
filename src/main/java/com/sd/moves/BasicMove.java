package main.java.com.sd.moves;

import main.java.com.sd.board.Board;
import main.java.com.sd.board.Square;
import main.java.com.sd.pieces.King;
import main.java.com.sd.pieces.Pawn;
import main.java.com.sd.pieces.Piece;
import main.java.com.sd.pieces.Rook;

public class BasicMove extends Move {

    public BasicMove(int initialSquare, int targetSquare, Piece capturedPiece) {
        super(initialSquare, targetSquare, capturedPiece);
    }

    public BasicMove(int initialSquare, int targetSquare) {
        super(initialSquare, targetSquare, null);
    }

}
