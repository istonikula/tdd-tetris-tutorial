package tetris;

public class Tetromino {
    public static final Tetromino T_SHAPE = new Tetromino(new Piece(""));

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
}
