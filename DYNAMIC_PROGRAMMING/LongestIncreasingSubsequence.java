package ALGORITHMS.DYNAMIC_PROGRAMMING;
/**
 * Longest Increasing Subsequence (LIS)
 * 
 * Problem: Find length of longest strictly increasing subsequence
 * 
 * Approach 1: DP - O(n²)
 * Approach 2: Binary Search + DP - O(n log n)
 */

import java.util.*;

public class LongestIncreasingSubsequence {

    // Method 1: O(n²) DP Solution
    public static int lisDP(int[] nums) {
        int n = nums.length;
        int[] dp = new int[n];
        Arrays.fill(dp, 1);

        int maxLen = 1;

        for (int i = 1; i < n; i++) {
            for (int j = 0; j < i; j++) {
                if (nums[i] > nums[j]) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
            maxLen = Math.max(maxLen, dp[i]);
        }

        return maxLen;
    }

    // Method 2: O(n log n) Binary Search + DP
    public static int lisBinarySearch(int[] nums) {
        List<Integer> tails = new ArrayList<>();

        for (int num : nums) {
            int pos = binarySearch(tails, num);

            if (pos == tails.size()) {
                tails.add(num);
            } else {
                tails.set(pos, num);
            }
        }

        return tails.size();
    }

    private static int binarySearch(List<Integer> tails, int target) {
        int left = 0, right = tails.size();

        while (left < right) {
            int mid = left + (right - left) / 2;
            if (tails.get(mid) < target) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }

        return left;
    }

    // Print actual LIS
    public static List<Integer> printLIS(int[] nums) {
        int n = nums.length;
        int[] dp = new int[n];
        int[] parent = new int[n];
        Arrays.fill(dp, 1);
        Arrays.fill(parent, -1);

        int maxLen = 1, maxIndex = 0;

        for (int i = 1; i < n; i++) {
            for (int j = 0; j < i; j++) {
                if (nums[i] > nums[j] && dp[j] + 1 > dp[i]) {
                    dp[i] = dp[j] + 1;
                    parent[i] = j;
                }
            }
            if (dp[i] > maxLen) {
                maxLen = dp[i];
                maxIndex = i;
            }
        }

        // Reconstruct LIS
        List<Integer> lis = new ArrayList<>();
        for (int i = maxIndex; i >= 0; i = parent[i]) {
            lis.add(nums[i]);
            if (parent[i] == -1)
                break;
        }
        Collections.reverse(lis);

        return lis;
    }

    public static void main(String[] args) {
        int[] nums = { 10, 9, 2, 5, 3, 7, 101, 18 };

        System.out.println("LIS length (DP): " + lisDP(nums));
        System.out.println("LIS length (Binary Search): " + lisBinarySearch(nums));
        System.out.println("Actual LIS: " + printLIS(nums));
    }
}
