package tetris;

public class Block {
    private final char color;

    public Block(char color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return String.valueOf(color);
    }
}
