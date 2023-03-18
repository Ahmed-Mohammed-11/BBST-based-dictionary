import java.util.*;

abstract class Tree<Any extends Comparable<Any>> {
    // Root node pointer. Will be null for an empty tree.
    class Node {
        Node left;
        Node right;
        int balanceFactor;
        int height;
        Any key;

        // constructor for the Node
        Node(Any key) {
            this.key = key;
            this.height = 0;
        }
    }

    // initializing the root node
    protected Node root;
    protected int size = 0;
    // list to store the tree in inorder traversal (testing purposes)
    protected List<Any> tree = new ArrayList<Any>();

    // returns the height of the tree
    public int height() {
        if (root == null)
            return -1;
        return root.height;
    }

    // returns number of nodes in the tree
    public int size() {
        return size;
    }

    public boolean search(Any key) {
        return search(root, key);
    }

    protected boolean search(Node node, Any key) {
        // if the node is null, return false not found as the end of tree reached
        if (node == null)
            return false;

        // compare the given key with the current node's key
        int comparisonResult = key.compareTo(node.key);

        // if the key is less than the node's key, try to find it in the left subtree
        if (comparisonResult < 0)
            return search(node.left, key);

        // if the key is greater than the node's key, try to find it in the right
        // subtree
        else if (comparisonResult > 0)
            return search(node.right, key);

        // if the key is equal to the node's key, return true as the key is found
        else
            return true;
    }

    // to be overridden by the subclasses
    abstract protected void update(Node node);

    abstract public boolean insert(Any key);

    abstract public boolean delete(Any key);

    abstract protected Node insert(Node node, Any key);

    abstract protected Node delete(Node node, Any key);

    // the right right case
    protected Node rotateLeft(Node node) {
        Node temp = node.right;
        node.right = temp.left;
        temp.left = node;
        return temp;
    }

    // the left left case
    protected Node rotateRight(Node node) {
        Node temp = node.left;
        node.left = temp.right;
        temp.right = node;
        return temp;
    }

    // the left right case
    protected Node rotateLeftRight(Node node) {
        node.left = rotateLeft(node.left);
        return rotateRight(node);
    }

    // the right left case
    protected Node rotateRightLeft(Node node) {
        node.right = rotateRight(node.right);
        return rotateLeft(node);
    }

    // just driver function for the printTree(Node node) function to give the client
    // interface to print the tree
    void printTree() {
        // clear the tree list before printing the tree to avoid duplicates
        if (tree.size() > 0) {
            tree.clear();
        }
        System.out.println(printTree(root));
    }

    // prints the tree in sorted order (inorder traversal)
    List<Any> printTree(Node node) {
        if (node != null) {
            printTree(node.left);
            tree.add(node.key);
            printTree(node.right);
        }
        return tree;
    }
}