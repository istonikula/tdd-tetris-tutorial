package tetris;

public class Piece {
    private final String s;

    public Piece(String s) {
        this.s = s;
    }

    @Override
    public String toString() {
        return s;
    }
}
