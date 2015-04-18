// Copyright (c) 2008-2015  Esko Luontola <www.orfjackal.net>
// You may use and modify this source code freely for personal non-commercial use.
// This source code may NOT be used as course material without prior written agreement.

package tetris;

import java.util.Arrays;

public class Board {
    private static final char EMPTY = '.';
    private final int rows;
    private final int columns;
    private char[][] board;
    private MovableGrid falling;

    public Board(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        board = new char[rows][columns];
        for (int i = 0; i < rows; i++) {
            Arrays.fill(board[i], EMPTY);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                sb.append(cellAt(row, col));
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    private char cellAt(int row, int col) {
        if (hasFallingAt(row, col)) {
            return falling.cellAt(row, col);
        }
        return board[row][col];
    }

    private boolean hasFallingAt(int row, int col) {
        return hasFalling() && falling.isAt(row, col);
    }

    public boolean hasFalling() {
        return falling != null;
    }

    public void drop(Grid x) {
        if (falling != null) {
            throw new IllegalStateException("already falling");
        }
        falling = new MovableGrid(x);
    }

    public void tick() {
        moveDown();
    }

    public void moveLeft() {
        MovableGrid candidate = falling.moveLeft();
        if (candidate.isValid()) {
            falling = candidate;
        }
    }

    private void copyToBoard() {
        falling.copyToBoard();
    }

    public void moveRight() {
        MovableGrid candidate = falling.moveRight();
        if (candidate.isValid()) {
            falling = candidate;
        }
    }

    public void moveDown() {
        MovableGrid candidate = falling.moveDown();
        if (candidate.isValid()) {
            falling = candidate;
        } else {
            copyToBoard();
            falling = null;
        }
    }

    public class MovableGrid implements Grid {
        private final Grid g;
        public final int rowOffset;
        public final int colOffset;

        public MovableGrid(Grid g) {
            this(g, 0, columns / 2 - g.cols() / 2);
        }

        private MovableGrid(Grid g, int rowOffset, int colOffset) {
            this.g = g;
            this.rowOffset = rowOffset;
            this.colOffset = colOffset;
        }

        public MovableGrid moveLeft() {
            return new MovableGrid(g, rowOffset, colOffset - 1);
        }

        public MovableGrid moveRight() {
            return new MovableGrid(g, rowOffset, colOffset + 1);
        }

        public MovableGrid moveDown() {
            return new MovableGrid(g, rowOffset + 1, colOffset);
        }

        public boolean isAt(int row, int col) {
            return row >= rowOffset &&
                    row < rowOffset + rows() &&
                    col >= colOffset &&
                    col < colOffset + cols() &&
                    cellAt(row, col) != EMPTY;
        }

        public void copyToBoard() {
            for (int row = 0; row < rows(); row++) {
                for (int col = 0; col < cols(); col++) {
                    int r = row + rowOffset;
                    int c = col + colOffset;
                    if (cellAt(r, c) != EMPTY) {
                        board[r][c] = falling.cellAt(r, c);
                    }
                }
            }
        }

        public boolean isValid() {
            for (int row = 0; row < rows(); row++) {
                for (int col = 0; col < g.cols(); col++) {
                    int r = rowOffset + row;
                    int c = colOffset + col;
                    if (cellAt(r, c) != EMPTY) {
                        if (isOutsideBoard(r, c) || hitsStaticBlock(r, c)) {
                            return false;
                        }
                    }
                }
            }
            return true;
        }

        @Override
        public char cellAt(int row, int col) {
            return g.cellAt(row - rowOffset, col - colOffset);
        }

        @Override
        public int rows() {
            return g.rows();
        }

        @Override
        public int cols() {
            return g.cols();
        }

        private boolean hitsStaticBlock(int row, int col) {
            return board[row][col] != EMPTY;
        }

        private boolean isOutsideBoard(int row, int col) {
            return row < 0 || row > rows - 1 || col < 0 || col > columns - 1;
        }
    }
}
