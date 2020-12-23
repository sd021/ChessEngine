package main.java.com.sd.pieces;

import main.java.com.sd.board.Board;
import main.java.com.sd.board.Square;
import main.java.com.sd.moves.Move;
import main.java.com.sd.pieces.colours.Colour;

import java.util.List;

public class Bishop extends Piece {

     public Bishop(Colour colour, int squareNum) {
         super(colour, squareNum);
         symbol = "B";
         pieceName = "Bishop";
     }

    public Bishop(Colour colour, int squareNum, int initialSquareNum) {
        super(colour, squareNum, initialSquareNum);
        symbol = "B";
        pieceName = "Bishop";
    }

    public Bishop makeCopy() {
        return new Bishop(this.colour, this.squareNum, this.initialSquareNum);
    }

    @Override
    public List<Move> getLegalMoves(Board board) {
        return this.getBishopMoves(board);
    }

}
