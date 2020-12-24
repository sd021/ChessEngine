package main.java.com.sd.pieces.colours;

public class ColourHelper {
    public static Colour getOppositeColour(Colour colour) {
        return colour == Colour.WHITE ? Colour.BLACK : Colour.WHITE;
    }
}
