package com.example.dictionary;

public class AVL<Any extends Comparable<Any>> extends Tree<Any> {

    // the right right case
    protected Node rotateLeft(Node node) {
        Node temp = node.right;
        node.right = temp.left;
        temp.left = node;
        update(node);
        update(temp);
        return temp;
    }

    // the left left case
    protected Node rotateRight(Node node) {
        Node temp = node.left;
        node.left = temp.right;
        temp.right = node;
        update(node);
        update(temp);
        return temp;
    }

    protected void update(Node node) {
        // initialize the height to -1 (nothing)
        int leftHeight = -1;
        int rightHeight = -1;
        // make sure that node.left and node.right are not null

        if (node.left != null) {
            leftHeight = node.left.height;
        }
        if (node.right != null) {
            rightHeight = node.right.height;
        }

        // get the max height of the left and right subtree and add 1 to it (the height
        // of the node itself)
        node.height = 1 + Math.max(leftHeight, rightHeight);
        node.balanceFactor = leftHeight - rightHeight;
    }

    // make sure the tree is balanced and the bf invariant is maintained
    protected Node rebalance(Node node) {
        if (node.balanceFactor > 1) {

            if (node.left.balanceFactor < 0) {
                node.left = rotateLeft(node.left);
            }

            return rotateRight(node);

        } else if (node.balanceFactor < -1) {

            if (node.right.balanceFactor > 0) {
                node.right = rotateRight(node.right);
            }

            return rotateLeft(node);

        }
        return node;
    }

    public boolean insert(Any key) {
        // if the key is null, return false

        if (key == null)
            return false;

        // check if the tree contains the key and if it doesn't then insert it and
        // return true
        if (search(root, key) == false) {
            root = insert(root, key);
            size++;
            return true;
        }

        // if the tree already contains the key, return false
        return false;
    }

    protected Node insert(Node node, Any key) {
        // if the node is null, return a new node with the key
        if (node == null)
            return new Node(key);

        // compare the given key with the node's key
        int comparisonResult = key.compareTo(node.key);

        // if the key is less than the node's key, try to insert it to the left
        if (comparisonResult < 0) {
            node.left = insert(node.left, key);
        }

        // if the key is greater than the node's key, try to insert it to the right
        else if (comparisonResult > 0) {
            node.right = insert(node.right, key);
        }

        update(node);
        return rebalance(node);
    }

    public boolean delete(Any key) {
        // if the key is null then return false
        if (key == null) {
            return false;
        }

        // if the key is found then delete
        if (search(root, key) == true) {
            root = delete(root, key);
            size--;
            return true;
        }

        // didn't find the key
        return false;
    }

    protected Node delete(Node node, Any key) {
        // if the node is null then return null (base case)
        if (node == null) {
            return null;
        }
        // compare the given key with the node's key to determine which subtree to go to
        int comparisonResult = key.compareTo(node.key);

        if (comparisonResult < 0) {
            // go to the left subtree
            node.left = delete(node.left, key);
        } else if (comparisonResult > 0) {
            // go to the right subtree
            node.right = delete(node.right, key);
        }
        // found the key
        else {
            // if the node is a leaf node then return null
            if (node.left == null && node.right == null) {
                return null;
            }
            // if the node has only one child then return the child
            else if (node.left == null) {
                return node.right;
            } else if (node.right == null) {
                return node.left;
            }
            // if the node has two children then find the replacement
            else {
                Node temp = node.right;
                while (temp.left != null) {
                    temp = temp.left;
                }
                node.key = temp.key;

                // delete the replacing node
                node.right = delete(node.right, temp.key);
            }
        }

        update(node);
        return rebalance(node);
    }
}