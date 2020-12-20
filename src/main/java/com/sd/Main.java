package com.sd;

import main.java.com.sd.board.Board;
import main.java.com.sd.pieces.*;
import main.java.com.sd.pieces.colours.Colour;

public class Main {
    public static void main(String[] args) {
        Board board = new Board();
        Piece myPiece = new Rook(Colour.BLACK, 13);
        board.getBoardArray()[13].setCurrentPiece(myPiece);

        System.out.println(myPiece);
        System.out.println(myPiece.getLegalMoves(board));


    }
}
