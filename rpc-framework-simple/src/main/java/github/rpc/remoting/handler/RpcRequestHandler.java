package github.rpc.remoting.handler;

import github.rpc.exception.RpcException;
import github.rpc.factory.SingletonFactory;
import github.rpc.provider.Impl.ZkServiceProviderImpl;
import github.rpc.provider.ServiceProvider;
import github.rpc.remoting.dto.RpcRequest;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 在这里定义一个RpcRequest的处理器
 */
@Slf4j
public class RpcRequestHandler {
    private final ServiceProvider serviceProvider;

    public RpcRequestHandler() {
        serviceProvider = SingletonFactory.getInstance(ZkServiceProviderImpl.class);
    }

    /**
     * 获取提供方法的服务，调用相关方法并返回结果
     * @param rpcRequest rpc请求对象 包含需要执行的方法的信息
     * @return 方法执行结果
     */
    public Object handle(RpcRequest rpcRequest){
        Object service = serviceProvider.getService(rpcRequest.getRpcServiceName());
        return invokeTargetMethod(rpcRequest, service);
    }

    /**
     * 调用目标方法，并返回执行结果
     * @param rpcRequest 记录了客户端的请求信息
     * @param service 提供请求的方法的服务
     * @return 方法执行的结果
     */
    private Object invokeTargetMethod(RpcRequest rpcRequest, Object service){
        Object result;
        try {
            // 利用反射机制从服务对象中获取方法
            Method method = service.getClass().getMethod(rpcRequest.getMethodName(), rpcRequest.getParamTypes());
            // 执行方法 获取方法执行结果
            result = method.invoke(service, rpcRequest.getParameters());
            log.info("service:[{}] successful invoke method:[{}]", rpcRequest.getInterfaceName(), rpcRequest.getMethodName());
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            throw new RpcException(e.getMessage(), e);
        }
        return result;
    }
}
