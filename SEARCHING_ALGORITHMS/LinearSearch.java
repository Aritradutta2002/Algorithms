/**
 * Linear Search Algorithm
 * 
 * Description:
 * Sequentially checks each element in the array until the target is found or
 * the end of the array is reached.
 * 
 * Time Complexity: O(n) - checks each element once
 * Space Complexity: O(1) - no extra space needed
 * 
 * Use Cases:
 * - Unsorted arrays
 * - Small datasets
 * - Single search operation
 */

public class LinearSearch {

    /**
     * Searches for target in array using linear search
     * 
     * @param arr    the array to search in
     * @param target the element to find
     * @return index of target if found, -1 otherwise
     */
    public static int linearSearch(int[] arr, int target) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == target) {
                return i;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        int[] arr = { 64, 34, 25, 12, 22, 11, 90 };
        int target = 22;

        int result = linearSearch(arr, target);
        if (result != -1) {
            System.out.println("Element " + target + " found at index " + result);
        } else {
            System.out.println("Element " + target + " not found");
        }
    }
}
