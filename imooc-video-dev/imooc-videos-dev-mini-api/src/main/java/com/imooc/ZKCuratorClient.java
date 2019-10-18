package com.imooc;

import com.imooc.enums.BGMOperatorTypeEnum;
import com.imooc.utils.JsonUtils;
import org.apache.commons.io.FileUtils;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.io.File;
import java.net.URL;
import java.util.Map;


//@Component
@PropertySource("classpath:Resource.properties")
public class ZKCuratorClient {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private CuratorFramework client = null;

	@Value("${com.imooc.zookeeperServer}")
	private String zookeeperServer;

	@Value("${com.imooc.bgmServer}")
	private String bgmServer;

	@Value("${com.imooc.fileSpace}")
	private String fileSpace;

	public void init(){

		if(client != null){
			return;
		}

		RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 5);
		client = CuratorFrameworkFactory.builder().connectString(zookeeperServer)
				.sessionTimeoutMs(10000).retryPolicy(retryPolicy).namespace("admin").build();
		client.start();

		try{
			addChildWatch("/bgm");
		}catch(Exception e){
			e.printStackTrace();
		}

	}

	public void addChildWatch(String nodePath) throws Exception {
		final PathChildrenCache pathChildrenCache = new PathChildrenCache(client, nodePath, true);
		pathChildrenCache.start();
		pathChildrenCache.getListenable().addListener(new PathChildrenCacheListener() {
			@Override
			public void childEvent(CuratorFramework curatorFramework, PathChildrenCacheEvent e) throws Exception {
				if (e.getType().equals(PathChildrenCacheEvent.Type.CHILD_ADDED)){
					logger.info("{},{},{}",zookeeperServer, bgmServer, fileSpace);

					String path = e.getData().getPath();

					String zkData = new String(e.getData().getData());
					if (zkData == null){
						return;
					}

					Map<String, String> map = JsonUtils.jsonToPojo(zkData, Map.class);
					String bgmPath = map.get("path");
					String type = map.get("type");

					//本地保存路径
					String finalSavePath = fileSpace + bgmPath;

					String bgmURL = bgmServer + bgmPath;

					if (type.equals(BGMOperatorTypeEnum.ADD.type)){
						URL url = new URL(bgmURL);
						File file = new File(finalSavePath);
						FileUtils.copyURLToFile(url, file);
						client.delete().forPath(path);
					}else if (type.equals(BGMOperatorTypeEnum.DELETE.type)){
						File file = new File(finalSavePath);
						FileUtils.forceDelete(file);
						client.delete().forPath(path);
					}
				}
			}
		});
	}
}
