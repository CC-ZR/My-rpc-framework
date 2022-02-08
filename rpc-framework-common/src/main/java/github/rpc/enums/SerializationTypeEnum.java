package github.rpc.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 枚举序列化器信息的类
 */
@AllArgsConstructor
@Getter
public enum SerializationTypeEnum {
    KRYO((byte) 0x01, "kryo"),
    PROTOBUFF((byte) 0x02, "protobuff");

    private final byte code;
    private final String name;

    public static String getName(byte code){
        for (SerializationTypeEnum c : SerializationTypeEnum.values()) {
            if (c.getCode() == code) {
                return c.name;
            }
        }
        return null;
    }
}
