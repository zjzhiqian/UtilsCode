package com.hzq.struct.queue;

/**
 * 优先队列,有序堆
 * Created by hzq on 17/1/6.
 */
public class MyPriQueue<T extends Comparable> {
    private Object[] arr = new Object[100];
    private int size = 0;

    public void insert(T t) {
        int hole = ++size;
        for (; hole > 1 && t.compareTo((arr[hole / 2])) < 0; hole = hole / 2) {
            arr[hole] = arr[hole / 2];
        }
        arr[hole] = t;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public T delete() {
        Object data = arr[1];
        int hole = 1;
        int child = 1;
        T result = (T) arr[size];
        for (; hole * 2 <= size; hole = child) {
            child = child * 2;
            if (child < size && ((T) arr[child]).compareTo((T) arr[child + 1]) > 0) {
                child++;
            }
            if (result.compareTo((T) arr[child]) > 0) {
                arr[hole] = arr[child];
            } else break;
        }
        arr[hole] = result;
        arr[size] = null;
        size--;
        return (T) data;

    }

    public static void main(String[] args) {
        MyPriQueue<Integer> queue = new MyPriQueue<>();
        queue.insert(3);
        queue.insert(4);
        queue.insert(5);
        queue.insert(-2);
        queue.insert(11);
        queue.insert(2);
        queue.insert(1);
        queue.insert(23);
        queue.insert(99);
        while (queue.size > 0) {
            System.out.println(queue.delete());
        }


    }

}
