package main.java.com.sd.pieces;

import main.java.com.sd.board.Board;
import main.java.com.sd.board.Square;
import main.java.com.sd.moves.BasicMove;
import main.java.com.sd.moves.EnPassantMove;
import main.java.com.sd.moves.InitialPawnMove;
import main.java.com.sd.moves.Move;
import main.java.com.sd.pieces.colours.Colour;
import main.java.com.sd.utils.ConfigLoader;

import java.util.ArrayList;
import java.util.List;

import static main.java.com.sd.view.GameSprites.*;

public class Pawn extends Piece {

    public Pawn(Colour colour, int squareNum) {
        super(colour, squareNum);
        symbol = "P";
        pieceName = "Pawn";
        pieceValue = ConfigLoader.loadValueAsInteger("PAWN_VALUE");


        if (colour == Colour.WHITE) {
            this.pieceSprite = WHITE_PAWN;
        } else {
            this.pieceSprite = BLACK_PAWN;
        }
    }

    public Pawn(Colour colour, int squareNum, int initialSquareNum) {
        super(colour, squareNum, initialSquareNum);
        symbol = "P";
        pieceName = "Pawn";
        pieceValue = ConfigLoader.loadValueAsInteger("PAWN_VALUE");


    }


    public Pawn makeCopy() {
        return new Pawn(this.colour, this.squareNum, this.initialSquareNum);
    }

    @Override
    public List<Move> getLegalMoves(Board board) {
        return this.getPawnMoves(board, false);
    }

    public List<Move> getLegalMoves(Board board, boolean getAttackedSquares) {
        return this.getPawnMoves(board, getAttackedSquares);
    }

    public List<Move> getInitialMoves(Board board) {
        List<Move> initialMoves = new ArrayList<>();
        if ((this.getSquareNum() == this.getInitialSquareNum()) && ((getDirection() == 1 && squareRow == 1) || (getDirection() == -1 && squareRow == 6))) {
            if (!board.getSquare(squareRow + getDirection(), squareCol).isOccupied()
                    && !board.getSquare(squareRow + getDirection() * 2, squareCol).isOccupied()) {
                initialMoves.add(new InitialPawnMove(squareNum, Square.convertRowColToSquareNum(squareRow + (getDirection() * 2), squareCol)));
            }
        }

        return initialMoves;
    }

    public boolean fastEnPassantValidation(Board board, List<Move> gameHistory) {
        if (gameHistory.size() > 0) {
            Move lastMove = gameHistory.get(gameHistory.size() - 1);

            if (lastMove.getClass() == InitialPawnMove.class && (this.squareRow == 3 || this.squareRow == 4)) {
                return true;
            }
        }

        return false;
    }

    public List<Move> getEnPassantMoves(Board board, List<Move> gameHistory) {
        List<Move> enPassantMoves = new ArrayList<>();

        if (!fastEnPassantValidation(board, gameHistory)) {
            return enPassantMoves;
        }

        List<Move> attackingSquares = getLegalMoves(board, true);

        // If in the last move a pawn in our lateral square moved there from the square two above/below
        List<Square> lateralSquares = new ArrayList<>();

        //TODO you can't just take away sqaure number for row
        if (squareCol > 0) lateralSquares.add(board.getSquare(this.squareRow, Math.max(this.squareCol - 1, 0)));
        if (squareCol < Board.BOARD_WIDTH)
            lateralSquares.add(board.getSquare(this.squareRow, Math.min(this.squareCol + 1, Board.BOARD_WIDTH - 1)));

        if (gameHistory.size() > 0) {
            Move lastMove = gameHistory.get(gameHistory.size() - 1);

            // If the square next to the pawn was an InitialPawnMove
            for (Square square : lateralSquares) {
                if (lastMove.getTargetSquare(board).equals(square) &&
                        lastMove.getClass() == InitialPawnMove.class) {
                    enPassantMoves.add(new EnPassantMove(squareNum,
                            Square.convertRowColToSquareNum(square.getRow() + getDirection(),
                                    square.getColumn()), square.getSquareNum(), square.getCurrentPiece()));
                }
            }
        }

        return enPassantMoves;
    }

}
