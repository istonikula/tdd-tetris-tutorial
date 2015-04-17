package tetris;

public class Tetromino implements Grid {
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
    public static final Tetromino O_SHAPE = new Tetromino(
            new Piece(
                ".OO\n" +
                ".OO\n" +
                "...\n")
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

    @Override
    public int rows() {
        return pieces[0].size();
    }

    @Override
    public int cols() {
        return pieces[0].size();
    }

    @Override
    public char cellAt(int row, int col) {
        return pieces[position].cellAt(row, col);
    }
}
