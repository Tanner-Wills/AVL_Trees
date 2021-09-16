/**
 * Node class used for implementing the AVL.
 *
 * DO NOT MODIFY THIS FILE!!
 *
 * @author CS 1332 TAs
 * @version 1.0
 */
public class AVLNode<T extends Comparable<? super T>> {

    private T data;
    private AVLNode<T> left;
    private AVLNode<T> right;
    private int height;
    private int balanceFactor;

    /**
     * Create an AVLNode with the given data.
     */
    public AVLNode(T data) {
        this.data = data;
    }

    /**
     * Gets the data.
     */
    public T getData() {
        return data;
    }

    /**
     * Gets the left child.
     */
    public AVLNode<T> getLeft() {
        return left;
    }

    /**
     * Gets the right child.
     */
    public AVLNode<T> getRight() {
        return right;
    }

    /**
     * Gets the height.
    */
    public int getHeight() {
        return height;
    }

    /**
     * Gets the balance factor.
     */
    public int getBalanceFactor() {
        return balanceFactor;
    }

    /**
     * Sets the data.
     */
    public void setData(T data) {
        this.data = data;
    }

    /**
     * Sets the left child.
     */
    public void setLeft(AVLNode<T> left) {
        this.left = left;
    }

    /**
     * Sets the right child.
     */
    public void setRight(AVLNode<T> right) {
        this.right = right;
    }

    /**
     * Sets the height.
     */
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * Sets the balance factor.
     */
    public void setBalanceFactor(int balanceFactor) {
        this.balanceFactor = balanceFactor;
    }
}