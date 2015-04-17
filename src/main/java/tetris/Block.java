package tetris;

public class Block {
    private final char color;

    public Block(char color) {
        this.color = color;
    }

    public String getColor() {
        return String.valueOf(color);
    }
}
