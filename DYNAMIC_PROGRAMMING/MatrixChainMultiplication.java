package ALGORITHMS.DYNAMIC_PROGRAMMING;

/**
 * Matrix Chain Multiplication
 * 
 * Problem: Find minimum cost to multiply chain of matrices
 * Pattern: Interval DP
 * 
 * Time Complexity: O(n³)
 * Space Complexity: O(n²)
 */

public class MatrixChainMultiplication {

    // Recursive + Memoization
    public static int mcmMemo(int[] dims) {
        int n = dims.length;
        int[][] memo = new int[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                memo[i][j] = -1;
            }
        }

        return mcmHelper(dims, 1, n - 1, memo);
    }

    private static int mcmHelper(int[] dims, int i, int j, int[][] memo) {
        if (i == j)
            return 0;

        if (memo[i][j] != -1)
            return memo[i][j];

        int min = Integer.MAX_VALUE;

        for (int k = i; k < j; k++) {
            int cost = mcmHelper(dims, i, k, memo) +
                    mcmHelper(dims, k + 1, j, memo) +
                    dims[i - 1] * dims[k] * dims[j];

            min = Math.min(min, cost);
        }

        memo[i][j] = min;
        return min;
    }

    // Bottom-Up DP
    public static int mcmDP(int[] dims) {
        int n = dims.length;
        int[][] dp = new int[n][n];

        // len is chain length
        for (int len = 2; len < n; len++) {
            for (int i = 1; i < n - len + 1; i++) {
                int j = i + len - 1;
                dp[i][j] = Integer.MAX_VALUE;

                for (int k = i; k < j; k++) {
                    int cost = dp[i][k] + dp[k + 1][j] +
                            dims[i - 1] * dims[k] * dims[j];

                    dp[i][j] = Math.min(dp[i][j], cost);
                }
            }
        }

        return dp[1][n - 1];
    }

    public static void main(String[] args) {
        // Matrix dimensions: A1(10×30) × A2(30×5) × A3(5×60)
        int[] dims = { 10, 30, 5, 60 };

        System.out.println("Min multiplications (Memo): " + mcmMemo(dims));
        System.out.println("Min multiplications (DP): " + mcmDP(dims));

        // Another example
        int[] dims2 = { 40, 20, 30, 10, 30 };
        System.out.println("\nMin multiplications: " + mcmDP(dims2));
    }
}
