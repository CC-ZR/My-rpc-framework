package github.rpc.config;

import lombok.*;

/**
 * 配置rpc服务
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class RpcServiceConfig {
    // 服务版本
    private String version;
    // 当一个接口有多个实现类的时候，用组号来标识
    private String group;
    // 目标服务对象
    private Object service;

    public String getRpcServiceName() {
        return this.getServiceName() + this.getGroup() + this.getVersion();
    }

    public String getServiceName() {
        return this.service.getClass().getInterfaces()[0].getCanonicalName();
    }
}
