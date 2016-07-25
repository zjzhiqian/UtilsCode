package com.hzq.struct;

/**
 * 平衡树的实现
 * Created by hzq on 15/5/5.
 */
public class AvlTree<T extends Comparable> {

    private AvlNode<T> root;

    public AvlTree(T element) {
        this.root = new AvlNode(element);
        this.root.height = 1;
    }

    public int getHeight() {
        return root == null ? -1 : root.height;
    }


    private int height(AvlNode<T> node) {
        return node == null ? -1 : node.height;
    }


    /**
     * 平衡树的添加方法
     *
     * @param element
     * @return
     */
    public T insert(T element) {
        return insert(root, element).element;
    }

    private AvlNode<T> insert(AvlNode<T> node, T element) {

        if (node == null) {
            return new AvlNode<T>(element);
        }

        int comPare = node.element.compareTo(element);

        if (comPare > 0) {
            //node大,插入左树
            node.left = insert(node.left, element);
            //TODO 这样判断平衡BUG
            if (height(node.left) - height(node.right) == 2) {
                //不平衡
                if (node.left.element.compareTo(element) > 0) {
                    //node大,插入左树
                    node = rotateWithLeftChild(node); //左树单旋转
                } else {
                    node = doubleWithLeftChild(node); //左树双旋转
                }
            }
        } else if (comPare < 0) {
            //node小,插入右树
            node.right = insert(node.right, element);
            if (height(node.left) - height(node.right) == 2) {
                if (node.right.element.compareTo(element) < 0) {
                    //node小,插入右树
                    node = rotateWithRightChild(node);//右树单旋转
                } else {
                    node = doubleWithRightChild(node);//右树双旋转
                }
            }

        }
        node.height = Math.max(height(node.left), height(node.right)) + 1;

        return node;
    }

    /**
     * 右树进行双旋转
     *
     * @param k3
     * @return
     */
    private AvlNode<T> doubleWithRightChild(AvlNode<T> k3) {
        k3.right = rotateWithLeftChild(k3.right);
        return rotateWithLeftChild(k3);
    }

    /**
     * 左树双旋转
     *
     * @param k3
     * @return
     */
    private AvlNode<T> doubleWithLeftChild(AvlNode<T> k3) {
        k3.left = rotateWithRightChild(k3.left);
        return rotateWithRightChild(k3);
    }


    /**
     * 右树单旋转
     *
     * @param k2
     * @return
     */
    private AvlNode<T> rotateWithRightChild(AvlNode<T> k2) {

        AvlNode<T> k1 = k2.right;
        k1.left = k2;
        k2.right = k1.left;
        k2.height = Math.max(height(k2.left), height(k2.right) + 1);
        k1.height = Math.max(height(k1.right), k2.height) + 1;
        return k1;
    }

    /**
     * 左树单旋转
     *
     * @param k2
     * @return
     */
    private AvlNode<T> rotateWithLeftChild(AvlNode<T> k2) {
        AvlNode<T> k1 = k2.left;
        k1.right = k2;
        k2.left = k1.right;
        k2.height = Math.max(height(k2.right), height(k2.left) + 1);
        k1.height = Math.max(height(k1.left), k2.height) + 1;
        return k1;
    }


    /**
     * 平衡树Node
     *
     * @param <T>
     */
    private static class AvlNode<T> {

        T element;
        AvlNode<T> left;
        AvlNode<T> right;
        Integer height;

        public AvlNode(T element) {
            this(element, null, null);
        }

        public AvlNode(T element, AvlNode<T> left, AvlNode<T> right) {
            this.element = element;
            this.left = left;
            this.right = right;
            this.height = 0;
        }


        @Override
        public String toString() {
            return element.toString();
        }


        public void display() {
            //空节点、叶子节点不打印
            if (element == null || (left == null && right == null))
                return;
            System.out.println(String.format("H=%2d, %s->(%s,%s)",
                    height,
                    element.toString(),
                    left == null ? null : left.element.toString(),
                    right == null ? null : right.element.toString()));
            if (left != null)
                left.display();
            if (right != null)
                right.display();
        }

    }


    public void display() {
        root.display();
    }


    public static void main(String... args) {
        AvlTree tree = new AvlTree(4);
        tree.insert(2);
        tree.insert(6);
        tree.insert(1);
        tree.insert(3);
        tree.insert(5);
        tree.insert(7);


        tree.insert(16);
        tree.insert(15);
        System.out.println("================");
        tree.display();
    }


}
