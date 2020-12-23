package main.java.com.sd.pieces;

import main.java.com.sd.board.Board;
import main.java.com.sd.board.Square;
import main.java.com.sd.moves.Move;
import main.java.com.sd.pieces.colours.Colour;

import java.util.List;

public class Pawn extends Piece{

    public Pawn(Colour colour, int squareNum) {
        super(colour, squareNum);
        symbol = "P";
        pieceName = "Pawn";
    }

    public Pawn(Colour colour, int squareNum, int initialSquareNum) {
        super(colour, squareNum, initialSquareNum);
        symbol = "P";
        pieceName = "Pawn";
    }

    public Pawn makeCopy() {
        return new Pawn(this.colour, this.squareNum, this.initialSquareNum);
    }

    @Override
    public List<Move> getLegalMoves(Board board) {
        return this.getPawnMoves(board);
    }

}
