package com.hzq.zookeeper;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * AuthTest
 * Created by hzq on 16/8/11.
 */
public class AuthTest {

    public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
        ZooKeeper zooKeeper = null;
        ZooKeeper zooKeeper1 = null;
        try {
            zooKeeper = new ZooKeeper("192.168.29.101:2181", 3000, event -> System.out.println("1"));
            //"world,auth,digest,ip,super"
            zooKeeper.addAuthInfo("digest", "foo:true".getBytes());
            zooKeeper.create("/test01", "data".getBytes(), ZooDefs.Ids.CREATOR_ALL_ACL, CreateMode.EPHEMERAL);


            zooKeeper1 = new ZooKeeper("192.168.29.101:2181", 5000, null);
            zooKeeper.getData("/test01", false, null);
            zooKeeper1.getData("/test01", false, null);

            TimeUnit.SECONDS.sleep(Integer.MAX_VALUE);
        } finally {
            zooKeeper.close();
            zooKeeper1.close();
        }

    }
}
