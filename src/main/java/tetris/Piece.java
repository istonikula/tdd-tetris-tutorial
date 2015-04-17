package tetris;

public class Piece {
    private Block[][] blocks;

    public Piece(String s) {
        String[] split = s.split("\n");
        blocks = new Block[split.length][split[0].length()];

        for (int i = 0; i < split.length; i++) {
            String row = split[i];
            for (int j = 0; j < row.length() ; j++) {
                char c = row.charAt(j);
                if (c != '.') {
                    blocks[i][j] = new Block(c);
                }
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks[i].length; j++ ) {
                Block b = blocks[i][j];
                if (b == null) {
                    sb.append(".");
                } else {
                    sb.append(b.getColor());
                }
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public Piece rotateRight() {
        return null;
    }
}
