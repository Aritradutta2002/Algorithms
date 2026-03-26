/**
 * Ternary Search Algorithm
 * 
 * Description:
 * Divides array into three parts and determines which segment to search next.
 * Primarily used for finding maximum/minimum in unimodal functions.
 * 
 * Time Complexity: O(log₃ n)
 * Space Complexity: O(1)
 * Prerequisites: Unimodal function or sorted array
 * 
 * Use Cases:
 * - Finding maximum/minimum in unimodal functions
 * - Optimization problems
 */

public class TernarySearch {

    /**
     * Ternary search for sorted arrays
     * 
     * @param arr    sorted array to search in
     * @param target element to find
     * @return index of target if found, -1 otherwise
     */
    public static int ternarySearch(int[] arr, int target) {
        return ternarySearchHelper(arr, target, 0, arr.length - 1);
    }

    /**
     * Recursive helper for ternary search
     */
    private static int ternarySearchHelper(int[] arr, int target, int left, int right) {
        if (left > right) {
            return -1;
        }

        // Divide array into three parts
        int mid1 = left + (right - left) / 3;
        int mid2 = right - (right - left) / 3;

        if (arr[mid1] == target) {
            return mid1;
        }
        if (arr[mid2] == target) {
            return mid2;
        }

        // Determine which third to search
        if (target < arr[mid1]) {
            return ternarySearchHelper(arr, target, left, mid1 - 1);
        } else if (target > arr[mid2]) {
            return ternarySearchHelper(arr, target, mid2 + 1, right);
        } else {
            return ternarySearchHelper(arr, target, mid1 + 1, mid2 - 1);
        }
    }

    /**
     * Ternary search for finding maximum in unimodal function
     * 
     * @param left    left boundary
     * @param right   right boundary
     * @param epsilon precision
     * @return x value where function is maximum
     */
    public static double ternarySearchMax(double left, double right, double epsilon) {
        while (right - left > epsilon) {
            double mid1 = left + (right - left) / 3;
            double mid2 = right - (right - left) / 3;

            if (f(mid1) < f(mid2)) {
                left = mid1;
            } else {
                right = mid2;
            }
        }
        return (left + right) / 2;
    }

    /**
     * Example unimodal function: f(x) = -(x-5)^2 + 25
     */
    private static double f(double x) {
        return -(x - 5) * (x - 5) + 25;
    }

    public static void main(String[] args) {
        // Test array search
        int[] arr = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
        int target = 7;

        int index = ternarySearch(arr, target);
        System.out.println("Element " + target +
                (index != -1 ? " found at index " + index : " not found"));

        // Test maximum finding
        double maxX = ternarySearchMax(0, 10, 1e-6);
        System.out.println("Maximum of f(x) occurs at x = " + maxX);
    }
}
