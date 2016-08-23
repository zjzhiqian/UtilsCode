package com.hzq.zookeeper.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.test.TestingCluster;
import org.apache.curator.test.TestingZooKeeperServer;
import org.apache.curator.utils.EnsurePath;
import org.apache.curator.utils.ZKPaths;
import org.apache.zookeeper.ZooKeeper;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

/**
 * curatorUtils
 * Created by hzq on 16/8/18.
 */
public class curatorUtils {

    private static CuratorFramework client;

    static {
        client = CuratorFrameworkFactory.newClient("192.168.29.101:2181", new ExponentialBackoffRetry(100, 3));
        client.start();
    }


    @Test
    /**
     * zkPaths
     */
    public void test01() throws Exception {
        String path = "/zkPath";
        ZooKeeper zooKeeper = client.getZookeeperClient().getZooKeeper();
        System.out.println(ZKPaths.fixForNamespace(path, "/sub"));
        System.out.println(ZKPaths.makePath(path, "sub"));
        System.out.println(ZKPaths.getNodeFromPath("/zkPath/sub1"));
        ZKPaths.PathAndNode pn = ZKPaths.getPathAndNode("/zkPath/sub1");
        System.out.println(pn.getPath());
        System.out.println(pn.getNode());

        String dir1 = path + "/child1";
        String dir2 = path + "/child2";
        ZKPaths.mkdirs(zooKeeper, dir1);
        ZKPaths.mkdirs(zooKeeper, dir2);
        System.out.println(ZKPaths.getSortedChildren(zooKeeper, path));
        ZKPaths.deleteChildren(zooKeeper, path, true);

    }

    @Test
    /**
     * ensurePath提供了确保节点存在的机制
     */
    public void test02() throws Exception {
        //way 1
        client.usingNamespace("zk-book");
        EnsurePath ensurePath = new EnsurePath("/zk-book/23");
        ensurePath.ensure(client.getZookeeperClient());
        //way 2
        EnsurePath ensurePath1 = client.newNamespaceAwareEnsurePath("/c1");
        ensurePath1.ensure(client.getZookeeperClient());
    }


    @Test
    /**
     * testingServer
     */
    public void test03() throws Exception {
        TestingCluster cluster = new TestingCluster(3);
        cluster.start();
        TimeUnit.SECONDS.sleep(3);
        for (TestingZooKeeperServer server : cluster.getServers()) {
            System.out.print(server.getInstanceSpec().getServerId() + "-");
            System.out.print(server.getQuorumPeer().getServerState() + "-");
            System.out.println(server.getInstanceSpec().getDataDirectory().getAbsolutePath());
        }
        TimeUnit.SECONDS.sleep(3333333);
        cluster.stop();


    }

}
