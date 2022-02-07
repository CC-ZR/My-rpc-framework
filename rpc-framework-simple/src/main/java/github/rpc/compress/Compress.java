package github.rpc.compress;

import github.rpc.extension.SPI;

/**
 * 定义压缩功能的通用接口
 * 为以后拓展压缩功能做考虑
 */
@SPI
public interface Compress {
    // 压缩方法
    byte[] compress(byte[] bytes);
    // 解压缩方法
    byte[] decompress(byte[] bytes);
}
