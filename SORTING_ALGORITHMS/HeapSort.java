/**
 * Heap Sort Algorithm
 * 
 * Description:
 * A comparison-based sorting algorithm that uses a binary heap data structure.
 * Builds a max heap and repeatedly extracts the maximum element.
 * 
 * Time Complexity:
 * - Best Case: O(n log n)
 * - Average Case: O(n log n)
 * - Worst Case: O(n log n)
 * 
 * Space Complexity: O(1) - in-place sorting
 * 
 * Stability: Not stable (can change relative order of equal elements)
 * 
 * Use Cases:
 * - Guaranteed O(n log n) performance
 * - In-place sorting with no extra space
 * - Priority queue applications
 * - When consistent performance is needed
 */

public class HeapSort {

    /**
     * Sorts an array using heap sort algorithm
     * 
     * @param arr the array to be sorted
     */
    public static void heapSort(int[] arr) {
        int n = arr.length;

        // Build max heap (rearrange array)
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(arr, n, i);
        }

        // Extract elements from heap one by one
        for (int i = n - 1; i > 0; i--) {
            // Move current root to end
            swap(arr, 0, i);

            // Heapify the reduced heap
            heapify(arr, i, 0);
        }
    }

    /**
     * Maintains heap property for a subtree rooted at index i
     * 
     * @param arr the array representing the heap
     * @param n   size of heap
     * @param i   root index of subtree
     */
    private static void heapify(int[] arr, int n, int i) {
        int largest = i; // Initialize largest as root
        int left = 2 * i + 1; // Left child
        int right = 2 * i + 2; // Right child

        // If left child is larger than root
        if (left < n && arr[left] > arr[largest]) {
            largest = left;
        }

        // If right child is larger than largest so far
        if (right < n && arr[right] > arr[largest]) {
            largest = right;
        }

        // If largest is not root
        if (largest != i) {
            swap(arr, i, largest);

            // Recursively heapify the affected sub-tree
            heapify(arr, n, largest);
        }
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

    // Test the heap sort implementation
    public static void main(String[] args) {
        int[] arr = { 12, 11, 13, 5, 6, 7 };

        System.out.println("Original array:");
        printArray(arr);

        heapSort(arr);

        System.out.println("Sorted array:");
        printArray(arr);

        // Test with large array
        int[] largeArr = { 64, 34, 25, 12, 22, 11, 90, 88, 45, 50 };
        System.out.println("\nLarge array:");
        printArray(largeArr);
        heapSort(largeArr);
        System.out.println("After heap sort:");
        printArray(largeArr);

        // Test with negative numbers
        int[] negArr = { -5, 20, 10, -40, 30, 0 };
        System.out.println("\nArray with negative numbers:");
        printArray(negArr);
        heapSort(negArr);
        System.out.println("After heap sort:");
        printArray(negArr);

        // Test with already sorted
        int[] sortedArr = { 1, 2, 3, 4, 5 };
        System.out.println("\nAlready sorted array:");
        printArray(sortedArr);
        heapSort(sortedArr);
        System.out.println("After heap sort:");
        printArray(sortedArr);
    }
}
