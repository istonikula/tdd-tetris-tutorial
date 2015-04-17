package tetris;

public class Tetromino {
    public static final Tetromino T_SHAPE = new Tetromino(new Piece(
            ".T.\n" +
            "TTT\n" +
            "...\n"));

    public static final Tetromino I_SHAPE = new Tetromino(new Piece(
            ".....\n" +
            ".....\n" +
            "IIII.\n" +
            ".....\n" +
            ".....\n"));

    private final Piece piece;

    private Tetromino(Piece p) {
        piece  = p;
    }

    public Tetromino rotateRight() {
        return new Tetromino(piece.rotateRight());
    }

    public Tetromino rotateLeft() {
        return new Tetromino(piece.rotateLeft());
    }

    @Override
    public String toString() {
        return piece.toString();
    }
}
