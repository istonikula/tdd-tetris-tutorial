package tetris;

public interface Grid {
    int rows();
    int cols();
    char cellAt(int row, int col);
}
