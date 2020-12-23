package main.java.com.sd.pieces;

import main.java.com.sd.board.Board;
import main.java.com.sd.board.Square;
import main.java.com.sd.moves.Move;
import main.java.com.sd.pieces.colours.Colour;

import java.util.List;

public class Knight extends Piece {

     public Knight(Colour colour, int squareNum) {
         super(colour, squareNum);
         symbol = "N";
         pieceName = "Knight";
     }

    public Knight(Colour colour, int squareNum, int initialSquareNum) {
        super(colour, squareNum, initialSquareNum);
        symbol = "N";
        pieceName = "Knight";
    }

    public Knight makeCopy() {
        return new Knight(this.colour, this.squareNum, this.initialSquareNum);
    }

    @Override
    public List<Move> getLegalMoves(Board board) {
        return this.getKnightMoves(board);
    }

}
