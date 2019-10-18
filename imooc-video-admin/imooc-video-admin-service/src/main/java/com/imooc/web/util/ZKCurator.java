package com.imooc.web.util;

import org.apache.curator.framework.CuratorFramework;
import org.apache.log4j.Logger;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;


public class ZKCurator {

    private Logger logger = Logger.getLogger(this.getClass());

    private CuratorFramework client;

    public ZKCurator(CuratorFramework client){
        this.client = client;
    }

    public void init(){
        client = client.usingNamespace("admin");

        try{
            if (client.checkExists().forPath("/bgm") == null){
                client.create().creatingParentsIfNeeded()
                        .withMode(CreateMode.PERSISTENT)
                        .withACL(ZooDefs.Ids.OPEN_ACL_UNSAFE)
                        .forPath("/bgm");
                logger.info("zookeeper服务器初始化成功！");
            }
        }catch (Exception e){
            logger.error("zookeeper服务器初始化错误！");
            e.printStackTrace();
        }
    }

    public void sendBgmOperator(String bgmId, String open){
        try{
            client.create().creatingParentsIfNeeded()
                    .withMode(CreateMode.PERSISTENT)
                    .withACL(ZooDefs.Ids.OPEN_ACL_UNSAFE)
                    .forPath("/bgm/" + bgmId, open.getBytes());
        }catch(Exception e){
            e.printStackTrace();
        }
    }

}
