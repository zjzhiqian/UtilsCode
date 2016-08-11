package com.hzq.zookeeper.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.BackgroundCallback;
import org.apache.curator.framework.api.CuratorEvent;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;
import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * curatorTest01
 * Created by hzq on 16/8/11.
 */
public class curatorTest01 {

    @Test
    /**
     * 异步创建节点
     */
    @SuppressWarnings("all")
    public void test01() throws Exception {
        ExecutorService service = Executors.newFixedThreadPool(3);
        ExponentialBackoffRetry backoffRetry = new ExponentialBackoffRetry(100, 3); //初始sleep时间,最大重试次数
        CuratorFramework client = CuratorFrameworkFactory.newClient("192.168.29.101:2181", backoffRetry);
        client.start();
        client.create()
                .withMode(CreateMode.EPHEMERAL)
                .inBackground((client1, event) -> System.out.println("code:" + event.getResultCode() + ",type:" + event.getType()), service)   //可以传入线程池,不传入的话默认使用zk的EventThread来处理
                .forPath("/tt", "init".getBytes());
        TimeUnit.SECONDS.sleep(300);
        client.close();
    }

    @Test
    /**
     *
     * @throws Exception
     */
//    @SuppressWarnings("all")
    public void test02() throws Exception {
        ExecutorService service = Executors.newFixedThreadPool(3);
        ExponentialBackoffRetry backoffRetry = new ExponentialBackoffRetry(100, 3); //初始sleep时间,最大重试次数
        CuratorFramework client = CuratorFrameworkFactory.newClient("192.168.29.101:2181", backoffRetry);
        client.start();
        client.create()
                .withMode(CreateMode.EPHEMERAL)
                .inBackground((client1, event) -> System.out.println("code:" + event.getResultCode() + ",type:" + event.getType()), service)   //可以传入线程池,不传入的话默认使用zk的EventThread来处理
                .forPath("/tt", "init".getBytes());
        TimeUnit.SECONDS.sleep(300);


        NodeCache nodeCache = new NodeCache(client, "/tt", false);
        nodeCache.start(true);
        nodeCache.getListenable().addListener(() ->
                System.out.println("node data updated ,new data : " + new String(nodeCache.getCurrentData().getData()))
        );

        TimeUnit.SECONDS.sleep(999);

        client.close();


    }


    public static void main(String[] args) throws Exception {
        ExponentialBackoffRetry backoffRetry = new ExponentialBackoffRetry(100, 3); //初始sleep时间,最大重试次数
        //参数1:url 2.重试策略 3.会话超时时间 4.连接超时时间
        CuratorFramework client = CuratorFrameworkFactory.newClient("192.168.29.101:2181", backoffRetry);
//          fluent流式创建client
//        CuratorFrameworkFactory.builder()
//                .connectString("url")
//                .sessionTimeoutMs(300)
//                .retryPolicy(backoffRetry)
//                .namespace("/t001") //操作的根路径
//                .build();
        client.start();

        //**************同步接口**********//
        //1.创建节点
        client.create().forPath("/path");
        client.create().forPath("/path1", "data".getBytes());
        client.create()
                .withMode(CreateMode.EPHEMERAL)
                .forPath("/path2", "data2".getBytes());
        client.create()
                .creatingParentsIfNeeded() //creatingParentsIfNeeded 避免了创建节点时 父节点不存在的异常,
                // zk要求所有非叶节点都是持久节点,所有经过这个操作后所有非叶节点都是持久节点
                .withMode(CreateMode.EPHEMERAL)
                .forPath("/path2", "data3".getBytes());

        //2.删除节点
        client.delete()
                .forPath("/path");  //只能删除叶节点

        client.delete()
                .deletingChildrenIfNeeded()
                .forPath("/path"); //删除节点,并递归删除子节点
        client.delete()
                .withVersion(1)  //指定版本号删除
                .forPath("/path");
        client.delete()
                .guaranteed()
                .forPath("/path"); //只要会话有效,持续进行删除操作,直到成功
        //3.读取节点
        client.getData()
                .forPath("/path");

        Stat stat = new Stat();
        client.getData()
                .storingStatIn(stat)
                .forPath("/path"); //读取节点,并设置Stat信息

        //4.更新值
        Stat stat1 = client.setData()
                .forPath("/path");
        Stat stat2 = client.setData()
                .withVersion(1)
                .forPath("/path");
        //**************同步接口**********//
    }
}
