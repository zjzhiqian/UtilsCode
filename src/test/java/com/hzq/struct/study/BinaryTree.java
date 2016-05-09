package com.hzq.struct.study;

/**
 * 普通二叉树Java代码
 * Created by hzq on 16/5/3.
 */
public class BinaryTree<T extends Comparable> {
    private BinaryNode<T> root;

    public BinaryTree(T data) {
        this.root = new BinaryNode<T>(data);
    }


    /**
     * 判断这个tree是否含有 指定node
     *
     * @param element
     * @return
     */
    public boolean contains(T element) {
        BinaryNode<T> node = new BinaryNode<T>(element);
        return contains(root, node);
    }

    private boolean contains(BinaryNode<T> root, BinaryNode<T> node) {
        if (root == null) {
            return false;
        }
        int comPare = root.element.compareTo(node.element);
        if (comPare > 0) {
            return contains(root.left, node);
        } else if (comPare < 0) {
            return contains(root.right, node);
        } else {
            return true;
        }


    }

    /**
     * 查找最大的node
     *
     * @return
     */
    public BinaryNode<T> findMax() {
        return findMax(root);
    }


    private BinaryNode<T> findMax(BinaryNode<T> node) {
        if (node.right != null) {
            return findMax(node.right);
        } else {
            return node;
        }
    }

    /**
     * 查找最小的node
     *
     * @return
     */
    public BinaryNode<T> findMin() {
        return findMin(root);
    }


    private BinaryNode<T> findMin(BinaryNode<T> node) {
        if (node.left != null) {
            return findMin(node.left);
        } else {
            return node;
        }
    }

    /**
     * 插入数据
     *
     * @param element
     * @return
     */
    public BinaryNode<T> insert(T element) {
        return insert(root, element);
    }

    private BinaryNode<T> insert(BinaryNode<T> node, T element) {
        if (node == null) {
            return new BinaryNode<T>(element);
        }
        int compRs = node.element.compareTo(element);
        if (compRs > 0) {
            node.left = insert(node.left, element);
        } else if (compRs < 0) {
            node.right = insert(node.right, element);
        }
        return node;
    }

    /**
     * 删除节点
     *
     * @param element
     * @return
     */
    public BinaryNode<T> remove(T element) {
        return remove(root, element);
    }

    private BinaryNode<T> remove(BinaryNode<T> node, T element) {
        if (node == null) {
            return node;
        }
        Integer compRs = node.element.compareTo(element);
        if (compRs > 0) {
            node.left = remove(node.left, element);
        } else if (compRs < 0) {
            node.right = remove(node.right, element);
        } else if (node.left != null && node.right != null) {
            //有2个子节点,先用右树最小值替代节点值,然后删除右树的这个最小值节点
            node.element = findMin(node.right).element;
            node.right = remove(node.right, node.element);
        } else {
            //只有一个子节点,将node用node下的节点替代
            node = node.left == null ? node.right : node.left;
        }
        return node;
    }

    /**
     * 打印tree
     */
    public void print() {
        print(root, 0);
    }

    private void print(BinaryNode<T> node, Integer height) {
        if (node != null) {
            height++;
            print(node.right, height);
            StringBuffer buffer = new StringBuffer();
            for (int i = 0; i < height; i++) {
                buffer.append("         ");
            }
            buffer.append("------");
            System.out.println(buffer.toString() + node.element);
            print(node.left, height);
        }
    }


    private static class BinaryNode<T extends Comparable> {


        T element;
        BinaryNode<T> left;
        BinaryNode<T> right;

        public BinaryNode(T element) {
            this.element = element;
        }

        public BinaryNode(T element, BinaryNode<T> left, BinaryNode<T> right) {
            this.element = element;
            this.left = left;
            this.right = right;
        }


        @Override
        public String toString() {
            return element.toString();
        }
    }


    public static void main(String... args) {
        BinaryTree<Integer> tree = new BinaryTree<Integer>(6);
        tree.insert(2);
        tree.insert(8);
        tree.insert(1);
        tree.insert(4);
        tree.insert(3);

        tree.insert(1);
        tree.insert(7);
        System.out.println(tree.contains(5));
//        System.out.println(tree.findMax().element);
//        System.out.println(tree.findMin().element);


        tree.print();

        tree.remove(2);
        System.out.println("===================================");
        tree.print();

    }


}


