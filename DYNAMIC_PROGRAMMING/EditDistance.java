package ALGORITHMS.DYNAMIC_PROGRAMMING;

/**
 * Edit Distance (Levenshtein Distance)
 * 
 * Problem: Minimum operations to convert string s1 to s2
 * Operations: Insert, Delete, Replace
 * 
 * Time Complexity: O(m × n)
 * Space Complexity: O(n) - space optimized
 */

public class EditDistance {

    // 2D DP Solution
    public static int editDistance2D(String s1, String s2) {
        int m = s1.length(), n = s2.length();
        int[][] dp = new int[m + 1][n + 1];

        // Base cases
        for (int i = 0; i <= m; i++)
            dp[i][0] = i;
        for (int j = 0; j <= n; j++)
            dp[0][j] = j;

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    dp[i][j] = 1 + Math.min(
                            dp[i - 1][j], // Delete
                            Math.min(
                                    dp[i][j - 1], // Insert
                                    dp[i - 1][j - 1] // Replace
                            ));
                }
            }
        }

        return dp[m][n];
    }

    // Space Optimized O(n)
    public static int editDistanceOptimized(String s1, String s2) {
        int m = s1.length(), n = s2.length();
        int[] prev = new int[n + 1];
        int[] curr = new int[n + 1];

        for (int j = 0; j <= n; j++)
            prev[j] = j;

        for (int i = 1; i <= m; i++) {
            curr[0] = i;
            for (int j = 1; j <= n; j++) {
                if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                    curr[j] = prev[j - 1];
                } else {
                    curr[j] = 1 + Math.min(prev[j], Math.min(curr[j - 1], prev[j - 1]));
                }
            }
            int[] temp = prev;
            prev = curr;
            curr = temp;
        }

        return prev[n];
    }

    public static void main(String[] args) {
        String s1 = "horse";
        String s2 = "ros";

        System.out.println("Edit Distance (2D): " + editDistance2D(s1, s2));
        System.out.println("Edit Distance (Optimized): " + editDistanceOptimized(s1, s2));

        s1 = "intention";
        s2 = "execution";
        System.out.println("\nEdit Distance for '" + s1 + "' -> '" + s2 + "': " +
                editDistance2D(s1, s2));
    }
}
