/**
 * Bubble Sort Algorithm
 * 
 * Description:
 * Repeatedly steps through the list, compares adjacent elements and swaps them
 * if they are in the wrong order. The pass through continues until no swaps are
 * needed.
 * 
 * Time Complexity:
 * - Best Case: O(n) - when array is already sorted
 * - Average Case: O(n²)
 * - Worst Case: O(n²)
 * 
 * Space Complexity: O(1) - in-place sorting
 * 
 * Stability: Stable (maintains relative order of equal elements)
 * 
 * Use Cases:
 * - Small datasets
 * - Nearly sorted data
 * - Educational purposes
 */

public class BubbleSort {

    /**
     * Sorts an array using bubble sort algorithm
     * 
     * @param arr the array to be sorted
     */
    public static void bubbleSort(int[] arr) {
        int n = arr.length;
        boolean swapped;

        // Outer loop for each pass
        for (int i = 0; i < n - 1; i++) {
            swapped = false;

            // Inner loop for comparing adjacent elements
            for (int j = 0; j < n - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    // Swap arr[j] and arr[j+1]
                    swap(arr, j, j + 1);
                    swapped = true;
                }
            }

            // If no swapping happened, array is already sorted
            if (!swapped) {
                break;
            }
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

    // Test the bubble sort implementation
    public static void main(String[] args) {
        int[] arr = { 64, 34, 25, 12, 22, 11, 90 };

        System.out.println("Original array:");
        printArray(arr);

        bubbleSort(arr);

        System.out.println("Sorted array:");
        printArray(arr);

        // Test with already sorted array
        int[] sortedArr = { 1, 2, 3, 4, 5 };
        System.out.println("\nAlready sorted array:");
        printArray(sortedArr);
        bubbleSort(sortedArr);
        System.out.println("After bubble sort:");
        printArray(sortedArr);

        // Test with reverse sorted array
        int[] reverseArr = { 5, 4, 3, 2, 1 };
        System.out.println("\nReverse sorted array:");
        printArray(reverseArr);
        bubbleSort(reverseArr);
        System.out.println("After bubble sort:");
        printArray(reverseArr);
    }
}
