/**
 * Counting Sort Algorithm
 * 
 * Description:
 * A non-comparison based sorting algorithm that counts the occurrences of each
 * distinct element and uses this information to place elements in sorted order.
 * 
 * Time Complexity:
 * - Best Case: O(n + k) where k is the range of input
 * - Average Case: O(n + k)
 * - Worst Case: O(n + k)
 * 
 * Space Complexity: O(k) - requires auxiliary array of size k
 * 
 * Stability: Stable (maintains relative order of equal elements)
 * 
 * Use Cases:
 * - Integers within a limited range
 * - Linear time sorting when range is not significantly larger than n
 * - As a subroutine in Radix Sort
 * 
 * Limitations:
 * - Only works for non-negative integers
 * - Space complexity depends on range of values
 */

public class CountingSort {

    /**
     * Sorts an array of non-negative integers using counting sort
     * 
     * @param arr the array to be sorted
     */
    public static void countingSort(int[] arr) {
        if (arr == null || arr.length <= 1) {
            return;
        }

        // Find the maximum element to determine range
        int max = findMax(arr);

        // Count array to store count of each element
        int[] count = new int[max + 1];

        // Store count of each element
        for (int num : arr) {
            count[num]++;
        }

        // Modify count array to contain actual positions
        for (int i = 1; i <= max; i++) {
            count[i] += count[i - 1];
        }

        // Build output array
        int[] output = new int[arr.length];
        for (int i = arr.length - 1; i >= 0; i--) {
            output[count[arr[i]] - 1] = arr[i];
            count[arr[i]]--;
        }

        // Copy output array to original array
        System.arraycopy(output, 0, arr, 0, arr.length);
    }

    /**
     * Alternative simpler implementation (not stable)
     * 
     * @param arr the array to be sorted
     */
    public static void countingSortSimple(int[] arr) {
        if (arr == null || arr.length <= 1) {
            return;
        }

        int max = findMax(arr);
        int[] count = new int[max + 1];

        // Count occurrences
        for (int num : arr) {
            count[num]++;
        }

        // Rebuild array
        int index = 0;
        for (int i = 0; i <= max; i++) {
            while (count[i] > 0) {
                arr[index++] = i;
                count[i]--;
            }
        }
    }

    /**
     * Finds maximum element in array
     */
    private static int findMax(int[] arr) {
        int max = arr[0];
        for (int num : arr) {
            if (num > max) {
                max = num;
            }
        }
        return max;
    }

    /**
     * Prints the array
     */
    private static void printArray(int[] arr) {
        for (int num : arr) {
            System.out.print(num + " ");
        }
        System.out.println();
    }

    // Test the counting sort implementation
    public static void main(String[] args) {
        int[] arr = { 4, 2, 2, 8, 3, 3, 1 };

        System.out.println("Original array:");
        printArray(arr);

        countingSort(arr);

        System.out.println("Sorted array (stable):");
        printArray(arr);

        // Test simple version
        int[] arr2 = { 4, 2, 2, 8, 3, 3, 1 };
        System.out.println("\nOriginal array:");
        printArray(arr2);
        countingSortSimple(arr2);
        System.out.println("Sorted array (simple):");
        printArray(arr2);

        // Test with larger range
        int[] largeRange = { 100, 50, 20, 30, 90, 10, 60 };
        System.out.println("\nArray with larger range:");
        printArray(largeRange);
        countingSort(largeRange);
        System.out.println("After counting sort:");
        printArray(largeRange);

        // Test with duplicates
        int[] dupArr = { 1, 1, 1, 2, 2, 3, 3, 3, 3 };
        System.out.println("\nArray with many duplicates:");
        printArray(dupArr);
        countingSort(dupArr);
        System.out.println("After counting sort:");
        printArray(dupArr);
    }
}
