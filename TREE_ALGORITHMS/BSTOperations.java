package ALGORITHMS.TREE_ALGORITHMS;
/**
 * Binary Search Tree Operations
 * 
 * Operations: Insertion, Deletion, Search
 * 
 * Time Complexity: O(h) where h is height
 * - Best case (balanced): O(log n)
 * - Worst case (skewed): O(n)
 */


public class BSTOperations {

    // Insert a value into BST
    public static TreeNode insert(TreeNode root, int val) {
        if (root == null) {
            return new TreeNode(val);
        }

        if (val < root.val) {
            root.left = insert(root.left, val);
        } else if (val > root.val) {
            root.right = insert(root.right, val);
        }

        return root;
    }

    // Search for a value in BST
    public static boolean search(TreeNode root, int val) {
        if (root == null) {
            return false;
        }

        if (val == root.val) {
            return true;
        } else if (val < root.val) {
            return search(root.left, val);
        } else {
            return search(root.right, val);
        }
    }

    // Delete a value from BST
    public static TreeNode delete(TreeNode root, int val) {
        if (root == null) {
            return null;
        }

        if (val < root.val) {
            root.left = delete(root.left, val);
        } else if (val > root.val) {
            root.right = delete(root.right, val);
        } else {
            // Node to be deleted found

            // Case 1: Leaf node or only one child
            if (root.left == null) {
                return root.right;
            } else if (root.right == null) {
                return root.left;
            }

            // Case 2: Node with two children
            // Find inorder successor (smallest in right subtree)
            TreeNode successor = findMin(root.right);
            root.val = successor.val;
            root.right = delete(root.right, successor.val);
        }

        return root;
    }

    // Find minimum value node
    private static TreeNode findMin(TreeNode root) {
        while (root.left != null) {
            root = root.left;
        }
        return root;
    }

    // Find maximum value node
    public static TreeNode findMax(TreeNode root) {
        while (root.right != null) {
            root = root.right;
        }
        return root;
    }

    // Inorder traversal (sorted order)
    public static void inorder(TreeNode root) {
        if (root != null) {
            inorder(root.left);
            System.out.print(root.val + " ");
            inorder(root.right);
        }
    }

    // Check if tree is valid BST
    public static boolean isValidBST(TreeNode root) {
        return isValidBSTHelper(root, Long.MIN_VALUE, Long.MAX_VALUE);
    }

    private static boolean isValidBSTHelper(TreeNode root, long min, long max) {
        if (root == null)
            return true;

        if (root.val <= min || root.val >= max)
            return false;

        return isValidBSTHelper(root.left, min, root.val) &&
                isValidBSTHelper(root.right, root.val, max);
    }

    public static void main(String[] args) {
        TreeNode root = null;

        // Insert nodes
        int[] values = { 50, 30, 70, 20, 40, 60, 80 };
        for (int val : values) {
            root = insert(root, val);
        }

        System.out.println("BST (Inorder):");
        inorder(root);
        System.out.println();

        // Search
        System.out.println("\nSearch 40: " + search(root, 40));
        System.out.println("Search 100: " + search(root, 100));

        // Delete
        System.out.println("\nDeleting 20:");
        root = delete(root, 20);
        inorder(root);
        System.out.println();

        System.out.println("\nDeleting 30:");
        root = delete(root, 30);
        inorder(root);
        System.out.println();

        System.out.println("\nDeleting 50:");
        root = delete(root, 50);
        inorder(root);
        System.out.println();

        System.out.println("\nIs valid BST: " + isValidBST(root));
    }
}
