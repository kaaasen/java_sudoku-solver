import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SudokuSolverUI {
    private SudokuSolver solver;
    private JFrame frame;
    private JPanel gridPanel;
    private JTextField[][] textFields;
    private JButton solveButton;
    private JButton resetButton;

    public SudokuSolverUI(SudokuSolver solver) {
        this.solver = solver;
        createGridPanel();
        createFrame();
        createSolveButton();
        createResetButton();
        
    }

    private void createFrame() {
        frame = new JFrame("Sudoku Solver");
        frame.setLayout(new BorderLayout());
        frame.add(gridPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(solveButton);
        buttonPanel.add(resetButton);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void createGridPanel() {
        gridPanel = new JPanel(new GridLayout(9, 9, 2, 2));
        textFields = new JTextField[9][9];
        Font font = new Font("SansSerif", Font.BOLD, 20);

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                JTextField textField = new JTextField();
                textField.setFont(font);
                textField.setHorizontalAlignment(JTextField.CENTER);
                textField.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
                textFields[i][j] = textField;
                gridPanel.add(textField);
            }
        }
        // add thicker borders between 3x3 grids
        for (int i = 0; i < 9; i+=3) {
            for (int j = 0; j < 9; j+=3) {
                int rowStart = i;
                int rowEnd = i + 3;
                int colStart = j;
                int colEnd = j + 3;
                JTextField textField = textFields[i][j];
                textField.setBorder(BorderFactory.createMatteBorder(2, 2, rowEnd==9 ? 2 : 1, colEnd==9 ? 2 : 1, Color.BLACK));
            }
        }
    }

    private void createResetButton() {
        JButton resetButton = new JButton("Reset");
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < 9; i++) {
                    for (int j = 0; j < 9; j++) {
                        textFields[i][j].setText("");
                    }
                }
            }
        });
        buttonPanel.add(resetButton);
    }

    private void createSolveButton() {
        JButton solveButton = new JButton("Solve");
        solveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int[][] board = new int[9][9];
                for (int i = 0; i < 9; i++) {
                    for (int j = 0; j < 9; j++) {
                        String input = textFields[i][j].getText();
                        if (input.equals("")) {
                            board[i][j] = 0;
                        } else {
                            board[i][j] = Integer.parseInt(input);
                        }
                    }
                }
                SudokuSolver solver = new SudokuSolver(board);
                if (solver.solve()) {
                    int[][] solution = solver.getBoard();
                    for (int i = 0; i < 9; i++) {
                        for (int j = 0; j < 9; j++) {
                            textFields[i][j].setText(Integer.toString(solution[i][j]));
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "No solution found for the Sudoku puzzle");
                }
            }
        });
        buttonPanel.add(solveButton);
    }
}
