/**
 * Your implementation of the AVL tree rotations.
 */
public class AVL<T extends Comparable<? super T>> {

    /**
     * DO NOT ADD ANY GLOBAL VARIABLES!
     */

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
    public void updateHeightAndBF(AVLNode<T> currentNode) {
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
    public AVLNode<T> rotateLeft(AVLNode<T> currentNode) {
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
    public AVLNode<T> rotateRight(AVLNode<T> currentNode) {
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
    public AVLNode<T> balance(AVLNode<T> currentNode) {

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
}