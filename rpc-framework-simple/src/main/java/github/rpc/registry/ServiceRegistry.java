package github.rpc.registry;

import java.net.InetSocketAddress;

/**
 * 服务注册的定义接口
 * 由服务端进行调用
 * 需要实现
 */

public interface ServiceRegistry {
    /**
     *
     * @param rpcServiceName rpc服务名
     * @param inetSocketAddress 提供服务的地址
     */
    void registerService(String rpcServiceName, InetSocketAddress inetSocketAddress);
}
