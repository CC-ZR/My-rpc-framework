package github.rpc.provider.Impl;

import github.rpc.config.RpcServiceConfig;
import github.rpc.enums.RpcErrorMessageEnum;
import github.rpc.exception.RpcException;
import github.rpc.provider.ServiceProvider;
import github.rpc.registry.ServiceRegistry;
import github.rpc.registry.zk.ZkServiceRegistryImpl;
import github.rpc.remoting.transport.netty.server.NettyRpcServer;
import lombok.extern.slf4j.Slf4j;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 使用zookeeper实现服务的提供功能
 */
@Slf4j
public class ZkServiceProviderImpl implements ServiceProvider {
    /**
     * 存储正在提供的服务
     * key: rpc service name(interface name + version + group)
     * value: service object
     */
    private final Map<String, Object> serviceMap;
    private final Set<String> registerdService;
    private final ServiceRegistry serviceRegistry;

    public ZkServiceProviderImpl(){
        // 为了保证线程安全 不能直接使用hashmap和hashset
        serviceMap = new ConcurrentHashMap<>();
        registerdService = ConcurrentHashMap.newKeySet();
        // 采用zookeeper实现的服务注册类
        serviceRegistry = new ZkServiceRegistryImpl();
    }

    @Override
    public void addService(RpcServiceConfig rpcServiceConfig) {
        String rpcServiceName = rpcServiceConfig.getRpcServiceName();
        if(registerdService.contains(rpcServiceName)){
            return;
        }
        registerdService.add(rpcServiceName);
        serviceMap.put(rpcServiceName, rpcServiceConfig.getService());
        log.info("Add service: {} and interfaces:{}", rpcServiceName, rpcServiceConfig.getService().getClass().getInterfaces());
    }

    @Override
    public Object getService(String rpcServiceName) {
        Object service = serviceMap.get(rpcServiceName);
        if(null == service){
            throw new RpcException(RpcErrorMessageEnum.SERVICE_CAN_NOT_BE_FOUND);
        }
        return service;
    }

    @Override
    public void publishService(RpcServiceConfig rpcServiceConfig) {
        try {
            String host = InetAddress.getLocalHost().getHostAddress();
            this.addService(rpcServiceConfig);
            serviceRegistry.registerService(rpcServiceConfig.getRpcServiceName(), new InetSocketAddress(host, NettyRpcServer.PORT));
        } catch (UnknownHostException e) {
            log.error("occur exception when getHostAddress", e);
        }
    }
}
