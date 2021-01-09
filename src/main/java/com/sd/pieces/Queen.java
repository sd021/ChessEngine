package main.java.com.sd.pieces;

import main.java.com.sd.board.Board;
import main.java.com.sd.moves.BasicMove;
import main.java.com.sd.moves.Move;
import main.java.com.sd.pieces.colours.Colour;
import main.java.com.sd.utils.ConfigLoader;

import java.util.List;

import static main.java.com.sd.view.GameSprites.*;

public class Queen extends Piece {

     public Queen(Colour colour, int squareNum) {
         super(colour, squareNum);
         symbol = "Q";
         pieceName = "Queen";
         pieceValue = ConfigLoader.loadValueAsInteger("QUEEN_VALUE");

         if (colour == Colour.WHITE) {
             this.pieceSprite = WHITE_QUEEN;
         } else {
             this.pieceSprite = BLACK_QUEEN;
         }
     }

    public Queen(Colour colour, int squareNum, int initialSquareNum) {
        super(colour, squareNum, initialSquareNum);
        symbol = "Q";
        pieceName = "Queen";
        pieceValue = ConfigLoader.loadValueAsInteger("QUEEN_VALUE");

    }

    public Queen makeCopy() {
        return new Queen(this.colour, this.squareNum, this.initialSquareNum);
    }

    @Override
    public List<Move> getLegalMoves(Board board) { return this.getQueenMoves(board); }

}
