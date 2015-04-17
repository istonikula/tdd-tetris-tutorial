package tetris;

public class Block implements Grid {
    private final char color;

    public Block(char color) {
        this.color = color;
    }

    public char getColor() {
        return color;
    }

    @Override
    public int rows() {
        return 1;
    }

    @Override
    public int cols() {
        return 1;
    }

    @Override
    public char cellAt(int row, int col) {
        return color;
    }
}
