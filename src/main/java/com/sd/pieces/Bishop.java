package main.java.com.sd.pieces;

import main.java.com.sd.board.Board;
import main.java.com.sd.moves.Move;
import main.java.com.sd.pieces.colours.Colour;
import main.java.com.sd.utils.ConfigLoader;

import java.util.List;

import static main.java.com.sd.view.GameSprites.BLACK_BISHOP;
import static main.java.com.sd.view.GameSprites.WHITE_BISHOP;

public class Bishop extends Piece {

     public Bishop(Colour colour, int squareNum) {
         super(colour, squareNum);
         symbol = "B";
         pieceName = "Bishop";
         pieceValue = ConfigLoader.loadValueAsInteger("BISHOP_VALUE");


         if (colour == Colour.WHITE) {
             this.pieceSprite = WHITE_BISHOP;
         } else {
             this.pieceSprite = BLACK_BISHOP;
         }
     }

    public Bishop(Colour colour, int squareNum, int initialSquareNum) {
        super(colour, squareNum, initialSquareNum);
        symbol = "B";
        pieceName = "Bishop";
        pieceValue = ConfigLoader.loadValueAsInteger("BISHOP_VALUE");
     }

    public Bishop makeCopy() {
        return new Bishop(this.colour, this.squareNum, this.initialSquareNum);
    }

    @Override
    public List<Move> getLegalMoves(Board board) {
        return this.getBishopMoves(board);
    }

}
