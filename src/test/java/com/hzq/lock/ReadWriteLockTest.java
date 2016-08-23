package com.hzq.lock;


import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * ReadWriteLockTest
 * Created by hzq on 16/5/10.
 */
//在读的过程中别的线程是无法调用写的方法的
//在写的过程中也是无法调用读的方法的
//只有在读的时候才能同时调用读的方法，当然在写的时候不能同时调用写的方法
public class ReadWriteLockTest {
    public static void main(String[] args) {
        final ReadWriteLockT s = new ReadWriteLockT();
        ExecutorService service = Executors.newCachedThreadPool();
        for (int i = 0; i < 4; i++) {
            service.execute(() -> {
                while (true) {
                    s.getX();
                }
            });
            service.execute(() -> {
                while (true) {
                    s.setX(new Random().nextInt());
                }
            });
        }
    }

    static class ReadWriteLockT {
        private int x = 0;
        private ReadWriteLock rock = new ReentrantReadWriteLock();

        public void setX(int x) {
            rock.writeLock().lock();
            System.out.println(Thread.currentThread().getName()+ ".........进入写锁");
            this.x = x;
            try {
                Thread.sleep(200L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("写完毕");
            rock.writeLock().unlock();
        }

        public int getX() {
            rock.readLock().lock();
            System.out.println(Thread.currentThread().getName() + "----------进入读锁:");
            try {
                Thread.sleep(500L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(x);
            System.out.println("读完毕");
            rock.readLock().unlock();
            return x;
        }
    }
}