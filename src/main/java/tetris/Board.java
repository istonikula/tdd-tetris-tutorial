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
            return fallingGrid.cellAt(row - fallingGrid.boardRow, col - fallingGrid.boardCol);
        }
        return board[row][col];
    }

    private boolean hasFallingAt(int row, int col) {
        return hasFalling() &&
                row >= fallingGrid.boardRow &&
                row < fallingGrid.boardRow + fallingGrid.rows() &&
                col >= fallingGrid.boardCol &&
                col < fallingGrid.boardCol + fallingGrid.cols() &&
                fallingGrid.cellAt(row - fallingGrid.boardRow, col - fallingGrid.boardCol) != EMPTY;
    }

    public boolean hasFalling() {
        return fallingGrid != null;
    }

    public void drop(Grid x) {
        if (fallingGrid != null) {
            throw new IllegalStateException("already falling");
        }
        fallingGrid = new MovableGrid(x);
    }

    public void tick() {
        moveDown();
    }

    public void moveLeft() {
        MovableGrid candidate = fallingGrid.moveLeft();
        if (isValidMove(candidate)) {
            fallingGrid = candidate;
        }
    }

    private void copyToBoard() {
        for (int row = 0; row < fallingGrid.rows(); row++) {
            for (int col = 0; col < fallingGrid.cols(); col++) {
                if (fallingGrid.cellAt(row, col) != EMPTY) {
                    board[fallingGrid.boardRow + row][fallingGrid.boardCol + col] = fallingGrid.cellAt(row, col);
                }
            }
        }
    }

    public void moveRight() {
        MovableGrid candidate = fallingGrid.moveRight();
        if (isValidMove(candidate)) {
            fallingGrid = candidate;
        }
    }

    public void moveDown() {
        MovableGrid candidate = fallingGrid.moveDown();
        if (isValidMove(candidate)) {
            fallingGrid = candidate;
        } else {
            copyToBoard();
            fallingGrid = null;
        }
    }

    private boolean isValidMove(MovableGrid g) {
        for (int row = 0; row < g.rows(); row++) {
            for (int col = 0; col < g.cols(); col++) {
                if (g.cellAt(row, col) != EMPTY) {
                    int r = g.boardRow + row;
                    int c = g.boardCol + col;
                    if (isOutsideBoard(r, c) || hitsStaticBlock(r, c)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private boolean hitsStaticBlock(int row, int col) {
        return board[row][col] != EMPTY;
    }

    private boolean isOutsideBoard(int row, int col) {
        return row < 0 || row > rows - 1 || col < 0 || col > columns - 1;
    }

    public class MovableGrid implements Grid {
        private final Grid g;
        public final int boardRow;
        public final int boardCol;

        public MovableGrid(Grid g) {
            this(g, 0, columns / 2 - g.cols() / 2);
        }

        private MovableGrid(Grid g, int boardRow, int boardCol) {
            this.g = g;
            this.boardRow = boardRow;
            this.boardCol = boardCol;
        }

        public MovableGrid moveLeft() {
            return new MovableGrid(g, boardRow, boardCol - 1);
        }

        public MovableGrid moveRight() {
            return new MovableGrid(g, boardRow, boardCol + 1);
        }

        public MovableGrid moveDown() {
            return new MovableGrid(g, boardRow + 1, boardCol);
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
