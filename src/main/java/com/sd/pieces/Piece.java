package main.java.com.sd.pieces;

import main.java.com.sd.board.Board;
import main.java.com.sd.board.Square;
import main.java.com.sd.pieces.colours.Colour;

import java.util.ArrayList;
import java.util.List;

import java.lang.Math;

public abstract class Piece {
    protected static String pieceName = "";
    protected final Colour colour;
    protected int squareNum;
    protected int squareRow;
    protected int squareCol;

    Piece(Colour colour, int squareNum) {
        this.colour = colour;
        this.squareNum = squareNum;
        this.squareRow = Square.convertSquareNumToRow(squareNum); // TODO fix these hard coding
        this.squareCol = Square.convertSquareNumToCol(squareNum);
        this.pieceName = pieceName;
    }

    private int getDirection() {
        if (colour == Colour.BLACK) {
            return -1;
        }
        else {
            return 1;
        }
    }

    protected List<Square> getLinearMoves(Board board) {
        List<Square> legalSquares = new ArrayList<Square>();

        for (int i = 0; i < board.getBoardArray().length; i++) {
            Square square = board.getBoardArray()[i];
            if ((square.getRow() == this.squareRow) || (square.getColumn() == this.squareCol)) {
                legalSquares.add(square);
            }
        }

        return legalSquares;
    }

    protected List<Square> getPawnMoves(Board board) {
        List<Square> legalSquares = new ArrayList<Square>();


    }

    protected List<Square> getKnightMoves(Board board) {
        List<Square> legalSquares = new ArrayList<Square>();

        final int[] xDirs = {-1, -1, 1, 1};
        final int[] yDirs = {-1, 1, -1, 1};

        final Square currentSquare = board.getSquare(squareRow, squareCol);
        Square targetSquare = currentSquare;

        for (int i = 0; i < xDirs.length; i++) {
            boolean continueSearch = true;

            targetSquare = board.getSquare(currentSquare.getRow() + 2 * xDirs[i], currentSquare.getColumn() + yDirs[i]);
            if (checkMoveValidity(targetSquare)) {
                legalSquares.add(targetSquare);
            }

            targetSquare = board.getSquare(currentSquare.getRow() + xDirs[i], currentSquare.getColumn() + 2 * yDirs[i]);
            if (checkMoveValidity(targetSquare)) {
                legalSquares.add(targetSquare);
            }
        }

        return legalSquares;
    }

    protected List<Square> getRookMoves(Board board) {
        List<Square> legalSquares = new ArrayList<Square>();

        final int[] xDirs = {-1, 0, 1, 0};
        final int[] yDirs = {0, -1, 0, 1};

        for (int i = 0; i < xDirs.length; i++) {
            boolean continueSearch = true;
            Square targetSquare = board.getSquare(squareRow, squareCol);     // Reset target square to current square before each search

            while (continueSearch) {
                targetSquare = board.getSquare(targetSquare.getRow() + xDirs[i], targetSquare.getColumn() + yDirs[i]);

                if (checkMoveValidity(targetSquare)) {
                    legalSquares.add(targetSquare);
                } else {
                    continueSearch = false;
                }
            }
        }
        return legalSquares;
    }

    protected List<Square> getBishopMoves(Board board) {
        List<Square> legalSquares = new ArrayList<Square>();

        final int[] xDirs = {-1, -1, 1, 1};
        final int[] yDirs = {-1, 1, -1, 1};

        for (int i = 0; i < xDirs.length; i++) {
            boolean continueSearch = true;
            Square targetSquare = board.getSquare(squareRow, squareCol);     // Reset target square to current square before each search

            while (continueSearch) {
                targetSquare = board.getSquare(targetSquare.getRow() + xDirs[i], targetSquare.getColumn() + yDirs[i]);

                if (checkMoveValidity(targetSquare)) {
                    legalSquares.add(targetSquare);
                } else {
                    continueSearch = false;
                }
            }
        }
        return legalSquares;
    }


    // TODO or causes check
    // maybe move to its own class
    private boolean checkMoveValidity(Square targetSquare) {
        if (targetSquare == null) {
            return false;
        }

        if (targetSquare.isOccupied()) {
            // todo think about this more
            if (targetSquare.getCurrentPiece().colour == this.colour) {
                return false;
            }
            else {
                return true;
            }
        }

        return true;
    }

    public static void setPieceName(String pieceName) {
        Piece.pieceName = pieceName;
    }

    public abstract List<Square> getLegalMoves(Board board);
}


