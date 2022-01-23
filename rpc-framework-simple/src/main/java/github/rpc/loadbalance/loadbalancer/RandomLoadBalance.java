package github.rpc.loadbalance.loadbalancer;

import github.rpc.loadbalance.AbstractLoadBalance;
import github.rpc.remoting.dto.RpcRequest;

import java.util.List;
import java.util.Random;

/**
 * 随机化获取服务地址
 * 本质上靠的是随机函数的均匀程度
 * 是否有效未知
 */
public class RandomLoadBalance extends AbstractLoadBalance {
    @Override
    protected String doSelect(List<String> serviceAddress, RpcRequest rpcRequest) {
        Random random = new Random();
        return serviceAddress.get(random.nextInt(serviceAddress.size()));
    }
}
