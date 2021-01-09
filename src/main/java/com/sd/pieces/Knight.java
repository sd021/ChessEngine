package main.java.com.sd.pieces;

import main.java.com.sd.board.Board;
import main.java.com.sd.moves.BasicMove;
import main.java.com.sd.moves.Move;
import main.java.com.sd.pieces.colours.Colour;
import main.java.com.sd.utils.ConfigLoader;

import java.io.IOException;
import java.util.List;

import static main.java.com.sd.view.GameSprites.*;

public class Knight extends Piece {

     public Knight(Colour colour, int squareNum) {
         super(colour, squareNum);
         symbol = "N";
         pieceName = "Knight";
         pieceValue = ConfigLoader.loadValueAsInteger("KNIGHT_VALUE");

         if (colour == Colour.WHITE) {
             this.pieceSprite = WHITE_KNIGHT;
         } else {
             this.pieceSprite = BLACK_KNIGHT;
         }
     }

    public Knight(Colour colour, int squareNum, int initialSquareNum) {
        super(colour, squareNum, initialSquareNum);
        symbol = "N";
        pieceName = "Knight";
        pieceValue = ConfigLoader.loadValueAsInteger("KNIGHT_VALUE");

    }

    public Knight makeCopy() {
        return new Knight(this.colour, this.squareNum, this.initialSquareNum);
    }

    @Override
    public List<Move> getLegalMoves(Board board) {
        return this.getKnightMoves(board);
    }

}
