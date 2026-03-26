package ALGORITHMS.TREE_ALGORITHMS;
/**
 * Binary Tree Traversals
 * 
 * Description: In-order, Pre-order, Post-order traversals
 * 
 * Time Complexity: O(n) for each traversal
 * Space Complexity: O(h) where h is height
 */

import java.util.*;

class TreeNode {
    int val;
    TreeNode left, right;

    TreeNode(int val) {
        this.val = val;
    }
}

public class TreeTraversals {

    // In-order: Left -> Root -> Right
    public static void inorder(TreeNode root) {
        if (root == null)
            return;
        inorder(root.left);
        System.out.print(root.val + " ");
        inorder(root.right);
    }

    // Pre-order: Root -> Left -> Right
    public static void preorder(TreeNode root) {
        if (root == null)
            return;
        System.out.print(root.val + " ");
        preorder(root.left);
        preorder(root.right);
    }

    // Post-order: Left -> Right -> Root
    public static void postorder(TreeNode root) {
        if (root == null)
            return;
        postorder(root.left);
        postorder(root.right);
        System.out.print(root.val + " ");
    }

    // Iterative In-order using Stack
    public static void inorderIterative(TreeNode root) {
        Stack<TreeNode> stack = new Stack<>();
        TreeNode curr = root;

        while (curr != null || !stack.isEmpty()) {
            while (curr != null) {
                stack.push(curr);
                curr = curr.left;
            }
            curr = stack.pop();
            System.out.print(curr.val + " ");
            curr = curr.right;
        }
    }

    public static void main(String[] args) {
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.left = new TreeNode(4);
        root.left.right = new TreeNode(5);

        System.out.print("In-order: ");
        inorder(root);
        System.out.println();

        System.out.print("Pre-order: ");
        preorder(root);
        System.out.println();

        System.out.print("Post-order: ");
        postorder(root);
        System.out.println();

        System.out.print("In-order (Iterative): ");
        inorderIterative(root);
        System.out.println();
    }
}
