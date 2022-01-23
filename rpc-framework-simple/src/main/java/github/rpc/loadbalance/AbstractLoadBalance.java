package github.rpc.loadbalance;

import github.rpc.remoting.dto.RpcRequest;

import java.util.List;

/**
 * 实现loadbalance接口的抽象类
 * 设置一个doselect方法用于选取合适的服务地址
 */
public abstract class AbstractLoadBalance implements LoadBalance {
    @Override
    public String selectServiceAddress(List<String> serviceAddress, RpcRequest rpcRequest) {
        if (serviceAddress == null || serviceAddress.size() == 0) {
            return null;
        }
        if(serviceAddress.size()==1){
            return serviceAddress.get(0);
        }
        return doSelect(serviceAddress, rpcRequest);
    }

    protected abstract String doSelect(List<String> serviceAddress, RpcRequest rpcRequest);
}
