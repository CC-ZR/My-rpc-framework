package github.rpc.remoting.dto;

import lombok.*;

/**
 * 本类包裹在rpcRequest和rpcResponse之外
 * 除了二者已有的信息之外，还附带了别的信息
 * 例如消息类型，序列化方式还有压缩方式等
 * 并且附带了请求的id 用于识别是哪一个请求
 * data中就存着request 和 response
 * 本类在实际构造netty发送帧的时候，可以更好地提供信息
 */

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class RpcMessage {
    //rpc message type
    private byte messageType;
    //serialization type
    private byte codec;
    //compress type
    private byte compress;
    //request id
    private int requestId;
    //request data
    private Object data;
}
