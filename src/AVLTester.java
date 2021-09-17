public class AVLTester {

    public static void main(String[] args) {
        AVLNode<Integer> node0 = new AVLNode<>(0);
        AVLNode<Integer> node1 = new AVLNode<>(1);
        AVLNode<Integer> node2 = new AVLNode<>(2);
        AVLNode<Integer> node3 = new AVLNode<>(3);
        AVLNode<Integer> node4 = new AVLNode<>(4);
        AVLNode<Integer> node5 = new AVLNode<>(5);
        AVLNode<Integer> node6 = new AVLNode<>(6);
        AVLNode<Integer> node7 = new AVLNode<>(7);
        AVLNode<Integer> node8 = new AVLNode<>(8);
        AVLNode<Integer> node9 = new AVLNode<>(9);

        node2.setLeft(node0);
        node0.setRight(node1);
        node2.setRight(node4);
        node4.setRight(node6);
        node6.setRight(node5);
        node4.setLeft(node3);

        AVL<Integer> misty = new AVL<>();
        /*
        misty.add(2);
        misty.add(0);
        misty.add(1);
        misty.add(4);
        misty.add(3);
        misty.add(6);
        misty.add(5);


        System.out.println(misty.preorder(misty.getRoot()));

        misty.remove(3);

         */
        misty.add(0);

        System.out.println(misty.preorder(misty.getRoot()));
        misty.remove(0);
        System.out.println(misty.preorder(misty.getRoot()));
    }
}
