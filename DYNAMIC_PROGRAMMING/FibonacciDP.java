package ALGORITHMS.DYNAMIC_PROGRAMMING;

/**
 * Fibonacci Sequence using Dynamic Programming
 * 
 * Problem: Find the nth Fibonacci number
 * Pattern: Linear DP
 * 
 * Time Complexity: O(n)
 * Space Complexity: O(1) - space optimized
 */

public class FibonacciDP {

    // Method 1: Top-Down with Memoization
    public static long fibonacciMemo(int n, long[] memo) {
        if (n <= 1)
            return n;
        if (memo[n] != -1)
            return memo[n];

        memo[n] = fibonacciMemo(n - 1, memo) + fibonacciMemo(n - 2, memo);
        return memo[n];
    }

    // Method 2: Bottom-Up Tabulation
    public static long fibonacciTab(int n) {
        if (n <= 1)
            return n;

        long[] dp = new long[n + 1];
        dp[0] = 0;
        dp[1] = 1;

        for (int i = 2; i <= n; i++) {
            dp[i] = dp[i - 1] + dp[i - 2];
        }

        return dp[n];
    }

    // Method 3: Space Optimized
    public static long fibonacciOptimized(int n) {
        if (n <= 1)
            return n;

        long prev2 = 0, prev1 = 1;

        for (int i = 2; i <= n; i++) {
            long curr = prev1 + prev2;
            prev2 = prev1;
            prev1 = curr;
        }

        return prev1;
    }

    public static void main(String[] args) {
        int n = 10;

        // Memoization
        long[] memo = new long[n + 1];
        for (int i = 0; i <= n; i++)
            memo[i] = -1;
        System.out.println("Fibonacci(" + n + ") [Memo] = " + fibonacciMemo(n, memo));

        // Tabulation
        System.out.println("Fibonacci(" + n + ") [Tab] = " + fibonacciTab(n));

        // Optimized
        System.out.println("Fibonacci(" + n + ") [Optimized] = " + fibonacciOptimized(n));
    }
}
