/**
 * Exponential Search Algorithm
 * 
 * Description:
 * Finds range where element may be present by repeated doubling, then performs
 * binary search in that range. Useful for unbounded/infinite arrays.
 * 
 * Time Complexity: O(log n)
 * Space Complexity: O(1)
 * Prerequisites: Sorted array
 * 
 * Use Cases:
 * - Unbounded or infinite sorted arrays
 * - When element is closer to the beginning
 */

public class ExponentialSearch {

    /**
     * Searches for target using exponential search
     * 
     * @param arr    sorted array to search in
     * @param target element to find
     * @return index of target if found, -1 otherwise
     */
    public static int exponentialSearch(int[] arr, int target) {
        int n = arr.length;

        // If element is at first position
        if (arr[0] == target) {
            return 0;
        }

        // Find range for binary search by repeated doubling
        int i = 1;
        while (i < n && arr[i] <= target) {
            i *= 2;
        }

        // Perform binary search in the found range
        return binarySearch(arr, target, i / 2, Math.min(i, n - 1));
    }

    /**
     * Binary search helper
     */
    private static int binarySearch(int[] arr, int target, int left, int right) {
        while (left <= right) {
            int mid = left + (right - left) / 2;

            if (arr[mid] == target) {
                return mid;
            } else if (arr[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        int[] arr = { 2, 3, 4, 10, 40, 50, 60, 70, 80, 90 };
        int target = 10;

        int index = exponentialSearch(arr, target);
        System.out.println("Element " + target +
                (index != -1 ? " found at index " + index : " not found"));
    }
}
