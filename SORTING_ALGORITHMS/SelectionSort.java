/**
 * Selection Sort Algorithm
 * 
 * Description:
 * Divides the array into sorted and unsorted regions. Repeatedly selects the
 * smallest (or largest) element from the unsorted region and moves it to the
 * end of the sorted region.
 * 
 * Time Complexity:
 * - Best Case: O(n²)
 * - Average Case: O(n²)
 * - Worst Case: O(n²)
 * 
 * Space Complexity: O(1) - in-place sorting
 * 
 * Stability: Not stable (can change relative order of equal elements)
 * 
 * Use Cases:
 * - When memory writes are expensive (minimum number of swaps)
 * - Small datasets
 * - When simplicity is preferred over efficiency
 */

public class SelectionSort {

    /**
     * Sorts an array using selection sort algorithm
     * 
     * @param arr the array to be sorted
     */
    public static void selectionSort(int[] arr) {
        int n = arr.length;

        // Move boundary of unsorted subarray one by one
        for (int i = 0; i < n - 1; i++) {
            // Find the minimum element in unsorted array
            int minIndex = i;

            for (int j = i + 1; j < n; j++) {
                if (arr[j] < arr[minIndex]) {
                    minIndex = j;
                }
            }

            // Swap the found minimum element with the first element
            if (minIndex != i) {
                swap(arr, i, minIndex);
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

    // Test the selection sort implementation
    public static void main(String[] args) {
        int[] arr = { 64, 25, 12, 22, 11 };

        System.out.println("Original array:");
        printArray(arr);

        selectionSort(arr);

        System.out.println("Sorted array:");
        printArray(arr);

        // Test with negative numbers
        int[] negArr = { -5, 20, 10, -40, 30 };
        System.out.println("\nArray with negative numbers:");
        printArray(negArr);
        selectionSort(negArr);
        System.out.println("After selection sort:");
        printArray(negArr);

        // Test with duplicates
        int[] dupArr = { 3, 5, 3, 1, 5, 2 };
        System.out.println("\nArray with duplicates:");
        printArray(dupArr);
        selectionSort(dupArr);
        System.out.println("After selection sort:");
        printArray(dupArr);
    }
}
