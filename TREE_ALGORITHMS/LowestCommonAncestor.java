package ALGORITHMS.TREE_ALGORITHMS;

/**
 * Lowest Common Ancestor (LCA) in Binary Tree
 * 
 * Problem: Find LCA of two nodes in a binary tree
 * 
 * Time Complexity: O(n)
 * Space Complexity: O(h) recursion stack
 */

public class LowestCommonAncestor {

    // LCA in Binary Tree
    public static TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null || root == p || root == q) {
            return root;
        }

        TreeNode left = lowestCommonAncestor(root.left, p, q);
        TreeNode right = lowestCommonAncestor(root.right, p, q);

        if (left != null && right != null) {
            return root; // Current node is LCA
        }

        return left != null ? left : right;
    }

    // LCA in Binary Search Tree (more efficient)
    public static TreeNode lowestCommonAncestorBST(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null)
            return null;

        // If both p and q are smaller, LCA is in left subtree
        if (p.val < root.val && q.val < root.val) {
            return lowestCommonAncestorBST(root.left, p, q);
        }

        // If both p and q are greater, LCA is in right subtree
        if (p.val > root.val && q.val > root.val) {
            return lowestCommonAncestorBST(root.right, p, q);
        }

        // Otherwise, current node is LCA
        return root;
    }

    // Iterative version for BST
    public static TreeNode lowestCommonAncestorBSTIterative(TreeNode root, TreeNode p, TreeNode q) {
        while (root != null) {
            if (p.val < root.val && q.val < root.val) {
                root = root.left;
            } else if (p.val > root.val && q.val > root.val) {
                root = root.right;
            } else {
                return root;
            }
        }
        return null;
    }

    // Helper to find a node
    private static TreeNode findNode(TreeNode root, int val) {
        if (root == null || root.val == val)
            return root;

        TreeNode left = findNode(root.left, val);
        if (left != null)
            return left;

        return findNode(root.right, val);
    }

    public static void main(String[] args) {
        // Create binary tree
        TreeNode root = new TreeNode(3);
        root.left = new TreeNode(5);
        root.right = new TreeNode(1);
        root.left.left = new TreeNode(6);
        root.left.right = new TreeNode(2);
        root.right.left = new TreeNode(0);
        root.right.right = new TreeNode(8);
        root.left.right.left = new TreeNode(7);
        root.left.right.right = new TreeNode(4);

        TreeNode p = root.left; // Node 5
        TreeNode q = root.left.right.right; // Node 4

        TreeNode lca = lowestCommonAncestor(root, p, q);
        System.out.println("LCA of " + p.val + " and " + q.val + " is: " + lca.val);

        // Test BST LCA
        TreeNode bstRoot = new TreeNode(6);
        bstRoot.left = new TreeNode(2);
        bstRoot.right = new TreeNode(8);
        bstRoot.left.left = new TreeNode(0);
        bstRoot.left.right = new TreeNode(4);
        bstRoot.right.left = new TreeNode(7);
        bstRoot.right.right = new TreeNode(9);

        TreeNode bstP = bstRoot.left; // Node 2
        TreeNode bstQ = bstRoot.left.right; // Node 4

        TreeNode bstLca = lowestCommonAncestorBST(bstRoot, bstP, bstQ);
        System.out.println("\nBST LCA of " + bstP.val + " and " + bstQ.val + " is: " + bstLca.val);
    }
}
