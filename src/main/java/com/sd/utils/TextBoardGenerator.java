package main.java.com.sd.utils;

import main.java.com.sd.board.Board;
import main.java.com.sd.board.Square;
import main.java.com.sd.pieces.*;
import main.java.com.sd.pieces.colours.Colour;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class TextBoardGenerator {
    private static Logger logger = LogManager.getLogger();

    public static Board boardFromText(String[] boardRepresentation) {
        int arrayLength = boardRepresentation.length;

        if (arrayLength != Board.BOARD_HEIGHT) {
            logger.error("Input text array has incorrect number of lines" +
                    " (expected: " + Board.BOARD_HEIGHT + ", found: "+ arrayLength + ")");

            return null;
        }

        Board returnBoard = new Board();

        int counter = 0;
        for (int i = arrayLength-1; i >= 0 ; i--) {
            String currentLine = boardRepresentation[i];
            if (currentLine.length() != Board.BOARD_WIDTH) {
                logger.error("Input text array line has incorrect number of characters" +
                        " (expected: " + Board.BOARD_WIDTH + ", found: "+ currentLine.length() + ")");
                return null;
            }

            for (int j = 0; j < currentLine.length(); j++) {
                char currentChar = currentLine.charAt(j);

                // Uppercase chars represent black, lowercase white
                Colour colour = Character.isUpperCase(currentChar) ? Colour.BLACK : Colour.WHITE;

                switch(Character.toLowerCase(currentChar)) {
                    case('r'):
                        returnBoard.setSquare(new Square(counter, j, new Rook(colour, Square.convertRowColToSquareNum(counter, j))));
                        break;
                    case('b'):
                        returnBoard.setSquare(new Square(counter, j, new Bishop(colour, Square.convertRowColToSquareNum(counter, j))));
                        break;
                    case('p'):
                        returnBoard.setSquare(new Square(counter, j, new Pawn(colour, Square.convertRowColToSquareNum(counter, j))));
                        break;
                    case('n'):
                        returnBoard.setSquare(new Square(counter, j, new Knight(colour, Square.convertRowColToSquareNum(counter, j))));
                        break;
                    case('q'):
                        returnBoard.setSquare(new Square(counter, j, new Queen(colour, Square.convertRowColToSquareNum(counter, j))));
                        break;
                    case('k'):
                        returnBoard.setSquare(new Square(counter, j, new King(colour, Square.convertRowColToSquareNum(counter, j))));
                        break;
                    default:
                        returnBoard.setSquare(new Square(counter, j));
                        break;
                }
            }
            counter++;
        }

        return returnBoard;
    }
}
