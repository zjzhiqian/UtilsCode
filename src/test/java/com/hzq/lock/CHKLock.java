package com.hzq.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

public class CHKLock {

    /**
     * Created by hzq on 16/5/14.
     */


    class QNode {
        public volatile boolean locked = false;
    }

    private AtomicReference<QNode> tail = new AtomicReference(new QNode());
    private ThreadLocal<QNode> myNode;
    private ThreadLocal<QNode> myPre;

    public CHKLock() {
        myNode = new ThreadLocal<QNode>() {
            @Override
            protected QNode initialValue() {
                return new QNode();
            }
        };
        myPre = new ThreadLocal<QNode>() {
            @Override
            protected QNode initialValue() {
                return null;
            }
        };
    }

    public void lock() {
        QNode qNode = myNode.get();
        qNode.locked = true;
        QNode refP = tail.getAndSet(qNode);
        myPre.set(refP);
        while (refP.locked) {
        }
    }

    public void unLock() {
        QNode qnode = myNode.get();
        qnode.locked = false;
        myNode.set(myPre.get());
    }


    /**
     * 模拟线程队列 main线程先进队列,new Thread后进队列,自旋等待
     */
    public static void main(String[] args) throws InterruptedException {
        final CHKLock lock = new CHKLock();

        lock.lock(); //线程1获取了锁
        System.out.println("线程1获取锁成功");
        new Thread(()->{
            System.out.println("线程2尝试获取锁");
            lock.lock();  //线程2获取锁会等待
            System.out.println("线程2获取锁成功");
        }).start();

        //等待睡眠
        TimeUnit.SECONDS.sleep(3);
        System.out.println("线程1释放锁");
        lock.unLock();

    }
}
