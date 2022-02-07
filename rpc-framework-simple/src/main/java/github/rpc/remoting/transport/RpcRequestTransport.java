package github.rpc.remoting.transport;

import github.rpc.extension.SPI;
import github.rpc.remoting.dto.RpcRequest;

@SPI
public interface RpcRequestTransport {
    /**
     * 向服务端发起rpc请求并获得结果
     * @param rpcRequest rpc请求体
     * @return 从服务端返回的结果
     */
    Object sendRpcRequest(RpcRequest rpcRequest);
}
