package github.rpc;

import github.rpc.registry.ServiceRegistry;
import github.rpc.registry.zk.ZkServiceRegistryImpl;
import github.rpc.registry.zk.util.CuratorUtils;
import org.apache.curator.framework.CuratorFramework;
import org.junit.Test;

import java.net.InetSocketAddress;

/**
 * 测试zookeeper是否能够跑通
 */
public class zktest {
    @Test
    public void testZk() throws Exception {
//        CuratorFramework zkClient = CuratorUtils.getZkClient();

//        zkClient.create().creatingParentsIfNeeded().forPath("/my_rpc/app1");
        ServiceRegistry serviceRegistry = new ZkServiceRegistryImpl();
        InetSocketAddress inetSocketAddress = new InetSocketAddress("127.0.0.1", 9999);
        System.out.println(inetSocketAddress.toString());
        serviceRegistry.registerService("app1", inetSocketAddress);

        // 关闭连接
//        if(zkClient != null){
//            zkClient.close();
//        }
    }
}
