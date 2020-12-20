package main.java.com.sd.pieces;

import main.java.com.sd.board.Board;
import main.java.com.sd.board.Square;
import main.java.com.sd.board.SquareNames;
import main.java.com.sd.pieces.colours.Colour;

import java.util.List;

public class Bishop extends Piece {
    public static String pieceName = "Bishop";

     public Bishop(Colour colour, int squareNum) {
         super(colour, squareNum);
     }

    @Override
    public List<Square> getLegalMoves(Board board) {
        return this.getBishopMoves(board);
    }

    @Override
    public String toString() {
        return "Piece{" +
                "(" + pieceName + ")" +
                ", square=" + SquareNames.squareName(squareNum) +
                '}';
    }
}
