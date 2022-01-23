package github.rpc.registry.zk;

import github.rpc.extension.ExtensionLoader;
import github.rpc.loadbalance.LoadBalance;
import github.rpc.loadbalance.loadbalancer.RandomLoadBalance;
import github.rpc.registry.ServiceDiscovery;
import github.rpc.remoting.dto.RpcRequest;

import java.net.InetSocketAddress;

/**
 * 基于zookeeper实现服务发现功能的类
 */
public class ZkServiceDiscoveryImpl implements ServiceDiscovery {
    private final LoadBalance loadBalance;

    public ZkServiceDiscoveryImpl(){
        // 暂时先用这个替代
//        loadBalance = new RandomLoadBalance();
        // 利用extensionloader进行管理
        this.loadBalance = ExtensionLoader.getExtensionLoader(LoadBalance.class).getExtension("loadBalance");
    }

    @Override
    public InetSocketAddress lookupService(RpcRequest rpcRequest) {

        return null;
    }
}
