package main.java.com.sd.pieces;

import main.java.com.sd.board.Board;
import main.java.com.sd.board.Square;
import main.java.com.sd.moves.BasicMove;
import main.java.com.sd.moves.Move;
import main.java.com.sd.moves.NonBindingMove;
import main.java.com.sd.moves.PawnPromotionMove;
import main.java.com.sd.pieces.colours.Colour;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class Piece {
    private static Logger logger = LogManager.getLogger();

    public BufferedImage pieceSprite;
    protected String pieceName;
    protected String symbol = "";
    protected final Colour colour;
    protected boolean hasBeenCaptured = false;
    protected final int initialSquareNum; // Used to identify pieces later in the game
    protected int squareNum;
    protected int squareRow;
    protected int squareCol;
    protected int pieceValue;

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

        if (targetMoveSquare == null) {
            logger.debug(board);
            logger.debug(currentSquare);
            System.out.println("YOOOOO");
        }

        if (!getAttackedSquares) {
            if (!targetMoveSquare.isOccupied()) {

                // Prevent adding a move to last row as pawn must be promoted in that case
                if ((getDirection() == 1 && squareRow != 6) || (getDirection() == -1 && squareRow != 1)) {
                        legalMoves.add(new BasicMove(currentSquare.getSquareNum(), targetMoveSquare.getSquareNum()));
                } else {
                    legalMoves.addAll(PawnPromotionMove.generatePromotionMoves(colour, currentSquare.getSquareNum(), targetMoveSquare.getSquareNum(), this, targetMoveSquare.getCurrentPiece()));
                }
            }
        }

        Square[] targetTakeSquares = new Square[2];
        targetTakeSquares[0] = board.getSquare(currentSquare.getRow() + getDirection(), currentSquare.getColumn() - 1);
        targetTakeSquares[1] = board.getSquare(currentSquare.getRow() + getDirection(), currentSquare.getColumn() + 1);


        for (Square targetSquare : targetTakeSquares) {
            if (targetSquare != null) {
                if (getAttackedSquares) {
                    // Used for testing checked squares during castling
                    legalMoves.add(new NonBindingMove(currentSquare.getSquareNum(), targetSquare.getSquareNum()));
                } else if (targetSquare.isOccupied()) {
                    // Prevent adding a move to last row as pawn must be promoted in that case
                    if ((getDirection() == 1 && squareRow != 6) || (getDirection() == -1 && squareRow != 1)) {
                        if (targetSquare.getCurrentPiece().getColour() != this.getColour()) {
                            legalMoves.add(new BasicMove(currentSquare.getSquareNum(), targetSquare.getSquareNum(), targetSquare.getCurrentPiece()));
                        }
                    } else {
                        legalMoves.addAll(PawnPromotionMove.generatePromotionMoves(colour, currentSquare.getSquareNum(), targetSquare.getSquareNum(), this, targetSquare.getCurrentPiece()));
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

                    if (shouldAddSquare) {
                        legalMoves.add(new BasicMove(currentSquare.getSquareNum(), targetSquare.getSquareNum(), targetSquare.getCurrentPiece()));
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
                    if (targetSquare.getCurrentPiece() == null) {
                        System.out.println(currentSquare);
                        System.out.println(targetSquare);
                        System.out.println(board);
                    }

                    if (targetSquare.getCurrentPiece().getColour() != this.colour) {
                        shouldAddSquare = true;
                    }
                } else {
                    shouldAddSquare = true;
                }

                if (targetSquare != null) {
                    if (shouldAddSquare) {
                        legalMoves.add(new BasicMove(currentSquare.getSquareNum(), targetSquare.getSquareNum(), targetSquare.getCurrentPiece()));
                    }
                }

                // If it is a king we only want to look one square in each direction
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

    public int getPieceValue() {
        return pieceValue;
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
                ", pieceVal=" + pieceValue +
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


