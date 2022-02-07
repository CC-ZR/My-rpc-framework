package github.rpc.provider;

import github.rpc.config.RpcServiceConfig;
import github.rpc.enums.RpcConfigEnum;

/**
 * 存储并提供服务对象
 */
public interface ServiceProvider {
    /**
     * @param rpcServiceConfig 里面包含了服务相关的一些属性
     */
    void addService(RpcServiceConfig rpcServiceConfig);

    /**
     * @param rpcServiceName rpc服务的名字
     * @return
     */
    Object getService(String rpcServiceName);

    /**
     * 注册服务
     * @param rpcServiceConfig rpc服务相关的一些属性
     */
    void publishService(RpcServiceConfig rpcServiceConfig);
}
