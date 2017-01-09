package com.hzq.struct.queue;

/**
 * Created by hzq on 17/1/6.
 */
public class MQ<T extends Comparable> {

    private Object[] arr = new Object[100];
    private int size;


    public void insert(T t) {
        int hole = ++size;
        for (; hole > 1 & ((T) arr[hole]).compareTo(arr[hole / 2]) > 0; hole = hole / 2) {
            arr[hole] = arr[hole / 2];
        }


    }

    public void delete() {
        T t = (T) arr[1];
        int hole = 1;
        for (; hole <= size; hole = hole * 2) {

        }


    }


}
