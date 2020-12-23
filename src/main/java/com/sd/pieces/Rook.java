package main.java.com.sd.pieces;

import main.java.com.sd.board.Board;
import main.java.com.sd.board.Square;
import main.java.com.sd.moves.Move;
import main.java.com.sd.pieces.colours.Colour;

import java.util.List;

public class Rook extends Piece {

     public Rook(Colour colour, int squareNum) {
         super(colour, squareNum);
         symbol = "R";
         pieceName = "Rook";
     }

    public Rook(Colour colour, int squareNum, int initialSquareNum) {
        super(colour, squareNum, initialSquareNum);
        symbol = "R";
        pieceName = "Rook";
    }

    public Rook makeCopy() {
        return new Rook(this.colour, this.squareNum, this.initialSquareNum);
    }

    @Override
    public List<Move> getLegalMoves(Board board) {
        return this.getRookMoves(board);
    }

}
