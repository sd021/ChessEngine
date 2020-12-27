package main.java.com.sd.pieces;

import main.java.com.sd.board.Board;
import main.java.com.sd.board.Square;
import main.java.com.sd.moves.BasicMove;
import main.java.com.sd.moves.Move;
import main.java.com.sd.moves.NonBindingMove;
import main.java.com.sd.moves.PawnPromotionMove;
import main.java.com.sd.pieces.colours.Colour;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class Piece {
    public BufferedImage pieceSprite;
    protected String pieceName;
    protected String symbol = "";
    protected final Colour colour;
    protected boolean hasBeenCaptured = false;
    protected final int initialSquareNum; // Used to identify pieces later in the game
    protected int squareNum;
    protected int squareRow;
    protected int squareCol;

    Piece(Colour colour, int squareNum) {
        this(colour, squareNum, squareNum);
    }

    Piece(Colour colour, int squareNum, int initialSquareNum) {
        this.colour = colour;
        this.initialSquareNum = initialSquareNum;
        updatePieceSquare(squareNum);
    }

    public abstract Piece makeCopy();

    public void updatePieceSquare(int squareNum) {
        this.squareNum = squareNum;
        this.squareRow = Square.convertSquareNumToRow(squareNum);
        this.squareCol = Square.convertSquareNumToCol(squareNum);
    }

    public void setHasBeenCaptured(boolean hasBeenCaptured) {
        this.hasBeenCaptured = hasBeenCaptured;
    }

    public boolean isHasBeenCaptured() {
        return hasBeenCaptured;
    }

    protected int getDirection() {
        if (colour == Colour.BLACK) {
            return -1;
        } else {
            return +1;
        }
    }

    protected List<Move> getPawnMoves(Board board, boolean getAttackedSquares) {
        List<Move> legalMoves = new ArrayList<>();

        final Square currentSquare = board.getSquare(squareRow, squareCol);
        Square targetMoveSquare = board.getSquare(currentSquare.getRow() + getDirection(), currentSquare.getColumn());

        if (!getAttackedSquares) {
            if (!targetMoveSquare.isOccupied()) {

                // Prevent adding a move to last row as pawn must be promoted in that case
                if ((getDirection() == 1 && squareRow != 6) || (getDirection() == -1 && squareRow != 1)) {
                    if (board.checkValidPosition(new BasicMove(currentSquare, targetMoveSquare))) {
                        legalMoves.add(new BasicMove(currentSquare, targetMoveSquare));
                    }
                } else {
                    legalMoves.addAll(PawnPromotionMove.generatePromotionMoves(currentSquare, targetMoveSquare));
                }
            }
        }

        Square[] targetTakeSquares = new Square[2];
        targetTakeSquares[0] = board.getSquare(currentSquare.getRow() + getDirection(), currentSquare.getColumn() - 1);
        targetTakeSquares[1] = board.getSquare(currentSquare.getRow() + getDirection(), currentSquare.getColumn() + 1);


        for (Square targetSquare : targetTakeSquares) {
            if (targetSquare != null) {
                if (getAttackedSquares) {
                    legalMoves.add(new NonBindingMove(currentSquare, targetSquare));
                } else if (targetSquare.isOccupied()) {
                    // Prevent adding a move to last row as pawn must be promoted in that case
                    if ((getDirection() == 1 && squareRow != 6) || (getDirection() == -1 && squareRow != 1)) {
                        if (targetSquare.getCurrentPiece().getColour() != this.getColour()) {
                            legalMoves.add(new BasicMove(currentSquare, targetSquare));
                        }
                    } else {
                        legalMoves.addAll(PawnPromotionMove.generatePromotionMoves(currentSquare, targetSquare));
                    }
                }
            }
        }

        return legalMoves;
    }

    protected List<Move> getKnightMoves(Board board) {
        List<Move> legalMoves = new ArrayList<Move>();

        final int[] xDirs = {-1, -1, 1, 1};
        final int[] yDirs = {-1, 1, -1, 1};

        boolean shouldAddSquare = false;

        final Square currentSquare = board.getSquare(squareRow, squareCol);
        Square[] targetSquares = new Square[2];

        for (int i = 0; i < xDirs.length; i++) {
            targetSquares[0] = board.getSquare(currentSquare.getRow() + 2 * xDirs[i], currentSquare.getColumn() + yDirs[i]);
            targetSquares[1] = board.getSquare(currentSquare.getRow() + xDirs[i], currentSquare.getColumn() + 2 * yDirs[i]);

            for (Square targetSquare : targetSquares) {
                shouldAddSquare = false;
                if (targetSquare != null) {
                    if (targetSquare.isOccupied()) {
                        if (targetSquare.getCurrentPiece().colour != this.colour) {
                            shouldAddSquare = true;
                        }
                    } else {
                        shouldAddSquare = true;
                    }

                    if (shouldAddSquare && board.checkValidPosition(new BasicMove(currentSquare, targetSquare))) {
                        legalMoves.add(new BasicMove(currentSquare, targetSquare));
                    }
                }
            }
        }

        return legalMoves;
    }

    protected List<Move> getRadialMoves(Board board, int[] xDirs, int[] yDirs, boolean singleSquare) {
        List<Move> legalMoves = new ArrayList<Move>();

        assert (xDirs.length == yDirs.length);
        Square currentSquare = board.getSquare(squareRow, squareCol);
        Square targetSquare = currentSquare;

        for (int i = 0; i < xDirs.length; i++) {
            // Continue searching until we find an invalid square or another piece
            boolean continueSearch = true;
            boolean shouldAddSquare = false;

            // Reset target square to current square before each search
            targetSquare = currentSquare;

            while (continueSearch) {
                shouldAddSquare = false;
                targetSquare = board.getSquare(targetSquare.getRow() + xDirs[i], targetSquare.getColumn() + yDirs[i]);

                if (targetSquare == null) {
                    continueSearch = false;
                } else if (targetSquare.isOccupied()) {
                    continueSearch = false;

                    // If piece belongs to other colour we can move there
                    if (targetSquare.getCurrentPiece().colour != this.colour) {
                        shouldAddSquare = true;
                    }
                } else {
                    shouldAddSquare = true;
                }

                if (targetSquare != null) {
                    if (shouldAddSquare && board.checkValidPosition(new BasicMove(currentSquare, targetSquare))) {
                        legalMoves.add(new BasicMove(currentSquare, targetSquare));
                    }
                }

                // If it is a king we only want to look one square in each direction
                // TODO can put check for other king here
                if (singleSquare) {
                    break;
                }
            }
        }

        return legalMoves;
    }

    private int clamp(int val, int min, int max) {
        return Math.max(min, Math.min(max, val));
    }

    protected List<Move> getBishopMoves(Board board) {
        final int[] xDirs = {-1, -1, 1, 1};
        final int[] yDirs = {-1, 1, -1, 1};

        return getRadialMoves(board, xDirs, yDirs, false);
    }

    protected List<Move> getRookMoves(Board board) {
        final int[] xDirs = {-1, 0, 1, 0};
        final int[] yDirs = {0, -1, 0, 1};

        return getRadialMoves(board, xDirs, yDirs, false);
    }

    protected List<Move> getQueenMoves(Board board) {
        final int[] xDirs = {-1, -1, -1, 0, 0, 1, 1, 1};
        final int[] yDirs = {-1, 0, 1, -1, 1, -1, 0, 1};

        return getRadialMoves(board, xDirs, yDirs, false);
    }

    protected List<Move> getKingMoves(Board board) {
        final int[] xDirs = {-1, -1, -1, 0, 0, 1, 1, 1};
        final int[] yDirs = {-1, 0, 1, -1, 1, -1, 0, 1};

        return getRadialMoves(board, xDirs, yDirs, true);
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
            } else {
                return true;
            }
        }

        return true;
    }

    public String getSymbol() {
        return symbol;
    }

    public Colour getColour() {
        return colour;
    }

    public String getSymbol(boolean coloured) {
        // Uppercase chars represent black, lowercase white
        if (coloured) {
            return this.colour == Colour.BLACK ? symbol.toUpperCase() : symbol.toLowerCase();
        } else {
            return symbol;
        }
    }

    public String getPieceName() {
        return pieceName;
    }

    public int getSquareNum() {
        return squareNum;
    }

    public int getSquareRow() {
        return squareRow;
    }

    public int getSquareCol() {
        return squareCol;
    }

    public int getInitialSquareNum() {
        return initialSquareNum;
    }

    @Override
    public String toString() {
        return "Piece{" +
                "(" + pieceName + ")" +
                ", colour=" + colour +
                ", squareRow=" + squareRow +
                ", squareCol=" + squareCol +
                ", hasBeenCaptured=" + hasBeenCaptured +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Piece piece = (Piece) o;
        return pieceName == piece.pieceName &&
                symbol == piece.symbol &&
                colour == piece.colour &&
                initialSquareNum == piece.initialSquareNum;
    }

    @Override
    public int hashCode() {
        return Objects.hash(pieceName, symbol, colour, squareNum, squareRow, squareCol);
    }


    // TODO make this a list of Moves
    public abstract List<Move> getLegalMoves(Board board);
}


