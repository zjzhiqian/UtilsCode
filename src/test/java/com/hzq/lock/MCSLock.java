package com.hzq.lock;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * Created by hzq on 16/7/26.
 */
public class MCSLock implements Lock {


    class QNode {
        boolean locked = false;
        QNode next = null;
    }


    AtomicReference<QNode> tail = new AtomicReference<>();
    ThreadLocal<QNode> myNode = new ThreadLocal<QNode>() {
        @Override
        protected QNode initialValue() {
            return new QNode();
        }
    };

    @Override
    public void lock() {
        QNode qnode = myNode.get();
        QNode pred = tail.getAndSet(qnode);
        if (pred != null) {
            qnode.locked = true;
            pred.next = qnode;

            // wait until predecessor gives up the lock
            while (qnode.locked) {
            }
        }
    }

    @Override
    public void unlock() {
        QNode qnode = myNode.get();
        if (qnode.next == null) {
            if (tail.compareAndSet(qnode, null))  //qNode下一个节点没有值,原子操作把tail设置为qNode,设置失败了表示其他线程修改了tail
                return;

            // wait until predecessor fills in its next field
            while (qnode.next == null) {
            }
        }
        qnode.next.locked = false;
        qnode.next = null;
    }

    @NotNull
    @Override
    public Condition newCondition() {
        return null;
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    @Override
    public boolean tryLock() {
        return false;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }

    public static void main(String[] args) {
        final MCSLock mcsLock = new MCSLock();
        mcsLock.lock();
        mcsLock.lock();


    }
}