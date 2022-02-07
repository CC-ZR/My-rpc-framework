package github.rpc.remoting.transport.netty.server;

import github.rpc.config.RpcServiceConfig;
import github.rpc.factory.SingletonFactory;
import github.rpc.provider.Impl.ZkServiceProviderImpl;
import github.rpc.provider.ServiceProvider;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * netty服务端
 * 作用是接受客户端的message，然后根据信息调用对应的方法，最后将调用结果返回给客户端
 */


@Slf4j
@Component
public class NettyRpcServer {
    // 设定rpc服务的端口号
    public static final int PORT = 9998;
    // 获取服务提供器
    private final ServiceProvider serviceProvider = SingletonFactory.getInstance(ZkServiceProviderImpl.class);

    // 手动注册服务的方法
    public void registerService(RpcServiceConfig rpcServiceConfig){
        serviceProvider.publishService(rpcServiceConfig);
    }

    @SneakyThrows
    public void start(){

    }

}
