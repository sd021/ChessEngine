package main.java.com.sd.board;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SquareNames {
    private static Logger logger = LogManager.getLogger();

    public static final List<Character> rowNames = Arrays.asList('1', '2', '3', '4', '5', '6', '7', '8');
    public static final List<Character> colNames = Arrays.asList('A', 'B', 'C', 'D', 'E', 'F', 'G', 'H');

    public static String squareNameFromNumber(int squareNum) {
        if (squareNum == -1 || squareNum >= Board.BOARD_HEIGHT * Board.BOARD_WIDTH) {
            logger.error("Square number provided outside of valid range. (" + squareNum +")");
            return "X0";
        }

        return new StringBuilder()
                .append(colNames.get(Square.convertSquareNumToCol(squareNum)))
                .append(rowNames.get(Square.convertSquareNumToRow(squareNum)))
                .toString();
    }

    public static int squareNumberFromName(String squareName) {
        if (squareName.length() != 2){
            logger.error("Bad square name provided, name must be two characters. (" + squareName + ")");
            return -1;
        }
        if (!colNames.contains(squareName.charAt(0))){
            logger.error("Bad square name format. (" + squareName + ")");
            return -1;
        }
        if (!rowNames.contains(squareName.charAt(1))){
            logger.error("Bad square name format. (" + squareName + ")");
            return -1;
        }

        int col = colNames.indexOf(squareName.charAt(0));
        int row = rowNames.indexOf(squareName.charAt(1));

        return Square.convertRowColToSquareNum(row, col);

    }
}
