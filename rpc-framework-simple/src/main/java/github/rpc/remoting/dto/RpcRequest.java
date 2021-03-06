package github.rpc.remoting.dto;

import lombok.*;

import java.io.Serializable;

/**
 * rpc请求的类定义
 * 每个rpc请求都需要按照这个结构进行组装
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@ToString
public class RpcRequest implements Serializable {
    private static final long serialVersionUID = 1905122041950251207L;
    private String requestId;
    private String interfaceName;
    private String methodName;
    private Object[] parameters;
    private Class<?>[] paramTypes;
    private String version;
    private String group;

    /**
     *
     * @return 返回组装好的rpc服务名，用于在注册中心中注册
     */
    public String getRpcServiceName() {
        return this.getInterfaceName() + this.getGroup() + this.getVersion();
    }

    public void testMethodName(){
        this.interfaceName = "app1";
        this.group = "";
        this.version = "";
    }
}
