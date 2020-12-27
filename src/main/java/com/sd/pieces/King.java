package main.java.com.sd.pieces;

import main.java.com.sd.board.Board;
import main.java.com.sd.board.Square;
import main.java.com.sd.moves.CastlingMove;
import main.java.com.sd.moves.BasicMove;
import main.java.com.sd.moves.Move;
import main.java.com.sd.pieces.colours.Colour;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static main.java.com.sd.view.GameSprites.*;

public class King extends Piece {

    public King(Colour colour, int squareNum) {
        super(colour, squareNum);
        symbol = "K";
        pieceName = "King";

        if (colour == Colour.WHITE) {
            this.pieceSprite = WHITE_KING;
        } else {
            this.pieceSprite = BLACK_KING;
        }
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
    public List<Move> getLegalMoves(Board board) {
        return this.getKingMoves(board);
    }

    public List<Move> getCastlingMoves(Board board, List<Piece> rookList, List<Move> gameHistory, List<Square> checkedSquares) {
        List<Move> castlingMoves = new ArrayList<>();

        if (!board.getSquare(this.squareNum).getSquareName().equals("E1") && !board.getSquare(this.squareNum).getSquareName().equals("E8")) {
            return castlingMoves;
        }

        Piece kingSideRook = null, queenSideRook = null;

        // Decide whether the rooks are king side or queen side
        // TODO rewrite switch statement
        for (Piece rook : rookList) {
            if (this.getColour() == Colour.WHITE) {
                if (board.getSquare(rook.getSquareNum()).getSquareName().equals("A1")) {
                    queenSideRook = rook;
                } else if (board.getSquare(rook.getSquareNum()).getSquareName().equals("H1")) {
                    kingSideRook = rook;
                }
            } else {
                if (board.getSquare(rook.getSquareNum()).getSquareName().equals("A8")) {
                    queenSideRook = rook;
                } else if (board.getSquare(rook.getSquareNum()).getSquareName().equals("H8")) {
                    kingSideRook = rook;
                }
            }
        }

        if (kingSideRook != null) {
            castlingMoves.addAll(getKingSideCastleMove(board, kingSideRook, gameHistory, checkedSquares));
        }

        if (queenSideRook != null) {
            castlingMoves.addAll(getQueenSideCastleMove(board, queenSideRook, gameHistory, checkedSquares));
        }


        return castlingMoves;
    }

    private List<Move> getKingSideCastleMove(Board board, Piece rook, List<Move> gameHistory, List<Square> checkedSquares) {
        List<Move> castleMoves = new ArrayList<>();

        // Squares between king and rook
        List<Square> innerSquares = new ArrayList<>();
        innerSquares.add(board.getSquare(this.getSquareNum() + 1));
        innerSquares.add(board.getSquare(this.getSquareNum() + 2));

        if (!validateCastleMove(board, rook, gameHistory, checkedSquares, innerSquares)) {
            return castleMoves;
        }

        castleMoves.add(new CastlingMove(board.getSquare(this.squareNum), board.getSquare(this.squareNum + 2), board.getSquare(rook.getSquareNum()), board.getSquare(rook.getSquareNum() - 2)));

        return castleMoves;
    }

    private List<Move> getQueenSideCastleMove(Board board, Piece rook, List<Move> gameHistory, List<Square> checkedSquares) {
        List<Move> castleMoves = new ArrayList<>();

        // Squares between king and rook
        List<Square> innerSquares = new ArrayList<>();
        innerSquares.add(board.getSquare(this.getSquareNum() - 1));
        innerSquares.add(board.getSquare(this.getSquareNum() - 2));

        if (!validateCastleMove(board, rook, gameHistory, checkedSquares, innerSquares)) {
            return castleMoves;
        }

        castleMoves.add(new CastlingMove(board.getSquare(this.squareNum), board.getSquare(this.squareNum - 2), board.getSquare(rook.getSquareNum()), board.getSquare(rook.getSquareNum() + 3)));

        return castleMoves;
    }

    private boolean validateCastleMove(Board board, Piece rook, List<Move> gameHistory, List<Square> checkedSquares, List<Square> innerSquares) {
        // Make sure squares between king and rook are empty
        if (innerSquares.stream()
                .filter(square -> square.isOccupied() == true)
                .collect(Collectors.toList())
                .toArray()
                .length != 0) {
            return false;
        }


        // Check king + rook are not in game history (ie. they haven't been moved yet)
        if (gameHistory.stream()
                .filter(move -> move.getInitialSquare().getCurrentPiece().equals(this) || move.getInitialSquare().getCurrentPiece().equals(rook))
                .collect(Collectors.toList())
                .toArray()
                .length > 0) {
            return false;
        }

        // Check squares between the rook and king are not checked
        if (checkedSquares.stream()
                .filter(square -> innerSquares.contains(square) || board.getSquare(this.getSquareNum()).equals(square))
                .collect(Collectors.toList())
                .toArray()
                .length > 0) {
            return false;
        }

        return true;
    }

}
