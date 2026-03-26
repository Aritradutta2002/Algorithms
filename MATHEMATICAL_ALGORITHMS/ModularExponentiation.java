package ALGORITHMS.MATHEMATICAL_ALGORITHMS;

/**
 * Modular Exponentiation - Fast Power with Modulo
 * 
 * Description: Computes (base^exp) % mod efficiently
 * 
 * Time Complexity: O(log n)
 * Space Complexity: O(1) iterative, O(log n) recursive
 */

public class ModularExponentiation {

    // Iterative implementation
    public static long powerMod(long base, long exp, long mod) {
        long result = 1;
        base %= mod;

        while (exp > 0) {
            if (exp % 2 == 1) {
                result = (result * base) % mod;
            }
            base = (base * base) % mod;
            exp /= 2;
        }

        return result;
    }

    // Recursive implementation
    public static long powerModRecursive(long base, long exp, long mod) {
        if (exp == 0)
            return 1;

        base %= mod;
        long half = powerModRecursive(base, exp / 2, mod);
        half = (half * half) % mod;

        if (exp % 2 == 1) {
            half = (half * base) % mod;
        }

        return half;
    }

    public static void main(String[] args) {
        long base = 2, exp = 10, mod = 1000000007;

        System.out.println(base + "^" + exp + " mod " + mod + " [Iterative] = " +
                powerMod(base, exp, mod));
        System.out.println(base + "^" + exp + " mod " + mod + " [Recursive] = " +
                powerModRecursive(base, exp, mod));
    }
}
