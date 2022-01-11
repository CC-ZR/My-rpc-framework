package github.rpc.registry;

import github.rpc.remoting.dto.RpcRequest;

import java.net.InetSocketAddress;

/**
 * 服务发现的接口
 * 由客户端调用
 * 需要实现
 */

public interface ServiceDiscovery {
    /**
     *
     * @param rpcRequest rpc服务请求类
     * @return 返回提供服务的地址与端口信息
     */
    InetSocketAddress lookupService(RpcRequest rpcRequest);
}
