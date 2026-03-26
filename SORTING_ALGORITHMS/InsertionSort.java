/**
 * Insertion Sort Algorithm
 * 
 * Description:
 * Builds the final sorted array one item at a time. Takes each element from
 * the unsorted part and inserts it into its correct position in the sorted
 * part.
 * 
 * Time Complexity:
 * - Best Case: O(n) - when array is already sorted
 * - Average Case: O(n²)
 * - Worst Case: O(n²) - when array is reverse sorted
 * 
 * Space Complexity: O(1) - in-place sorting
 * 
 * Stability: Stable (maintains relative order of equal elements)
 * 
 * Use Cases:
 * - Small datasets
 * - Nearly sorted data (performs very well)
 * - Online algorithm (can sort as data arrives)
 * - When simplicity and stability are important
 */

public class InsertionSort {

    /**
     * Sorts an array using insertion sort algorithm
     * 
     * @param arr the array to be sorted
     */
    public static void insertionSort(int[] arr) {
        int n = arr.length;

        // Start from the second element (index 1)
        for (int i = 1; i < n; i++) {
            int key = arr[i];
            int j = i - 1;

            // Move elements greater than key one position ahead
            while (j >= 0 && arr[j] > key) {
                arr[j + 1] = arr[j];
                j--;
            }

            // Insert the key at correct position
            arr[j + 1] = key;
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

    // Test the insertion sort implementation
    public static void main(String[] args) {
        int[] arr = { 12, 11, 13, 5, 6 };

        System.out.println("Original array:");
        printArray(arr);

        insertionSort(arr);

        System.out.println("Sorted array:");
        printArray(arr);

        // Test with nearly sorted array (best case)
        int[] nearlySorted = { 1, 2, 3, 5, 4, 6, 7 };
        System.out.println("\nNearly sorted array:");
        printArray(nearlySorted);
        insertionSort(nearlySorted);
        System.out.println("After insertion sort:");
        printArray(nearlySorted);

        // Test with reverse sorted array (worst case)
        int[] reverseArr = { 9, 8, 7, 6, 5, 4, 3, 2, 1 };
        System.out.println("\nReverse sorted array:");
        printArray(reverseArr);
        insertionSort(reverseArr);
        System.out.println("After insertion sort:");
        printArray(reverseArr);

        // Test with single element
        int[] singleElement = { 42 };
        System.out.println("\nSingle element array:");
        printArray(singleElement);
        insertionSort(singleElement);
        System.out.println("After insertion sort:");
        printArray(singleElement);
    }
}
