package tetris;

public class Tetromino {
    public static final Tetromino T_SHAPE = new Tetromino(
            new Piece(
                ".T.\n" +
                "TTT\n" +
                "...\n"),
            new Piece(
                ".T.\n" +
                ".TT\n" +
                ".T.\n"),
            new Piece(
                "...\n" +
                "TTT\n" +
                ".T.\n"),
            new Piece(
                ".T.\n" +
                "TT.\n" +
                ".T.\n")
    );

    public static final Tetromino I_SHAPE = new Tetromino(
            new Piece(
                ".....\n" +
                ".....\n" +
                "IIII.\n" +
                ".....\n" +
                ".....\n"),
            new Piece(
                "..I..\n" +
                "..I..\n" +
                "..I..\n" +
                "..I..\n" +
                ".....\n")
    );

    private final Piece[] pieces;
    private final int position;

    private Tetromino(Piece... p) {
        this(0, p);

    }

    public Tetromino(int pos, Piece[] pieces) {
        position = pos;
        this.pieces  = pieces;
    }

    public Tetromino rotateRight() {
        return new Tetromino(((position+1) % pieces.length), pieces);
    }

    public Tetromino rotateLeft() {
        return rotateRight().rotateRight().rotateRight();
    }

    @Override
    public String toString() {
        return pieces[position].toString();
    }
}
