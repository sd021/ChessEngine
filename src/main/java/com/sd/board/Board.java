package main.java.com.sd.board;

import main.java.com.sd.moves.BasicMove;
import main.java.com.sd.moves.EnPassantMove;
import main.java.com.sd.moves.Move;
import main.java.com.sd.moves.PawnPromotionMove;
import main.java.com.sd.pieces.Piece;
import main.java.com.sd.pieces.colours.Colour;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import static main.java.com.sd.pieces.colours.Colour.BLACK;
import static main.java.com.sd.pieces.colours.Colour.WHITE;

public class Board {
    private static Logger logger = LogManager.getLogger();

    private List<Piece> allPieces = new ArrayList<Piece>();
    private List<Piece> takenPieces = new ArrayList<Piece>();
    private List<Piece> activePieces = new ArrayList<Piece>();
    private List<Piece> whitePieces = new ArrayList<Piece>();
    private List<Piece> blackPieces = new ArrayList<Piece>();

    public static final int BOARD_WIDTH = 8;
    public static final int BOARD_HEIGHT = 8;

    private Square[] boardArray = new Square[BOARD_WIDTH * BOARD_HEIGHT];

    public Board() {
        for (int i = 0; i < BOARD_WIDTH; i++) {
            for (int j = 0; j < BOARD_HEIGHT; j++) {
                Square square = new Square(i, j);
                boardArray[square.getSquareNum()] = square;
            }
        }
    }

    // Copy constructor
    public Board(Board board) {
        for (int i = 0; i < Board.BOARD_WIDTH * Board.BOARD_HEIGHT; i++) {
            this.setSquare(board.getSquare(i).makeCopy());
        }
    }

    public List<Piece> getActiveColouredPieces(Colour colour) {
        if (colour == WHITE) {
            return whitePieces;
        } else {
            return blackPieces;
        }
    }


    public Square getSquare(int squareNum) {
        if (squareNum == -1) {
            return null;
        } else {
            return boardArray[squareNum];
        }
    }

    public Square getSquare(int row, int col) {
        int squareNum = Square.convertRowColToSquareNum(row, col);
        return getSquare(squareNum);
    }

    public Square getSquare(Square square) {
        return boardArray[square.getSquareNum()];
    }

    public static int getSquareDistances(Square square1, Square square2) {
        return (int) Math.sqrt(Math.pow(square1.getRow() - square2.getRow(), 2) + Math.pow(square1.getColumn() - square2.getColumn(), 2));
    }

    public Square getSquare(String squareName) {
        int squareNum = SquareNames.squareNumberFromName(squareName);
        return getSquare(squareNum);
    }

    public void setSquare(Square square) {
        // TODO set constraints here on valid square
        if (square.getSquareNum() < 0 || square.getSquareNum() > BOARD_HEIGHT * BOARD_WIDTH) {
            logger.error("Can't set square with invalid square number.");
        } else {
            boardArray[square.getSquareNum()] = square;

            if (square.getCurrentPiece() != null) {
                allPieces.add(square.getCurrentPiece());
                activePieces.add(square.getCurrentPiece());
                if (square.getCurrentPiece().getColour() == WHITE) whitePieces.add(square.getCurrentPiece());
                if (square.getCurrentPiece().getColour() == BLACK) blackPieces.add(square.getCurrentPiece());
            }
        }
    }

    public List<Piece> getActivePieces() {
        return activePieces;
    }

    public List<Piece> getAllPieces() {
        return allPieces;
    }

    public List<Piece> getBlackPieces() {
        return blackPieces;
    }

    public List<Piece> getTakenPieces() {
        return takenPieces;
    }

    public List<Piece> getWhitePieces() {
        return whitePieces;
    }

    private void addColouredPiece(Piece piece) {
        if (piece.getColour() == WHITE) whitePieces.add(piece);
        if (piece.getColour()== BLACK) blackPieces.add(piece);
    }

    private void removeColouredPiece(Piece piece) {
        if (piece.getColour() == WHITE) whitePieces.remove(piece);
        if (piece.getColour()== BLACK) blackPieces.remove(piece);
    }

    public boolean checkValidPosition(BasicMove move) {
        return true;
    }

    public void makeMove(Move move) {
        makeMove(this, move);
    }

    public void reverseMove(Move move) {
        reverseMove(this, move);
    }

    public void makeMove(Board board, Move move) {
        String takenString = "";
        Piece movingPiece;

        if (move.getClass() == PawnPromotionMove.class) {
            movingPiece = ((PawnPromotionMove) move).getDesiredPiece();
        } else {
            movingPiece = move.getInitialSquare(board).getCurrentPiece();
        }

        // Set moving piece square to new square
        if (movingPiece == null) {
            logger.info(move);
            logger.info(board);
        }
        movingPiece.updatePieceSquare(move.getTargetSquare());

        // Set target square piece to moving piece
        move.getTargetSquare(board).updateSquare(movingPiece);

        // Remove the moving piece from initial square
        move.getInitialSquare(board).updateSquare();

        if (move.isCaptureMove()) {
            board.takenPieces.add(move.getCapturedPiece());
            board.activePieces.remove(move.getCapturedPiece());
            removeColouredPiece(move.getCapturedPiece());
            move.getCapturedPiece().setHasBeenCaptured(true);

            if (move.getClass() == EnPassantMove.class) {
                ((EnPassantMove) move).getTakenSquare(board).updateSquare(null);
            }
        }

        if (move.getClass() == PawnPromotionMove.class) {
            activePieces.add(((PawnPromotionMove) move).getDesiredPiece());
            activePieces.remove(((PawnPromotionMove) move).getPawnPiece());
            removeColouredPiece(((PawnPromotionMove) move).getPawnPiece());
            addColouredPiece(((PawnPromotionMove) move).getDesiredPiece());
        }
    }

    public void reverseMove(Board board, Move move) {
        Piece movedPiece;
        Piece restorePiece;

        if (move.getClass() == PawnPromotionMove.class) {
            movedPiece = ((PawnPromotionMove) move).getPawnPiece();
        } else {
            movedPiece = move.getBoardSquare(board, move.getTargetSquare(board)).getCurrentPiece();
        }

        restorePiece = move.getCapturedPiece();

        // Set moving piece square to initial square
        movedPiece.updatePieceSquare(move.getInitialSquare(board).getSquareNum());

        // Set initial square piece to moved piece
        move.getInitialSquare(board).updateSquare(movedPiece);

        // Restore original piece (or no piece) to target square
        move.getTargetSquare(board).updateSquare(restorePiece);

        if (move.isCaptureMove()) {
            board.takenPieces.remove(move.getCapturedPiece());
            board.activePieces.add(move.getCapturedPiece());
            addColouredPiece(move.getCapturedPiece());

            move.getCapturedPiece().setHasBeenCaptured(false);

            if (move.getClass() == EnPassantMove.class) {
                ((EnPassantMove) move).getTargetSquare(board).updateSquare(null);
                ((EnPassantMove) move).getTakenSquare(board).updateSquare(move.getCapturedPiece());
            }
        }

        if (move.getClass() == PawnPromotionMove.class) {
            //TODO
            activePieces.remove(((PawnPromotionMove) move).getDesiredPiece());
            activePieces.add(((PawnPromotionMove) move).getPawnPiece());
            addColouredPiece(((PawnPromotionMove) move).getPawnPiece());
            removeColouredPiece(((PawnPromotionMove) move).getDesiredPiece());
        }


    }

    @Override
    public String toString() {
        String outStr = "";

        for (int i = BOARD_HEIGHT - 1; i >= 0; i--) {
            for (int j = 0; j < BOARD_WIDTH; j++) {
                if (getSquare(i, j).getCurrentPiece() == null) {
                    outStr += ".";
                } else {
                    outStr += getSquare(i, j).getCurrentPiece().getSymbol(true);
                }
            }
            outStr += "\n";
        }

        return "\n" + outStr
                + "WhitePieces = " + this.whitePieces + "\n"
                + "BlackPieces = " + this.blackPieces + "\n"
                + "TakenPieces = " + this.takenPieces + "\n";
    }
}
