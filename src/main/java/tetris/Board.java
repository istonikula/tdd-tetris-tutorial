// Copyright (c) 2008-2015  Esko Luontola <www.orfjackal.net>
// You may use and modify this source code freely for personal non-commercial use.
// This source code may NOT be used as course material without prior written agreement.

package tetris;

public class Board {
    public static final char EMPTY = '.';
    private final int rows;
    private final int columns;

    private char[][] board;

    private MovableGrid fallingGrid;

    public Board(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        board = new char[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                board[i][j] = EMPTY;
            }
        }
    }

    @Override
    public String toString() {
        String s = "";
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                s += cellAt(row, col);
            }
            s += "\n";
        }
        return s;
    }

    private char cellAt(int row, int col) {
        if (hasFallingAt(row, col)) {
            return fallingGrid.cellAt(row - fallingGrid.row, col - fallingGrid.col);
        }
        return board[row][col];
    }

    private boolean hasFallingAt(int row, int col) {
        return hasFalling() &&
                row >= fallingGrid.row &&
                row < fallingGrid.row + fallingGrid.rows() &&
                col >= fallingGrid.col &&
                col < fallingGrid.col + fallingGrid.cols() &&
                fallingGrid.cellAt(row - fallingGrid.row, col - fallingGrid.col) != EMPTY;
    }

    public boolean hasFalling() {
        return fallingGrid != null;
    }

    public void drop(Grid x) {
        if (fallingGrid != null) {
            throw new IllegalStateException("already falling");
        }
        fallingGrid = new MovableGrid(x);
        fallingGrid.row = 0;
        fallingGrid.col = columns / 2 - x.cols() / 2;
    }

    public void tick() {
        if (hitsBottom() || hitsStatic()) {
            copyToBoard();
            fallingGrid = null;
        } else {
            fallingGrid.row++;
        }
    }

    private boolean hitsStatic() {
        for (int row = 0; row < fallingGrid.rows(); row++) {
            for (int col = 0; col < fallingGrid.cols(); col++) {
                if (fallingGrid.cellAt(row, col) != EMPTY) {
                    if (board[fallingGrid.row + row + 1][fallingGrid.col + col] != EMPTY) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private void copyToBoard() {
        for (int row = 0; row < fallingGrid.rows(); row++) {
            for (int col = 0; col < fallingGrid.cols(); col++) {
                if (fallingGrid.cellAt(row, col) != EMPTY) {
                    board[fallingGrid.row + row][fallingGrid.col + col] = fallingGrid.cellAt(row, col);
                }
            }
        }
    }

    private boolean hitsBottom() {
        for (int row = 0; row < fallingGrid.rows(); row++) {
            for (int col = 0; col < fallingGrid.cols(); col++) {
                if (fallingGrid.cellAt(row, col) != EMPTY) {
                    if (fallingGrid.row + row == rows - 1) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public void moveLeft() {
        if (!hitsLeft()) {
            fallingGrid.col--;
        }
    }

    public void moveRight() {
        if (!hitsRight()) {
            fallingGrid.col++;
        }
    }

    private boolean hitsLeft() {
        for (int col = 0; col < fallingGrid.cols(); col++) {
            for (int row = 0; row < fallingGrid.rows(); row++) {
                if (fallingGrid.cellAt(row, col) != EMPTY) {
                    if (fallingGrid.col + col == 0) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean hitsRight() {
        for (int col = 0; col < fallingGrid.cols(); col++) {
            for (int row = 0; row < fallingGrid.rows(); row++) {
                if (fallingGrid.cellAt(row, col) != EMPTY) {
                    if (fallingGrid.col + col == columns - 1) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public void moveDown() {
        tick();
    }


    public class MovableGrid implements Grid {
        private int row;
        public int col;
        private final Grid g;

        public MovableGrid(Grid g) {
            this.g = g;
        }

        @Override
        public int rows() {
            return g.rows();
        }

        @Override
        public int cols() {
            return g.cols();
        }

        @Override
        public char cellAt(int row, int col) {
            return g.cellAt(row, col);
        }
    }
}
