package main.java.com.sd.pieces.colours;

public class Colour {
    public final static Colour WHITE = new Colour(0);
    public final static Colour BLACK = new Colour(1);

    private final Colour colour;

    public Colour(int colour) {
        if (colour == 0) {
            this.colour = WHITE;
        } else {
            this.colour = BLACK;
        }
    }

    public Colour getColour() {
        return colour;
    }
}
