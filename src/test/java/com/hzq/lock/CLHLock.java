package com.hzq.lock;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * Created by hzq on 16/5/11.
 */

class QNode {
    public volatile boolean locked = false;
}


public class CLHLock implements Lock {
    private AtomicReference<QNode> tail;
    private ThreadLocal<QNode> myNode;
    private ThreadLocal<QNode> myPred;

    public CLHLock() {
        tail = new AtomicReference<>(new QNode());
        myNode = new ThreadLocal<QNode>() {
            protected QNode initialValue() {
                return new QNode();
            }
        };
        myPred = new ThreadLocal<QNode>() {
            protected QNode initialValue() {
                return null;
            }
        };
    }

    @Override
    public void lock() {
        QNode qnode = myNode.get();
        qnode.locked = true;
        QNode pred = tail.getAndSet(qnode);
        myPred.set(pred);
        while (pred.locked) {
            System.out.println("11111");
        }
    }


    public void unlock() {
        QNode qnode = myNode.get();
        qnode.locked = false;
        myNode.set(myPred.get());
    }

    public static void main (String... args){


        ExecutorService service = Executors.newFixedThreadPool(3);
        Lock lock = new CLHLock();
        service.execute(new ttsk(lock));
        service.execute(new ttsk(lock));



    }


    static class ttsk implements Runnable{
        private final Lock lock;
        ttsk(Lock lock){
            this.lock = lock;
        }
        @Override
        public void run() {

            lock.lock();


        }
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


    @NotNull
    @Override
    public Condition newCondition() {
        return null;
    }




}