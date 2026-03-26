/**
 * Quick Sort Algorithm
 * 
 * Description:
 * A divide-and-conquer algorithm that picks a pivot element and partitions
 * the array around it, then recursively sorts the partitions.
 * 
 * Time Complexity:
 * - Best Case: O(n log n) - balanced partitions
 * - Average Case: O(n log n)
 * - Worst Case: O(n²) - already sorted array with poor pivot choice
 * 
 * Space Complexity: O(log n) - recursion stack space
 * 
 * Stability: Not stable (can change relative order of equal elements)
 * 
 * Use Cases:
 * - General purpose sorting (one of the fastest in practice)
 * - In-place sorting with good cache locality
 * - When average case performance is more important than worst case
 */

public class QuickSort {

    /**
     * Sorts an array using quick sort algorithm
     * 
     * @param arr the array to be sorted
     */
    public static void quickSort(int[] arr) {
        if (arr == null || arr.length <= 1) {
            return;
        }
        quickSortHelper(arr, 0, arr.length - 1);
    }

    /**
     * Recursive helper method for quick sort
     */
    private static void quickSortHelper(int[] arr, int low, int high) {
        if (low < high) {
            // Partition the array and get pivot index
            int pivotIndex = partition(arr, low, high);

            // Recursively sort elements before and after partition
            quickSortHelper(arr, low, pivotIndex - 1);
            quickSortHelper(arr, pivotIndex + 1, high);
        }
    }

    /**
     * Partitions the array around a pivot
     * Uses last element as pivot
     */
    private static int partition(int[] arr, int low, int high) {
        int pivot = arr[high];
        int i = low - 1; // Index of smaller element

        for (int j = low; j < high; j++) {
            // If current element is smaller than or equal to pivot
            if (arr[j] <= pivot) {
                i++;
                swap(arr, i, j);
            }
        }

        // Place pivot in correct position
        swap(arr, i + 1, high);
        return i + 1;
    }

    /**
     * Helper method to swap two elements in an array
     */
    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
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

    // Test the quick sort implementation
    public static void main(String[] args) {
        int[] arr = { 10, 7, 8, 9, 1, 5 };

        System.out.println("Original array:");
        printArray(arr);

        quickSort(arr);

        System.out.println("Sorted array:");
        printArray(arr);

        // Test with larger array
        int[] largeArr = { 64, 34, 25, 12, 22, 11, 90, 88 };
        System.out.println("\nLarge array:");
        printArray(largeArr);
        quickSort(largeArr);
        System.out.println("After quick sort:");
        printArray(largeArr);

        // Test with negative numbers
        int[] negArr = { -5, 20, 10, -40, 30 };
        System.out.println("\nArray with negative numbers:");
        printArray(negArr);
        quickSort(negArr);
        System.out.println("After quick sort:");
        printArray(negArr);

        // Test with duplicates
        int[] dupArr = { 5, 2, 3, 2, 1, 3, 5 };
        System.out.println("\nArray with duplicates:");
        printArray(dupArr);
        quickSort(dupArr);
        System.out.println("After quick sort:");
        printArray(dupArr);
    }
}
