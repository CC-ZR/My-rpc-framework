package github.rpc.loadbalance;

import github.rpc.remoting.dto.RpcRequest;

import java.util.List;

/**
 * 定义负载均衡用的接口
 * 本质上从服务地址的list中选一个出来用
 * 考虑到负载均衡，在选取上应该用比较合适的方法
 */
public interface LoadBalance {
    String selectServiceAddress(List<String> serviceAdress, RpcRequest rpcRequest);
}
