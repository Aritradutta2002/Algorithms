/**
 * Jump Search Algorithm
 * 
 * Description:
 * Searches a sorted array by jumping ahead by fixed steps and doing linear
 * search in the identified block. Optimal jump size is √n.
 * 
 * Time Complexity: O(√n)
 * Space Complexity: O(1)
 * Prerequisites: Array must be sorted
 * 
 * Use Cases:
 * - Sorted arrays where backward jumping is costly
 * - Better than linear search, simpler than binary search
 */

public class JumpSearch {

    /**
     * Searches for target using jump search
     * 
     * @param arr    sorted array to search in
     * @param target element to find
     * @return index of target if found, -1 otherwise
     */
    public static int jumpSearch(int[] arr, int target) {
        int n = arr.length;
        int step = (int) Math.sqrt(n); // Optimal jump size
        int prev = 0;

        // Jump to find the block where element may be present
        while (arr[Math.min(step, n) - 1] < target) {
            prev = step;
            step += (int) Math.sqrt(n);
            if (prev >= n) {
                return -1;
            }
        }

        // Linear search in the identified block
        while (arr[prev] < target) {
            prev++;
            if (prev == Math.min(step, n)) {
                return -1;
            }
        }

        // If element is found
        if (arr[prev] == target) {
            return prev;
        }

        return -1;
    }

    public static void main(String[] args) {
        int[] arr = { 0, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89, 144, 233, 377 };
        int target = 55;

        int index = jumpSearch(arr, target);
        System.out.println("Element " + target +
                (index != -1 ? " found at index " + index : " not found"));
    }
}
