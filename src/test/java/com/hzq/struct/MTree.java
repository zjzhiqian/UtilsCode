package com.hzq.struct;

/**
 * Created by hzq on 16/5/29.
 */
public class MTree<T extends Comparable> {


    private BinaryNode<T> root;

    public MTree(T data) {
        root = new BinaryNode<>();
        root.data = data;
    }

    public boolean contains(T element) {
        return contains(root, element);
    }

    private boolean contains(BinaryNode<T> root, T element) {
        if (root == null) return false;
        Integer comp = root.data.compareTo(element);
        if (comp > 0) return contains(root.left, element);
        else if (comp < 0) return contains(root.right, element);
        else return true;
    }


    public void insert(T element) {
        insert(root, element);
    }

    private BinaryNode<T> insert(BinaryNode<T> root, T element) {
        if (root == null) {
            root = new BinaryNode();
            root.data = element;
            return root;
        }
        Integer comp = root.data.compareTo(element);
        if (comp > 0) root.left = insert(root.left, element);
        else if (comp < 0) root.right = insert(root.right, element);
        return root;

    }


    private static class BinaryNode<T extends Comparable> {
        T data;
        BinaryNode left;
        BinaryNode right;

        BinaryNode() {
            this.data = null;
            this.left = null;
            this.right = null;
        }
    }
}

