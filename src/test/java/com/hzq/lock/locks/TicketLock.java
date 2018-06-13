package com.hzq.lock.locks;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 优先级 自选锁
 * Created by hzq on 17/5/18.
 */
public class TicketLock {
    private AtomicInteger serviceNum = new AtomicInteger();
    private AtomicInteger ticketNum = new AtomicInteger();

    public int lock() {
        int myTickets = ticketNum.getAndIncrement();
        while (!serviceNum.equals(myTickets)) {
        }
        return serviceNum.intValue();
    }

    public void unlock(int ticket) {
        serviceNum.compareAndSet(ticket, ticket + 1);
    }
}