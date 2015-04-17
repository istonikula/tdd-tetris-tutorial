// Copyright (c) 2008-2015  Esko Luontola <www.orfjackal.net>
// You may use and modify this source code freely for personal non-commercial use.
// This source code may NOT be used as course material without prior written agreement.

package tetris;

public class Board {
    private final int rows;
    private final int columns;

    private char[][] board;

    private Grid fallingGrid;
    private int fallingGridRow;
    private int fallingGridCol;

    public Board(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        board = new char[rows][columns];
        for (int i = 0; i < rows ; i++) {
            for (int j = 0; j < columns; j++) {
                board[i][j] = '.';
            }
        }
        resetFalling();
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
            return fallingGrid.cellAt(row - fallingGridRow, col - fallingGridCol);
        }
        return staticAt(row, col);
    }

    private boolean hasFallingAt(int row, int col) {
        return hasFalling() &&
                row >= fallingGridRow &&
                row < fallingGridRow + fallingGrid.rows() &&
                col >= fallingGridCol &&
                col < fallingGridCol + fallingGrid.cols();
    }

    private char staticAt(int row, int col) {
        return board[row][col];
    }

    public boolean hasFalling() {
        return fallingGrid != null;
    }

    public void drop(Grid x) {
        if (fallingGrid != null) {
            throw new IllegalStateException("already falling");
        }
        fallingGridRow = 0;
        fallingGridCol = columns/2  - x.cols()/2;

        this.fallingGrid = x;
    }

    public void tick() {
        if (hitsBottom() || hitsStatic() ) {
            copyToBoard();
        } else {
            fallingGridRow++;
        }
    }

    private boolean hitsStatic() {
        return staticAt(fallingGridRow + 1, fallingGridCol) != '.';
    }

    private void copyToBoard() {
        board[fallingGridRow][fallingGridCol] = fallingGrid.cellAt(fallingGridRow, fallingGridCol);
        resetFalling();
    }

    private boolean hitsBottom() {
        return fallingGridRow == rows - 1;
    }

    private void resetFalling() {
        fallingGrid = null;
    }
}
