/**
 * Binary Search Algorithm
 * 
 * Description:
 * Efficiently searches a sorted array by repeatedly dividing the search
 * interval
 * in half. Compares the target with the middle element and eliminates half of
 * the remaining elements.
 * 
 * Time Complexity: O(log n)
 * Space Complexity: O(1) iterative, O(log n) recursive
 * Prerequisites: Array must be sorted
 * 
 * Use Cases:
 * - Sorted arrays
 * - Large datasets with multiple searches
 * - Most common and efficient searching technique
 */

public class BinarySearch {

    /**
     * Iterative binary search
     * 
     * @param arr    sorted array to search in
     * @param target element to find
     * @return index of target if found, -1 otherwise
     */
    public static int binarySearch(int[] arr, int target) {
        int left = 0;
        int right = arr.length - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2; // Prevent overflow

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

    /**
     * Recursive binary search
     * 
     * @param arr    sorted array to search in
     * @param target element to find
     * @param left   starting index
     * @param right  ending index
     * @return index of target if found, -1 otherwise
     */
    public static int binarySearchRecursive(int[] arr, int target, int left, int right) {
        if (left > right) {
            return -1;
        }

        int mid = left + (right - left) / 2;

        if (arr[mid] == target) {
            return mid;
        } else if (arr[mid] < target) {
            return binarySearchRecursive(arr, target, mid + 1, right);
        } else {
            return binarySearchRecursive(arr, target, left, mid - 1);
        }
    }

    public static void main(String[] args) {
        int[] arr = { 2, 5, 8, 12, 16, 23, 38, 45, 56, 67, 78 };
        int target = 23;

        // Test iterative version
        int result = binarySearch(arr, target);
        System.out.println("Iterative: Element " + target +
                (result != -1 ? " found at index " + result : " not found"));

        // Test recursive version
        result = binarySearchRecursive(arr, target, 0, arr.length - 1);
        System.out.println("Recursive: Element " + target +
                (result != -1 ? " found at index " + result : " not found"));
    }
}
