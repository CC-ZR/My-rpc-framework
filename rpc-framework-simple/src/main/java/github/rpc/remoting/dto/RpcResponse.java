package github.rpc.remoting.dto;

import github.rpc.enums.RpcResponseCodeEnum;
import lombok.*;

import java.io.Serializable;

/**
 * rpc请求的响应类
 * 用于向客户端返回结果
 */

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class RpcResponse<T> implements Serializable {
    private static final long serialVersionUID = 715745410605631233L;
    private String requestId;
    // response code
    private Integer code;
    // response massege
    private String message;
    // response body
    private T data;

    /**
     *
     * @param data
     * @param requestId
     * @param <T>
     * @return 调用成功了，返回数据，设置好返回体
     */
    public static <T> RpcResponse<T> success(T data, String requestId){
        RpcResponse<T> rpcResponse = new RpcResponse();
        rpcResponse.setCode(RpcResponseCodeEnum.SUCCESS.getCode());
        rpcResponse.setMessage(RpcResponseCodeEnum.SUCCESS.getMessage());
        rpcResponse.setRequestId(requestId);
        if(null != data){
            rpcResponse.setData(data);
        }
        return rpcResponse;
    }

    /**
     *
     * @param rpcResponseCodeEnum
     * @param <T>
     * @return 调用失败了 失败的可能性有多种 于是写成参数 方便扩展
     */
    public static <T> RpcResponse<T> fail(RpcResponseCodeEnum rpcResponseCodeEnum){
        RpcResponse<T> rpcResponse = new RpcResponse<>();
        rpcResponse.setCode(rpcResponseCodeEnum.getCode());
        rpcResponse.setMessage(rpcResponseCodeEnum.getMessage());
        return rpcResponse;
    }
}
