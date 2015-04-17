// Copyright (c) 2008-2015  Esko Luontola <www.orfjackal.net>
// You may use and modify this source code freely for personal non-commercial use.
// This source code may NOT be used as course material without prior written agreement.

package tetris;

public class Board {
    private final int rows;
    private final int columns;

    private Block[][] staticBlocks;

    private Block fallingBlock;
    private int fallingBlockRow;
    private int fallingBlockCol;

    public Board(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        staticBlocks = new Block[rows][columns];
        resetFalling();
    }

    @Override
    public String toString() {
        String s = "";
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                Block b = getBlock(row, col);
                s += b == null ? "." : b.getColor();
            }
            s += "\n";
        }
        return s;
    }

    private Block getBlock(int row, int col) {
        if (hasFallingAt(row, col)) {
            return fallingBlock;
        }
        return getStaticBlock(row, col);
    }

    private boolean hasFallingAt(int row, int col) {
        return hasFalling() && row == fallingBlockRow && col == fallingBlockCol;
    }

    private Block getStaticBlock(int row, int col) {
        return staticBlocks[row][col];
    }

    public boolean hasFalling() {
        return fallingBlock != null;
    }

    public void drop(Block x) {
        if (fallingBlock != null) {
            throw new IllegalStateException("already falling");
        }

        this.fallingBlock = x;
    }

    public void tick() {
        if (hitsBottom() || hitsStatic() ) {
            moveToStatic();
        } else {
            fallingBlockRow++;
        }
    }

    private boolean hitsStatic() {
        return getStaticBlock(fallingBlockRow + 1, fallingBlockCol) != null;
    }

    private void moveToStatic() {
        staticBlocks[fallingBlockRow][fallingBlockCol] = fallingBlock;
        resetFalling();
    }

    private boolean hitsBottom() {
        return fallingBlockRow == rows - 1;
    }

    private void resetFalling() {
        fallingBlock = null;
        fallingBlockRow = 0;
        fallingBlockCol = 1;
    }
}
