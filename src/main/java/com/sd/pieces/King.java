package main.java.com.sd.pieces;

import main.java.com.sd.board.Board;
import main.java.com.sd.board.Square;
import main.java.com.sd.moves.Move;
import main.java.com.sd.pieces.colours.Colour;

import java.util.List;

public class King extends Piece {

     public King(Colour colour, int squareNum) {
         super(colour, squareNum);
         symbol = "K";
         pieceName = "King";
     }

    public King(Colour colour, int squareNum, int initialSquareNum) {
        super(colour, squareNum, initialSquareNum);
        symbol = "K";
        pieceName = "King";
    }

    public King makeCopy() {
        return new King(this.colour, this.squareNum, this.initialSquareNum);
    }

    @Override
    public List<Move> getLegalMoves(Board board) { return this.getKingMoves(board); }

}
