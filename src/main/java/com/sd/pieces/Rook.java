package main.java.com.sd.pieces;

import main.java.com.sd.board.Board;
import main.java.com.sd.moves.BasicMove;
import main.java.com.sd.moves.Move;
import main.java.com.sd.pieces.colours.Colour;
import main.java.com.sd.utils.ConfigLoader;

import java.util.List;

import static main.java.com.sd.view.GameSprites.*;

public class Rook extends Piece {

     public Rook(Colour colour, int squareNum) {
         super(colour, squareNum);
         symbol = "R";
         pieceName = "Rook";
         pieceValue = ConfigLoader.loadValueAsInteger("ROOK_VALUE");

         if (colour == Colour.WHITE) {
             this.pieceSprite = WHITE_ROOK;
         } else {
             this.pieceSprite = BLACK_ROOK;
         }
     }

    public Rook(Colour colour, int squareNum, int initialSquareNum) {
        super(colour, squareNum, initialSquareNum);
        symbol = "R";
        pieceName = "Rook";
        pieceValue = ConfigLoader.loadValueAsInteger("ROOK_VALUE");

    }

    public Rook makeCopy() {
        return new Rook(this.colour, this.squareNum, this.initialSquareNum);
    }

    @Override
    public List<Move> getLegalMoves(Board board) {
        return this.getRookMoves(board);
    }

}
