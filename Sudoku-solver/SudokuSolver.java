import java.util.ArrayList;
import java.util.List;

public class SudokuSolver {

    private int[][] board;

    public SudokuSolver(int[][] board) {
        this.board = board;
    }

    public boolean solve() {
        int row = -1;
        int col = -1;
        boolean isEmpty = true;

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board[i][j] == 0) {
                    row = i;
                    col = j;
                    isEmpty = false;
                    break;
                }
            }
            if (!isEmpty) {
                break;
            }
        }

        if (isEmpty) {
            return true;
        }

        for (int num = 1; num <= 9; num++) {
            if (isValid(row, col, num)) {
                board[row][col] = num;

                if (solve()) {
                    return true;
                } else {
                    board[row][col] = 0;
                }
            }
        }
        return false;
    }

    private boolean isValid(int row, int col, int num) {
        for (int i = 0; i < 9; i++) {
            if (board[row][i] == num) {
                return false;
            }
            if (board[i][col] == num) {
                return false;
            }
            if (board[3 * (row / 3) + i / 3][3 * (col / 3) + i % 3] == num) {
                return false;
            }
        }
        return true;
    }

    public int[][] getBoard() {
        return board;
    }
}
