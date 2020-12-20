package test.java.com.sd.BoardTypesTest;

import main.java.com.sd.board.Square;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SquareTest {
    private final int width = 8;
    private final int height = 8;

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testGetRow() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                Square testSquare = new Square(i, j, width, height);
                int row = testSquare.getRow();
                assertEquals(i, row);
            }

        }
    }

    @Test
    public void testGetColumn() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                Square testSquare = new Square(i, j, width, height);
                int col = testSquare.getColumn();
                assertEquals(j, col);
            }

        }
    }
}

