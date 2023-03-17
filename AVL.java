public class AVL<Any extends Comparable<Any>> extends Tree<Any> {

    // caller to the rotateLeft method from tree class
    Node leftRotateCaller(Node node) {
        Node temp = rotateLeft(node);
        update(node);
        update(temp);
        return temp;
    }

    // caller to the rotateRight method from tree class
    Node rightRotateCaller(Node node) {
        Node temp = rotateRight(node);
        update(node);
        update(temp);
        return temp;
    }

    @Override
    public void update(Node node) {
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
    public Node rebalance(Node node) {
        if (node.balanceFactor > 1) {

            if (node.left.balanceFactor < 0) {
                node.left = leftRotateCaller(node.left);
            }
            return rightRotateCaller(node);

        } else if (node.balanceFactor < -1) {
            if (node.right.balanceFactor > 0) {
                node.right = rightRotateCaller(node.right);
            }
            return leftRotateCaller(node);

        }
        return node;
    }

    @Override
    public Node insert(Node node, Any key) {
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

    @Override
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

    @Override
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

    @Override
    public Node delete(Node node, Any key) {
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

    public static void main(String[] args) {

        // uncomment this code for testing purposes

        // AVL<Integer> tree = new AVL<Integer>();
        // System.out.println("inserting 4 is : " + tree.insert(4));
        // System.out.println("inserting 6 is : " + tree.insert(6));
        // System.out.println("inserting 10 is : " + tree.insert(10));
        // System.out.println("inserting 15 is : " + tree.insert(15));
        // System.out.println("inserting 13 is : " + tree.insert(13));
        // System.out.println("inserting 22 is : " + tree.insert(22));
        // System.out.println("inserting 19 is : " + tree.insert(19));
        // System.out.println("inserting 11 is : " + tree.insert(11));
        // System.out.println("inserting 27 is : " + tree.insert(27));
        // System.out.println("inserting 34 is : " + tree.insert(34));
        // System.out.println("delete 10 is : " + tree.delete(10));
        // System.out.println("found 2 : " + tree.search(2));
        // System.out.println("inserting 2 is : " + tree.insert(2));
        // System.out.println("number of nodes in this tree is : " + tree.size());
        // System.out.println("height of tree is : " + tree.height());
        // System.out.println("found 1 : " + tree.search(1));
        // tree.printTree();
        // System.out.println("inserting 1 is : " + tree.insert(1));
        // System.out.println("inserting 12 is : " + tree.insert(12));
        // System.out.println("number of nodes in this tree is : " + tree.size());
        // System.out.println("delete 12 is : " + tree.delete(12));
        // System.out.println("inserting 8 is : " + tree.insert(8));
        // System.out.println("found 12 : " + tree.search(12));
        // tree.printTree();
        // System.out.println("number of nodes in this tree is : " + tree.size());
        // System.out.println("delete 4 is : " + tree.delete(4));
        // System.out.println("delete 16 is : " + tree.delete(16));
        // System.out.println("height of tree is : " + tree.height());
        // System.out.println("delete 8 is : " + tree.delete(8));
        // System.out.println("height of tree is : " + tree.height());
        // System.out.println("delete 2 is : " + tree.delete(2));
        // System.out.println("delete 1 is : " + tree.delete(1));
        // System.out.println("height of tree is : " + tree.height());
        // System.out.println("delete 6 is : " + tree.delete(6));
        // System.out.println("height of tree is : " + tree.height());
        // System.out.println("number of node in this tree is : " + tree.size());
        // tree.printTree();

        /*
         * expected output is
         * inserting 4 is : true
         * inserting 6 is : true
         * inserting 10 is : true
         * inserting 15 is : true
         * inserting 13 is : true
         * inserting 22 is : true
         * inserting 19 is : true
         * inserting 11 is : true
         * inserting 27 is : true
         * inserting 34 is : true
         * delete 10 is : true
         * found 2 : false
         * inserting 2 is : true
         * number of nodes in this tree is : 10
         * height of tree is : 3
         * found 1 : false
         * [2, 4, 6, 11, 13, 15, 19, 22, 27, 34]
         * inserting 1 is : true
         * inserting 12 is : true
         * number of nodes in this tree is : 12
         * delete 12 is : true
         * inserting 8 is : true
         * found 12 : false
         * [1, 2, 4, 6, 8, 11, 13, 15, 19, 22, 27, 34]
         * number of nodes in this tree is : 12
         * delete 4 is : true
         * delete 16 is : false
         * height of tree is : 3
         * delete 8 is : true
         * height of tree is : 3
         * delete 2 is : true
         * delete 1 is : true
         * height of tree is : 3
         * delete 6 is : true
         * height of tree is : 2
         * number of node in this tree is : 7
         * [11, 13, 15, 19, 22, 27, 34]
         */
    }
}