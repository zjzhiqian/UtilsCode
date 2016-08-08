package com.hzq.zookeeper;

import com.alibaba.fastjson.JSON;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;

import java.util.concurrent.TimeUnit;

/**
 * Created by hzq on 16/7/27.
 */
public class SetConfig {
    public static final String url = "192.168.145.202:2181";


    public static final String root = "/myConf";
    public static final String urlNode = root + "/"+url;
    public static final String userNameNode = root + "/username";
    public static final String passwordNode = root + "/password";

    public static final String auth_type = "digest";
    public static final String auth_psw = "password";

    public static final String urlStr = "10.1.1.1";
    public static final String nameStr = "username1";
    public static final String pswStr = "password1";


    public static void main(String[] args) throws Exception {
        ZooKeeper zk = new ZooKeeper(url, 3000, watchedEvent -> System.out.println("触发事件 : " + watchedEvent.getType()));
        while (ZooKeeper.States.CONNECTED != zk.getState()) {
            TimeUnit.SECONDS.sleep(3);

//            JSON.parse()
        }

        zk.addAuthInfo(auth_type, auth_psw.getBytes());


        if (zk.exists(root, true) == null) {
            zk.create(root, "root".getBytes(), ZooDefs.Ids.CREATOR_ALL_ACL, CreateMode.PERSISTENT);
        }

        if (zk.exists(urlNode, true) == null) {
            zk.create(urlNode, urlStr.getBytes(), ZooDefs.Ids.CREATOR_ALL_ACL, CreateMode.PERSISTENT);
        }

        if (zk.exists(userNameNode, true) == null) {
            zk.create(userNameNode, nameStr.getBytes(), ZooDefs.Ids.CREATOR_ALL_ACL, CreateMode.PERSISTENT);
        }

        if (zk.exists(passwordNode, true) == null) {
            zk.create(passwordNode, pswStr.getBytes(), ZooDefs.Ids.CREATOR_ALL_ACL, CreateMode.PERSISTENT);
        }
        zk.close();


    }
}
