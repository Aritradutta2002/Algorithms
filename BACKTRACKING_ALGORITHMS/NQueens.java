package ALGORITHMS.BACKTRACKING_ALGORITHMS;
/**
 * N-Queens Problem - Backtracking
 * Problem: Place N queens on N×N chessboard so no two queens attack each other
 * Time Complexity: O(N!)
 * Space Complexity: O(N²)
 */

import java.util.*;

public class NQueens {

    public static List<List<String>> solveNQueens(int n) {
        List<List<String>> solutions = new ArrayList<>();
        char[][] board = new char[n][n];

        // Initialize board
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                board[i][j] = '.';
            }
        }

        backtrack(board, 0, solutions);
        return solutions;
    }

    private static void backtrack(char[][] board, int row, List<List<String>> solutions) {
        if (row == board.length) {
            solutions.add(constructSolution(board));
            return;
        }

        for (int col = 0; col < board.length; col++) {
            if (isValid(board, row, col)) {
                board[row][col] = 'Q';
                backtrack(board, row + 1, solutions);
                board[row][col] = '.'; // Backtrack
            }
        }
    }

    private static boolean isValid(char[][] board, int row, int col) {
        // Check column
        for (int i = 0; i < row; i++) {
            if (board[i][col] == 'Q')
                return false;
        }

        // Check diagonal (top-left)
        for (int i = row - 1, j = col - 1; i >= 0 && j >= 0; i--, j--) {
            if (board[i][j] == 'Q')
                return false;
        }

        // Check diagonal (top-right)
        for (int i = row - 1, j = col + 1; i >= 0 && j < board.length; i--, j++) {
            if (board[i][j] == 'Q')
                return false;
        }

        return true;
    }

    private static List<String> constructSolution(char[][] board) {
        List<String> solution = new ArrayList<>();
        for (char[] row : board) {
            solution.add(new String(row));
        }
        return solution;
    }

    public static void main(String[] args) {
        int n = 4;
        List<List<String>> solutions = solveNQueens(n);

        System.out.println("Total solutions for " + n + "-Queens: " + solutions.size());
        System.out.println("\nFirst solution:");
        for (String row : solutions.get(0)) {
            System.out.println(row);
        }
    }
}
