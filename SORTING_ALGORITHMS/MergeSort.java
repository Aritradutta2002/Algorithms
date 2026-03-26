/**
 * Merge Sort Algorithm
 * 
 * Description:
 * A divide-and-conquer algorithm that divides the array into two halves,
 * recursively sorts them, and then merges the two sorted halves.
 * 
 * Time Complexity:
 * - Best Case: O(n log n)
 * - Average Case: O(n log n)
 * - Worst Case: O(n log n)
 * 
 * Space Complexity: O(n) - requires additional space for merging
 * 
 * Stability: Stable (maintains relative order of equal elements)
 * 
 * Use Cases:
 * - Guaranteed O(n log n) performance
 * - Sorting linked lists
 * - External sorting (large files that don't fit in memory)
 * - When stable sorting is required
 */

public class MergeSort {

    /**
     * Sorts an array using merge sort algorithm
     * 
     * @param arr the array to be sorted
     */
    public static void mergeSort(int[] arr) {
        if (arr == null || arr.length <= 1) {
            return;
        }

        int[] temp = new int[arr.length];
        mergeSortHelper(arr, temp, 0, arr.length - 1);
    }

    /**
     * Recursive helper method for merge sort
     */
    private static void mergeSortHelper(int[] arr, int[] temp, int left, int right) {
        if (left < right) {
            int mid = left + (right - left) / 2;

            // Sort first and second halves
            mergeSortHelper(arr, temp, left, mid);
            mergeSortHelper(arr, temp, mid + 1, right);

            // Merge the sorted halves
            merge(arr, temp, left, mid, right);
        }
    }

    /**
     * Merges two sorted subarrays
     */
    private static void merge(int[] arr, int[] temp, int left, int mid, int right) {
        // Copy data to temp array
        for (int i = left; i <= right; i++) {
            temp[i] = arr[i];
        }

        int i = left; // Initial index of left subarray
        int j = mid + 1; // Initial index of right subarray
        int k = left; // Initial index of merged array

        // Merge temp arrays back into arr
        while (i <= mid && j <= right) {
            if (temp[i] <= temp[j]) {
                arr[k++] = temp[i++];
            } else {
                arr[k++] = temp[j++];
            }
        }

        // Copy remaining elements of left subarray
        while (i <= mid) {
            arr[k++] = temp[i++];
        }

        // Copy remaining elements of right subarray (if any)
        while (j <= right) {
            arr[k++] = temp[j++];
        }
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

    // Test the merge sort implementation
    public static void main(String[] args) {
        int[] arr = { 38, 27, 43, 3, 9, 82, 10 };

        System.out.println("Original array:");
        printArray(arr);

        mergeSort(arr);

        System.out.println("Sorted array:");
        printArray(arr);

        // Test with large array
        int[] largeArr = { 64, 34, 25, 12, 22, 11, 90, 88, 45, 50, 32, 19 };
        System.out.println("\nLarge array:");
        printArray(largeArr);
        mergeSort(largeArr);
        System.out.println("After merge sort:");
        printArray(largeArr);

        // Test with negative numbers
        int[] negArr = { -5, 20, 10, -40, 30, 0, -10 };
        System.out.println("\nArray with negative numbers:");
        printArray(negArr);
        mergeSort(negArr);
        System.out.println("After merge sort:");
        printArray(negArr);

        // Test stability with duplicates
        int[] dupArr = { 5, 2, 3, 2, 1, 3 };
        System.out.println("\nArray with duplicates:");
        printArray(dupArr);
        mergeSort(dupArr);
        System.out.println("After merge sort:");
        printArray(dupArr);
    }
}
