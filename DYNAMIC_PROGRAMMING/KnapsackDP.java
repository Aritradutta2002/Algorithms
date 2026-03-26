package ALGORITHMS.DYNAMIC_PROGRAMMING;

/**
 * 0/1 Knapsack Problem using Dynamic Programming
 * 
 * Problem: Given weights and values of n items, find max value for given
 * capacity
 * Pattern: 2D DP
 * 
 * Time Complexity: O(n × W) where W is capacity
 * Space Complexity: O(W) - space optimized
 */

public class KnapsackDP {

    // 2D DP Solution
    public static int knapsack2D(int[] weights, int[] values, int capacity) {
        int n = weights.length;
        int[][] dp = new int[n + 1][capacity + 1];

        for (int i = 1; i <= n; i++) {
            for (int w = 1; w <= capacity; w++) {
                if (weights[i - 1] <= w) {
                    dp[i][w] = Math.max(
                            values[i - 1] + dp[i - 1][w - weights[i - 1]],
                            dp[i - 1][w]);
                } else {
                    dp[i][w] = dp[i - 1][w];
                }
            }
        }

        return dp[n][capacity];
    }

    // Space Optimized 1D DP
    public static int knapsackOptimized(int[] weights, int[] values, int capacity) {
        int[] dp = new int[capacity + 1];

        for (int i = 0; i < weights.length; i++) {
            for (int w = capacity; w >= weights[i]; w--) {
                dp[w] = Math.max(dp[w], values[i] + dp[w - weights[i]]);
            }
        }

        return dp[capacity];
    }

    public static void main(String[] args) {
        int[] values = { 60, 100, 120 };
        int[] weights = { 10, 20, 30 };
        int capacity = 50;

        System.out.println("Max value (2D): " + knapsack2D(weights, values, capacity));
        System.out.println("Max value (Optimized): " + knapsackOptimized(weights, values, capacity));
    }
}
