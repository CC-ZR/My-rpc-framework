package github.rpc.registry.zk;

import github.rpc.registry.ServiceRegistry;
import github.rpc.registry.zk.util.CuratorUtils;
import org.apache.curator.framework.CuratorFramework;

import java.net.InetSocketAddress;

/**
 * 本类用zookeeper实现服务注册功能
 */
public class ZkServiceRegistryImpl implements ServiceRegistry {

    @Override
    public void registerService(String rpcServiceName, InetSocketAddress inetSocketAddress) {
        String servicePath = CuratorUtils.ZK_REGISTER_ROOT_PATH + "/" + rpcServiceName + inetSocketAddress.toString();
        CuratorFramework zkClient = CuratorUtils.getZkClient();
        // 插入新的节点完成服务注册
        CuratorUtils.createPersistentNode(zkClient, servicePath);
    }
}
