package github.rpc.remoting.transport.netty.client;

import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 存储并获取channel对象
 * channel对象是NIO中用来与服务端通信的
 */

@Slf4j
public class ChannelProvider {
    private final Map<String, Channel> channelMap;

    public ChannelProvider(){
        channelMap = new ConcurrentHashMap<>();
    }

    // 需要的时候 获取channel
    public Channel get(InetSocketAddress inetSocketAddress){
        String key = inetSocketAddress.toString();
        // 检查是否已经有基于这个地址的channel了
        if(channelMap.containsKey(key)){
            Channel channel = channelMap.get(key);
            if(channel != null && channel.isActive()){
                return channel;
            }else{
                channelMap.remove(key); // channel已经不活动了 删掉
            }
        }
        return null;
    }

    public void set(InetSocketAddress inetSocketAddress, Channel channel){
        String key = inetSocketAddress.toString();
        channelMap.put(key, channel);
    }

    public void remove(InetSocketAddress inetSocketAddress) {
        String key = inetSocketAddress.toString();
        channelMap.remove(key);
        log.info("Channel map size :[{}]", channelMap.size());
    }
}
