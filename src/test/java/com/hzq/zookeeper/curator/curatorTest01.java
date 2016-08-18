package com.hzq.zookeeper.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.BackgroundCallback;
import org.apache.curator.framework.api.CuratorEvent;
import org.apache.curator.framework.recipes.atomic.AtomicValue;
import org.apache.curator.framework.recipes.atomic.DistributedAtomicInteger;
import org.apache.curator.framework.recipes.cache.*;
import org.apache.curator.framework.recipes.leader.LeaderSelector;
import org.apache.curator.framework.recipes.leader.LeaderSelectorListenerAdapter;
import org.apache.curator.framework.recipes.locks.InterProcessMultiLock;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.retry.RetryNTimes;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

/**
 * curatorTest01
 * Created by hzq on 16/8/11.
 */
public class curatorTest01 {

    @Test
    /**
     * 异步创建节点
     */
    public void test01() throws Exception {
        ExecutorService service = Executors.newFixedThreadPool(3);
        ExponentialBackoffRetry backoffRetry = new ExponentialBackoffRetry(100, 3); //初始sleep时间,最大重试次数
        CuratorFramework client = CuratorFrameworkFactory.newClient("192.168.29.101:2181", backoffRetry);
        client.start();
        client.create()
                .withMode(CreateMode.EPHEMERAL)
                .inBackground((client1, event) -> System.out.println("code:" + event.getResultCode() + ",type:" + event.getType()), service)   //可以传入线程池,不传入的话默认使用zk的EventThread来处理
                .forPath("/tt", "init".getBytes());


        client.close();
    }

    @Test
    /**
     * nodeCache 监听节点变化
     *
     * @throws Exception
     */
    public void test02() throws Exception {
//        ExecutorService service = Executors.newFixedThreadPool(3);
        ExponentialBackoffRetry backoffRetry = new ExponentialBackoffRetry(100, 3); //初始sleep时间,最大重试次数
        CuratorFramework client = CuratorFrameworkFactory.newClient("192.168.29.101:2181", backoffRetry);
        client.start();

        NodeCache nodeCache = new NodeCache(client, "/tt", false); //1.客户端实例 2.路径 3.是否进行数据压缩
        nodeCache.start(true); //true 第一次启动时就会从zk读取对应数据内容 存到Cache中
        nodeCache.getListenable().addListener(() ->
                //nodeCache能监听节点是否存在   如果节点不存在,就会在节点创建时出发listener  如果节点被删除无法触发
                System.out.println("node data updated ,new data : " + new String(nodeCache.getCurrentData().getData()))
        );
        TimeUnit.SECONDS.sleep(999);
        client.close();
    }

    @Test
    /**
     * childNodeCache 监听子节点变化
     * @throws Exception
     */
    public void test03() throws Exception {
        ExponentialBackoffRetry backoffRetry = new ExponentialBackoffRetry(100, 3); //初始sleep时间,最大重试次数
        CuratorFramework client = CuratorFrameworkFactory.newClient("192.168.29.101:2181", backoffRetry);
        client.start();
        PathChildrenCache pathChildrenCache = new PathChildrenCache(client, "/tt", true);
        pathChildrenCache.start(PathChildrenCache.StartMode.POST_INITIALIZED_EVENT);
        pathChildrenCache.getListenable().addListener((client1, event) -> System.out.println("type:" + event.getType() + " data:" + event.getData()));
        TimeUnit.SECONDS.sleep(999);
        client.close();
    }


    @Test
    /**
     * zk选举
     */
    public void test04() throws InterruptedException {
        ExponentialBackoffRetry backoffRetry = new ExponentialBackoffRetry(100, 3); //初始sleep时间,最大重试次数
        CuratorFramework client = CuratorFrameworkFactory.newClient("192.168.29.101:2181", backoffRetry);
        client.start();
        LeaderSelector leaderSelector = new LeaderSelector(client, "/leader/select", new LeaderSelectorListenerAdapter() {
            @Override
            public void takeLeadership(CuratorFramework client) throws Exception {
                System.out.println("being master");
                TimeUnit.SECONDS.sleep(5);
                System.out.println("lost master");
            }
        });
        leaderSelector.autoRequeue();
        leaderSelector.start();
        TimeUnit.SECONDS.sleep(200);

    }


    @Test
    /**
     * 分布式锁服务
     */
    public void test05() throws InterruptedException {
        ExponentialBackoffRetry backoffRetry = new ExponentialBackoffRetry(100, 3); //初始sleep时间,最大重试次数
        CuratorFramework client = CuratorFrameworkFactory.newClient("192.168.29.101:2181", backoffRetry);
        client.start();
        CountDownLatch countDownLatch = new CountDownLatch(1);
        InterProcessMutex lock = new InterProcessMutex(client, "/lock2");
        for (int i = 0; i < 500; i++) {
            new Thread(() -> {
                try {
                    countDownLatch.await();
                    lock.acquire();
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss|SSS");
                    System.out.println(simpleDateFormat.format(new Date()));
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        lock.release();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
        countDownLatch.countDown();
        TimeUnit.SECONDS.sleep(3000);

    }



    @Test
    /**
     * zk分布式计数器
     */
    public void test06() throws Exception {
        ExponentialBackoffRetry backoffRetry = new ExponentialBackoffRetry(100, 3); //初始sleep时间,最大重试次数
        CuratorFramework client = CuratorFrameworkFactory.newClient("192.168.29.101:2181", backoffRetry);
        client.start();

        DistributedAtomicInteger atomicInteger = new DistributedAtomicInteger(client, "/atomicPath", new RetryNTimes(3, 1000));
        AtomicValue<Integer> value = atomicInteger.add(9);
        System.out.println("result:" +value.succeeded());


    }

    @Test
    public void tessss() throws    Exception{
        ExponentialBackoffRetry backoffRetry = new ExponentialBackoffRetry(100, 3); //初始sleep时间,最大重试次数
        //参数1:url 2.会话超时时间 3.连接超时时间  4.重试策略
        CuratorFramework client = CuratorFrameworkFactory.newClient("127.0.0.1:51210", backoffRetry);
        client.start();

        client.create().forPath("/path1", "data".getBytes());

        byte[] bytes = client.getData()
                .forPath("/path");
        System.out.println(new String(bytes,"UTF-8"));
    }


    public static void main(String[] args) throws Exception {
        ExponentialBackoffRetry backoffRetry = new ExponentialBackoffRetry(100, 3); //初始sleep时间,最大重试次数
        //参数1:url 2.会话超时时间 3.连接超时时间  4.重试策略
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
