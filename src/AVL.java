import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Your implementation of the AVL tree rotations.
 */
public class AVL<T extends Comparable<? super T>> {

    /**
     * DO NOT ADD ANY GLOBAL VARIABLES!
     */
    private AVLNode<T> root;
    private int size;


    /**
     * Adds the element to the tree.
     *
     * Start by adding it as a leaf like in a regular BST and then rotate the
     * tree as necessary.
     *
     * If the data is already in the tree, then nothing should be done (the
     * duplicate shouldn't get added, and size should not be incremented).
     *
     * Remember to recalculate heights and balance factors while going back
     * up the tree after adding the element, making sure to rebalance if
     * necessary. This is as simple as calling the balance() method on the
     * current node, before returning it (assuming that your balance method
     * is written correctly from part 1 of this assignment).
     *
     * @param data The data to add.
     * @throws java.lang.IllegalArgumentException If data is null.
     */
    /**
     * Adds the data to the tree.
     *
     * Should be O(log n) for best and average cases and O(n) for worst case.
     */
    public void add(T data) {
        if(data == null){
            throw new IllegalArgumentException("Can't add null data to the Tree!");
        } else {
            root = rAdd(root, data);
        }
    }

    private AVLNode<T> rAdd(AVLNode<T> curr, T data){
        if(curr == null){
            size ++;
            curr = new AVLNode<>(data);

        } else if (curr.getData().compareTo(data) > 0){
            curr.setLeft(rAdd(curr.getLeft(), data));

        } else if (curr.getData().compareTo(data) < 0){
            curr.setRight(rAdd(curr.getRight(), data));
        }
        updateHeightAndBF(curr);
        curr = balance(curr);
        return curr;
    }

    /**
     * Removes and returns the element from the tree matching the given
     * parameter.
     *
     * There are 3 cases to consider:
     * 1: The node containing the data is a leaf (no children). In this case,
     *    simply remove it.
     * 2: The node containing the data has one child. In this case, simply
     *    replace it with its child.
     * 3: The node containing the data has 2 children. Use the successor to
     *    replace the data, NOT predecessor. As a reminder, rotations can occur
     *    after removing the successor node.
     *
     * Remember to recalculate heights and balance factors while going back
     * up the tree after removing the element, making sure to rebalance if
     * necessary. This is as simple as calling the balance() method on the
     * current node, before returning it (assuming that your balance method
     * is written correctly from part 1 of this assignment).
     *
     * Do NOT return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * @param data The data to remove.
     * @return The data that was removed.
     * @throws java.lang.IllegalArgumentException If the data is null.
     * @throws java.util.NoSuchElementException   If the data is not found.
     */
    public T remove(T data) {
        // WRITE YOUR CODE HERE (DO NOT MODIFY METHOD HEADER)!
        if (data == null)
            throw new IllegalArgumentException();
        AVLNode<T> dummy = new AVLNode<>(null);
        root = innerRemove(data,root,dummy);
        return dummy.getData();
    }

    private AVLNode<T> innerRemove(T data, AVLNode<T> currNode,AVLNode<T> dummy){
        // Base Case - Data Not Found
        if (currNode == null)
            throw new NoSuchElementException();

        int compVal = currNode.getData().compareTo(data);
        // Base Case - Data Found
        if (compVal == 0){
            // Save data to dummy node to return & decrement size
            dummy.setData(currNode.getData());
            size--;
            // No child case
            if (currNode.getLeft() == null && currNode.getRight() == null) {
                return null;
            }
            // Left Child
            else if (currNode.getLeft() != null && currNode.getRight() == null){
                return currNode.getLeft();
            }
            // Right Child
            else if (currNode.getLeft() == null && currNode.getRight() != null){
                return currNode.getRight();
            }
            // 2 Child
            else {
                AVLNode<T> dummy2 = new AVLNode<>(null);
                currNode.setRight(successor(currNode.getRight(),dummy2));
                currNode.setData(dummy2.getData());
                updateHeightAndBF(currNode);
            }
        }
        // Recursive Find
        else if (compVal > 0){
            currNode.setLeft(innerRemove(data,currNode.getLeft(),dummy));
            updateHeightAndBF(currNode);
        }
        else {
            currNode.setRight(innerRemove(data,currNode.getRight(),dummy));
            updateHeightAndBF(currNode);
        }
        if (currNode.getBalanceFactor() > 1 || currNode.getBalanceFactor() < -1){
            currNode = balance(currNode);
        }
        return currNode;
    }

    private AVLNode<T> successor(AVLNode<T> currNode, AVLNode<T> dummy2){
        // Base Case - Found Successor
        if (currNode.getLeft() == null){
            dummy2.setData(currNode.getData());
            return  currNode.getRight();
        }
        // Recurse
        else {
            currNode.setLeft(successor(currNode.getLeft(),dummy2));
            updateHeightAndBF(currNode);
            if (Math.abs(currNode.getBalanceFactor()) > 1)
                currNode = balance(currNode);
            return currNode;
        }
    }

    /**
     * Updates the height and balance factor of a node using its
     * setter methods.
     *
     * Recall that a null node has a height of -1. If a node is not
     * null, then the height of that node will be its height instance
     * data. The height of a node is the max of its left child's height
     * and right child's height, plus one.
     *
     * The balance factor of a node is the height of its left child
     * minus the height of its right child.
     *
     * This method should run in O(1).
     * You may assume that the passed in node is not null.
     *
     * This method should only be called in rotateLeft(), rotateRight(),
     * and balance().
     *
     * @param currentNode The node to update the height and balance factor of.
     */
    private void updateHeightAndBF(AVLNode<T> currentNode) {
        int leftHeight;
        if(currentNode.getLeft() == null)
            leftHeight = -1;
        else
            leftHeight = currentNode.getLeft().getHeight();

        int rightHeight;
        if(currentNode.getRight() == null)
            rightHeight = -1;
        else
            rightHeight = currentNode.getRight().getHeight();


        currentNode.setHeight(Math.max(leftHeight, rightHeight) + 1);
        currentNode.setBalanceFactor(leftHeight - rightHeight);
    }

    /**
     * Method that rotates a current node to the left. After saving the
     * current's right node to a variable, the right node's left subtree will
     * become the current node's right subtree. The current node will become
     * the right node's left subtree.
     *
     * This method should only be called in balance().
    */
    private AVLNode<T> rotateLeft(AVLNode<T> currentNode) {
        /**
         * 1. Node B is Node A's right child
         * 2. Node A's right child becomes Node B's left child
         * 3. Node A becomes Node B's left child
         * 4. Update height and BF of A
         * 5. Update height and BF of B
         * 6. Return B
         */
        AVLNode<T> nodeB = currentNode.getRight();
        currentNode.setRight(currentNode.getRight().getLeft());
        nodeB.setLeft(currentNode);
        updateHeightAndBF(currentNode);
        updateHeightAndBF(nodeB);
        return nodeB;
    }

    /**
     * Method that rotates a current node to the right. After saving the
     * current's left node to a variable, the left node's right subtree will
     * become the current node's left subtree. The current node will become
     * the left node's right subtree.
     *
    `* This method should only be called in balance().
     */
    private AVLNode<T> rotateRight(AVLNode<T> currentNode) {
        /**
         * 1. Node B is Node C's right child
         * 2. Node C's right child becomes Node B's right child
         * 3. Node C becomes Node B's right child
         * 4. Update height and BF of C
         * 5. Update height and BF of B
         * 6. Return B
         */
        AVLNode<T> nodeB = currentNode.getLeft();
        currentNode.setLeft(currentNode.getLeft().getRight());
        nodeB.setRight(currentNode);
        updateHeightAndBF(currentNode);
        updateHeightAndBF(nodeB);
        return nodeB;
    }

    /**
     * This is the overarching method that is used to balance a subtree
     * starting at the passed in node. This method will utilize
     * updateHeightAndBF(), rotateLeft(), and rotateRight() to balance
     * the subtree. In part 2 of this assignment, this balance() method
     * will be used in your add() and remove() methods.
     *
     * The height and balance factor of the current node is first recalculated.
     * Based on the balance factor, a no rotation, a single rotation, or a
     * double rotation takes place. The current node is returned.
     *
     * You may assume that the passed in node is not null. Therefore, you do
     * not need to perform any preliminary checks, rather, you can immediately
     * check to see if any rotations need to be performed.
     *
     * This method should run in O(1).
     *
     * @param cur The current node under inspection.
     * @return The AVLNode that the caller should return.
     */
    private AVLNode<T> balance(AVLNode<T> currentNode) {

        /* First, we update the height and balance factor of the current node. */
        updateHeightAndBF(currentNode);

        if (currentNode.getBalanceFactor() <= -2 ) {
            if ( currentNode.getRight().getBalanceFactor() >= 1 ) {
                currentNode.setRight(rotateRight(currentNode.getRight()));
            }
            currentNode = rotateLeft(currentNode);
        } else if ( currentNode.getBalanceFactor() >= 2 ) {
            if ( currentNode.getLeft().getBalanceFactor() <= -1 ) {
                currentNode.setLeft(rotateLeft(currentNode.getLeft()));
            }
            currentNode = rotateRight(currentNode);
        }

        return currentNode;
    }
    /**
     * Returns the root of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return The root of the tree.
     */
    public AVLNode<T> getRoot() {
        // DO NOT MODIFY THIS METHOD!
        return root;
    }

    /**
     * Returns the size of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return The size of the tree.
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }

    /**
     * Pre Order Traversal
     */
    public List<T> preorder(AVLNode<T> root) {
        // C,L,R

        List<T> returnVals = new ArrayList<T>();
        if (baseCase(root) == true){
            returnVals.add(root.getData());
        }
        else {
            List<T> leftVals = new ArrayList<T>();
            List<T> rightVals = new ArrayList<T>();
            if(root.getLeft() != null){
                leftVals = preorder(root.getLeft());
            }
            if(root.getRight() != null){
                rightVals = preorder(root.getRight());
            }

            returnVals.add(root.getData());
            returnVals.addAll(leftVals);
            returnVals.addAll(rightVals);
        }
        return returnVals;
    }
    /**
     * Base Case Helper Method
     */
    private boolean baseCase(AVLNode<T> root){
        if(root != null){
            if (root.getLeft() == null && root.getRight() == null)
                return true;
        }
        return false;
    }

}