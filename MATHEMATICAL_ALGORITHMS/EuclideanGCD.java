package ALGORITHMS.MATHEMATICAL_ALGORITHMS;

/**
 * Euclidean Algorithm - GCD Calculation
 * Description: Finds Greatest Common Divisor of two numbers
 * Time Complexity: O(log min(a, b))
 * Space Complexity: O(1) iterative, O(log min(a,b)) recursive
 */

public class EuclideanGCD {

    // Recursive implementation
    public static int gcdRecursive(int a, int b) {
        if (b == 0)
            return a;
        return gcdRecursive(b, a % b);
    }

    // Iterative implementation
    public static int gcdIterative(int a, int b) {
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }

    // LCM using GCD
    public static int lcm(int a, int b) {
        return (a * b) / gcdIterative(a, b);
    }

    public static void main(String[] args) {
        int a = 48, b = 18;

        System.out.println("GCD(" + a + ", " + b + ") [Recursive] = " + gcdRecursive(a, b));
        System.out.println("GCD(" + a + ", " + b + ") [Iterative] = " + gcdIterative(a, b));
        System.out.println("LCM(" + a + ", " + b + ") = " + lcm(a, b));
    }
}
