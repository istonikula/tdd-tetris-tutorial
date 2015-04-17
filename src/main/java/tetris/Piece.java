package tetris;

public class Piece {
    private Block[][] blocks;

    public Piece(String s) {
        String[] split = s.split("\n");
        blocks = new Block[split.length][split[0].length()];

        for (int i = 0; i < split.length; i++) {
            String row = split[i];
            for (int j = 0; j < row.length(); j++) {
                char c = row.charAt(j);
                blocks[i][j] = new Block(c);
            }
        }
    }

    // 0,0 -> 0,c-1
    // 0,2 -> 1,c-1
    // 0,3 -> r-1,c-1
    //
    // 1,0 -> 0,1
    // 1,1 -> 1,1
    // 1,c-1 -> r-1,1
    //
    // r-1,0 -> 0,0
    // r-1,1 -> 1,0
    // r-1,2 -> r-1,0
    public Piece rotateRight() {
        Block[][] rotated = new Block[blocks.length][blocks.length];
        int n = blocks.length - 1;
        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks[i].length; j++) {
                int newRow = j;
                int newCol = n - i;
//                System.out.println("i -> newRow = " + i + " -> " + newRow);
//                System.out.println("j -> newCol = " + j + " -> " + newCol);
                rotated[newRow][newCol] = blocks[i][j];
            }
        }
        return new Piece(blocksToString(rotated));
    }

    @Override
    public String toString() {
        return blocksToString(blocks);
    }

    private String blocksToString(Block[][] blocks) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks[i].length; j++) {
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

    public Piece rotateLeft() {
        return rotateRight().rotateRight().rotateRight();
    }

    public int size() {
        return blocks.length;
    }

    public char cellAt(int row, int col) {
        return blocks[row][col].getColor();
    }
}
