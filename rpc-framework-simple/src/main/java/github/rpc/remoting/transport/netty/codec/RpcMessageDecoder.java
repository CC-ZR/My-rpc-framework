package github.rpc.remoting.transport.netty.codec;

import github.rpc.compress.Compress;
import github.rpc.compress.gzip.GzipCompress;
import github.rpc.remoting.constants.RpcConstants;
import github.rpc.remoting.dto.RpcMessage;
import github.rpc.remoting.dto.RpcRequest;
import github.rpc.remoting.dto.RpcResponse;
import github.rpc.serialize.Serializer;
import github.rpc.serialize.kryo.KryoSerializer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

/**
 * custom protocol decoder
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
 * <p>
 * {@link LengthFieldBasedFrameDecoder} is a length-based decoder , used to solve TCP unpacking and sticking problems.
 * </p>
 */

/**
 * 自定义rpc的编解码器
 * 此处定义解码器
 * 从数据流中还原出包
 */
@Slf4j
public class RpcMessageDecoder extends LengthFieldBasedFrameDecoder {
    // 无参构造函数
    public RpcMessageDecoder() {
        /**
         * 参数解释：
         * 第一个就是最大帧长，我们自己定义的
         * 第二个用于定位包长的数据在哪，我们之前有4B的魔数和1B的版本，所以是5
         * 第三个就是表示长度是多少 我们是4B 一个int
         * 第四个确定剩余的长度 4 + 5 = 9,所以要减9
         * 第五个参数表示要剥离多少个字节 我们自己确认魔数和版本，所以一个都不要删
         */
        this(RpcConstants.MAX_FRAME_LENGTH, 5, 4, -9, 0);
    }

    public RpcMessageDecoder(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength, int lengthAdjustment, int initialBytesToStrip) {
        super(maxFrameLength, lengthFieldOffset, lengthFieldLength, lengthAdjustment, initialBytesToStrip);
    }

    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        Object decoded = super.decode(ctx, in);
        if (ctx instanceof ByteBuf) {
            ByteBuf frame = (ByteBuf) decoded;
            if(frame.readableBytes() >= RpcConstants.TOTAL_LENGTH){
                try {
                    return decodeFrame(frame);
                } catch (Exception e) {
                    log.error("Decode frame error!", e);
                    throw e;
                } finally {
                    frame.release();
                }
            }
        }
        return decoded;
    }


    // 将帧解码
    private Object decodeFrame(ByteBuf in) {
        // 按顺序读入字节来判断
        checkMagicNumber(in);
        checkVersion(in);
        int fullLength = in.readInt();
        // 开始抽取信息构建RpcMessage对象
        byte messageType = in.readByte();
        byte codecType = in.readByte();
        byte compressType = in.readByte();
        int requestId = in.readInt();
        RpcMessage rpcMessage = RpcMessage.builder()
                .codec(codecType)
                .requestId(requestId)
                .messageType(messageType)
                .compress(compressType)
                .build();
        // 检查是否是心跳机制的包
        if (messageType == RpcConstants.HEARTBEAT_REQUEST_TYPE) {
            rpcMessage.setData(RpcConstants.PING);
            return rpcMessage;
        }
        if (messageType == RpcConstants.HEARTBEAT_RESPONSE_TYPE) {
            rpcMessage.setData(RpcConstants.PONG);
            return rpcMessage;
        }
        // 还原数据
        int bodyLength = fullLength - RpcConstants.HEAD_LENGTH;
        if (bodyLength != 0) {
            byte[] b = new byte[bodyLength];
            in.readBytes(b);
            // 先解压缩
            Compress compress = new GzipCompress();
            b = compress.decompress(b);
            // 再反序列化
            Serializer serializer = new KryoSerializer();
            if (messageType == RpcConstants.REQUEST_TYPE) {
                rpcMessage.setData(serializer.deserialize(b, RpcRequest.class));
            } else {
                rpcMessage.setData(serializer.deserialize(b, RpcResponse.class));
            }
        }
        return rpcMessage;
    }


    // 读取帧的前四个字节，检查魔数是否正确
    private void checkMagicNumber(ByteBuf in) {
        int len = RpcConstants.MAGIC_NUMBER.length;
        byte[] temp = new byte[len];
        in.readBytes(temp); // 读入四个字节
        for (int i = 0; i < len; ++i) {
            if (temp[i] != RpcConstants.MAGIC_NUMBER[i]) {
                throw new IllegalArgumentException("Unknown magic code: " + Arrays.toString(temp));
            }
        }
    }

    // 检查版本是否正确
    private void checkVersion(ByteBuf in) {
        byte version = in.readByte();
        if (version != RpcConstants.VERSION) {
            throw new RuntimeException("version isn't compatible" + version);
        }
    }
}
