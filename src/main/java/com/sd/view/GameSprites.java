package main.java.com.sd.view;

import java.awt.image.*;
        import javax.imageio.*;
import javax.swing.*;
import java.net.*;
        import java.io.*;
        import java.util.*;

public class GameSprites {
    private GameSprites() {}
    public static final int SIZE = 64;
    public static final BufferedImage SHEET;
    static {
        try {
            // see https://stackoverflow.com/a/19209651/2891664
            SHEET = ImageIO.read(new URL("https://i.stack.imgur.com/memI0.png"));
        } catch (IOException x) {
            throw new UncheckedIOException(x);
        }
    }
    public static final BufferedImage BLACK_KING    = SHEET.getSubimage(0 * SIZE, 0,    SIZE, SIZE);
    public static final BufferedImage WHITE_KING = SHEET.getSubimage(0 * SIZE, SIZE, SIZE, SIZE);
    public static final BufferedImage BLACK_QUEEN     = SHEET.getSubimage(1 * SIZE, 0,    SIZE, SIZE);
    public static final BufferedImage WHITE_QUEEN   = SHEET.getSubimage(1 * SIZE, SIZE, SIZE, SIZE);
    public static final BufferedImage BLACK_ROOK     = SHEET.getSubimage(2 * SIZE, 0,    SIZE, SIZE);
    public static final BufferedImage WHITE_ROOK   = SHEET.getSubimage(2 * SIZE, SIZE, SIZE, SIZE);
    public static final BufferedImage BLACK_KNIGHT   = SHEET.getSubimage(3 * SIZE, 0,    SIZE, SIZE);
    public static final BufferedImage WHITE_KNIGHT = SHEET.getSubimage(3 * SIZE, SIZE, SIZE, SIZE);
    public static final BufferedImage BLACK_BISHOP   = SHEET.getSubimage(4 * SIZE, 0,    SIZE, SIZE);
    public static final BufferedImage WHITE_BISHOP = SHEET.getSubimage(4 * SIZE, SIZE, SIZE, SIZE);
    public static final BufferedImage BLACK_PAWN     = SHEET.getSubimage(5 * SIZE, 0,    SIZE, SIZE);
    public static final BufferedImage WHITE_PAWN   = SHEET.getSubimage(5 * SIZE, SIZE, SIZE, SIZE);
    public static final List<BufferedImage> SPRITES =
            Collections.unmodifiableList(Arrays.asList(BLACK_QUEEN,  WHITE_QUEEN,
                    BLACK_KING,   WHITE_KING,
                    BLACK_ROOK,   WHITE_ROOK,
                    BLACK_KNIGHT, WHITE_KNIGHT,
                    BLACK_BISHOP, WHITE_BISHOP,
                    BLACK_PAWN,   WHITE_PAWN));
}
