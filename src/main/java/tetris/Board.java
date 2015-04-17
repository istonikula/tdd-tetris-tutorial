// Copyright (c) 2008-2015  Esko Luontola <www.orfjackal.net>
// You may use and modify this source code freely for personal non-commercial use.
// This source code may NOT be used as course material without prior written agreement.

package tetris;

import java.util.ArrayList;
import java.util.List;

public class Board {

    private final int rows;
    private final int columns;
    private Block block;
    private int blockRow = 0;
    private List<Block> staticBlocks = new ArrayList<>();

    public Board(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
    }

    public String toString() {
        String s = "";
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                Block b = getBlock(row, col);
                s += b == null ? "." : b.toString();
            }
            s += "\n";
        }
        return s;
    }

    private Block getBlock(int row, int col) {
        if (block != null && row == blockRow && col == 1) {
            return block;
        }
        Block staticBlock = getStaticBlock(row, col);
        return staticBlock;
    }

    private Block getStaticBlock(int row, int col) {
        if (row == rows - 1 && col == 1  && !staticBlocks.isEmpty()) {
            return staticBlocks.get(0);
        }
        return null;
    }

    public boolean hasFalling() {
        return block != null;
    }

    public void drop(Block x) {
        if (block != null) {
            throw new IllegalStateException("already falling");
        }

        this.block = x;
    }

    public void tick() {
        if (blockRow == rows - 1) {
            staticBlocks.add(block);
            block = null;
            blockRow = 0;
        } else {
            blockRow++;
        }
    }
}
