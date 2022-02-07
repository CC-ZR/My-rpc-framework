package github.rpc.remoting.transport.netty.codec;

/**
 * <p>
 * custom protocol decoder
 * <p>
 * <pre>
 *   0     1     2     3     4        5     6     7     8         9          10      11     12  13  14   15 16
 *   +-----+-----+-----+-----+--------+----+----+----+------+-----------+-------+----- --+-----+-----+-------+
 *   |   magic   code        |version | full length         | messageType| codec|compress|    RequestId       |
 *   +-----------------------+--------+---------------------+-----------+-----------+-----------+------------+
 *   |                                                                                                       |
 *   |                                         body                                                          |
 *   |                                                                                                       |
 *   |                                        ... ...                                                        |
 *   +-------------------------------------------------------------------------------------------------------+
 * 4B  magic code（魔法数）   1B version（版本）   4B full length（消息长度）    1B messageType（消息类型）
 * 1B compress（压缩类型） 1B codec（序列化类型）    4B  requestId（请求的Id）
 * body（object类型数据）
 * </pre>
 */

import github.rpc.enums.CompressTypeEnum;
import github.rpc.remoting.constants.RpcConstants;
import github.rpc.remoting.dto.RpcMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 自定义rpc的编解码器
 * 此处定义编码器
 */
public class RpcMassegeEncoder extends MessageToByteEncoder<RpcMessage> {
    // 原子运算整数，保证一些操作，如自增的操作的原子性，用于处理多线程的情况
    public static final AtomicInteger ATOMIC_INTEGER = new AtomicInteger(0);

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, RpcMessage rpcMessage, ByteBuf out) throws Exception {
        out.writeBytes(RpcConstants.MAGIC_NUMBER);
        out.writeByte(RpcConstants.VERSION);
        // 空出4B的空间给消息长度（因为现在还不知道）
        out.writerIndex(out.writerIndex() + 4);
        // 继续添加剩下的头部
        out.writeByte(rpcMessage.getMessageType());
        out.writeByte(rpcMessage.getCodec());
        out.writeByte(CompressTypeEnum.GZIP.getCode());
        out.writeInt(ATOMIC_INTEGER.getAndIncrement());
        // 开始计算全长
        byte[] bodyBytes = null;
        int fullLength = RpcConstants.HEAD_LENGTH;
    }
}
