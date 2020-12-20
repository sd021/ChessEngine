package main.java.com.sd.board;

public class SquareNames {
    public static final char[] rowNames = {'1', '2', '3', '4', '5', '6', '7', '8'};
    public static final char[] colNames = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H'};

    public static String squareName(int squareNum) {
        return new StringBuilder()
                .append(colNames[Square.convertSquareNumToCol(squareNum)])
                .append(rowNames[Square.convertSquareNumToRow(squareNum)])
                .toString();
    }
}
