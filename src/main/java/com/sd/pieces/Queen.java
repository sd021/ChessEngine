package main.java.com.sd.pieces;

import main.java.com.sd.board.Board;
import main.java.com.sd.board.Square;
import main.java.com.sd.moves.Move;
import main.java.com.sd.pieces.colours.Colour;

import java.util.List;

public class Queen extends Piece {

     public Queen(Colour colour, int squareNum) {
         super(colour, squareNum);
         symbol = "Q";
         pieceName = "Queen";
     }

    public Queen(Colour colour, int squareNum, int initialSquareNum) {
        super(colour, squareNum, initialSquareNum);
        symbol = "Q";
        pieceName = "Queen";
    }

    public Queen makeCopy() {
        return new Queen(this.colour, this.squareNum, this.initialSquareNum);
    }

    @Override
    public List<Move> getLegalMoves(Board board) { return this.getQueenMoves(board); }

}
