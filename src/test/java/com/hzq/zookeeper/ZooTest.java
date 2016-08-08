package com.hzq.zookeeper;

import org.apache.zookeeper.*;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Created by hzq on 16/8/8.
 */
public class ZooTest {
    private static CountDownLatch latch = new CountDownLatch(1);


    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
        //参数1: url列表 由,分割  ip:port
        //参数2:sessionTimeOut
        //参数3:watcher
        //参数4:标识当前会话是否支持只读,这台机器与过半机器失去了网络连接,是否提供服务
        //参数5:  两个参数 sessionId sessionPsw 确定一个会话
        ZooKeeper zooKeeper = new ZooKeeper("192.168.29.103:2181", 3000, event -> {
            System.out.println("watching event change :    " + event);
            if (Watcher.Event.KeeperState.SyncConnected == event.getState()) {
                latch.countDown();
                System.out.println("countDown");
            }
        }); //会话建立是异步过程
        System.out.println(zooKeeper.getState());
        latch.await();
        System.out.println("....finish connect");

        //同步创建
        //参数1:path
        //参数2:data
        //参数3:ACL策略
        //参数4:PERSISTENT持久 PERSISTENT_SEQUENTIAL持久序列 EPHEMERAL临时  EPHEMERAL_SEQUENTIAL临时序列
        String path = zooKeeper.create("/test", "data".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
        System.out.println(path);

        //异步创建 callBack 和 context
        zooKeeper.create("/test", "data".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL, (rc, path1, ctx, name) -> {
            //rc 0:调用成功 -4:客户端与服务端连接断开 -110:指定节点已存在 -112:会话已过去
            //path1 节点路径参数值
            //ctx 第四个参数 ctx
            //name 节点路径
            System.out.println(rc);
            System.out.println(path1);
            System.out.println(ctx);
            System.out.println(name);
        }, "ctx");

        //删除节点(删除节点时,必须删除所有子节点)
//        zooKeeper.delete("path",1);
        //异步删除
//        zooKeeper.delete("path", 1, (rc, path1, ctx) -> {
//
//        },"ctx");


        //读取数据 getChildren
        //参数1: path
        //参数2: watcher (watcher仅仅通知一次)
        //参数3: boolean 是否需要watcher
        //参数4: callback
        //参数5: ctx
        //参数6: stat
        zooKeeper.getChildren("/test", event -> {
            try {
                System.out.println(zooKeeper.getChildren(event.getPath(), true));
            } catch (KeeperException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });


        //getData

        TimeUnit.SECONDS.sleep(150);
        zooKeeper.close();
    }
}
