package github.rpc.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 用于枚举压缩类型的枚举类
 */
@AllArgsConstructor
@Getter
public enum CompressTypeEnum {
    GZIP((byte) 0x01, "gzip");

    private final byte code;
    private final String name;

    // 获取压缩的类型名
    public static String getName(byte code){
        for(CompressTypeEnum c : CompressTypeEnum.values()){
            if(c.code == code){
                return c.name;
            }
        }
        return null;
    }
}
