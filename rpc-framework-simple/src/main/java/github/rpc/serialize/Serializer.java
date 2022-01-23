package github.rpc.serialize;

import github.rpc.extension.SPI;

/**
 * 序列化的接口，所有的序列化方式都要实现这个接口
 */
@SPI
public interface Serializer {
    /**
     * 将对象序列化
     * @param obj 需要进行序列化的对象
     * @return 序列化之后的结果字节数组
     */
    byte[] serialize(Object obj);


    /**
     * 将序列化之后的字符数组反序列化为对象
     * @param bytes 序列化后的字符数组
     * @param clazz 对象的类
     * @param <T>
     * @return 反序列化的对象
     */
    <T> T deserialize(byte[] bytes, Class<T> clazz);
}
