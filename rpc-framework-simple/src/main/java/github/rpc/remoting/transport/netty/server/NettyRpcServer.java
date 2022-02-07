package github.rpc.remoting.transport.netty.server;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * netty服务端
 * 作用是接受客户端的message，然后根据信息调用对应的方法，最后将调用结果返回给客户端
 */


@Slf4j
@Component
public class NettyRpcServer {
    public static final int PORT = 9998;
}
