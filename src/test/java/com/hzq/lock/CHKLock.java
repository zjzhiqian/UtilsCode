package com.hzq.lock;

import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by hzq on 16/5/14.
 */


class QNode {
    public volatile boolean locked = false;
}

public class CHKLock {

    private AtomicReference<QNode> atomicRef = new AtomicReference();
    private ThreadLocal<QNode> mNode;
    private ThreadLocal<QNode> pNode;

    public CHKLock() {
        mNode = new ThreadLocal<QNode>() {
            @Override
            protected QNode initialValue() {
                return new QNode();
            }
        };
        pNode = new ThreadLocal<QNode>() {
            @Override
            protected QNode initialValue() {
                return null;
            }
        };
    }

    public void lock() {
        QNode qNode = mNode.get();
        qNode.locked = true;
        QNode refP = atomicRef.getAndSet(qNode);
        pNode.set(refP);
        while (refP.locked) {
        }
    }

    public void unLock() {
        QNode qnode = mNode.get();
        qnode.locked = false;
        mNode.set(pNode.get());
    }
}
