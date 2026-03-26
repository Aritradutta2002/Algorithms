/**
 * Interpolation Search Algorithm
 * 
 * Description:
 * An improvement over binary search for uniformly distributed sorted arrays.
 * Uses interpolation formula to estimate the position of the target.
 * 
 * Time Complexity: O(log log n) average, O(n) worst case
 * Space Complexity: O(1)
 * Prerequisites: Sorted array with uniform distribution
 * 
 * Use Cases:
 * - Uniformly distributed sorted data
 * - Better than binary search in best case
 */

public class InterpolationSearch {

    /**
     * Searches for target using interpolation search
     * 
     * @param arr    sorted array to search in
     * @param target element to find
     * @return index of target if found, -1 otherwise
     */
    public static int interpolationSearch(int[] arr, int target) {
        int low = 0;
        int high = arr.length - 1;

        while (low <= high && target >= arr[low] && target <= arr[high]) {
            // If array has single element
            if (low == high) {
                if (arr[low] == target)
                    return low;
                return -1;
            }

            // Interpolation formula to find probable position
            int pos = low + ((target - arr[low]) * (high - low)) /
                    (arr[high] - arr[low]);

            if (arr[pos] == target) {
                return pos;
            }

            if (arr[pos] < target) {
                low = pos + 1;
            } else {
                high = pos - 1;
            }
        }

        return -1;
    }

    public static void main(String[] args) {
        int[] arr = { 10, 20, 30, 40, 50, 60, 70, 80, 90, 100 };
        int target = 70;

        int index = interpolationSearch(arr, target);
        System.out.println("Element " + target +
                (index != -1 ? " found at index " + index : " not found"));
    }
}
