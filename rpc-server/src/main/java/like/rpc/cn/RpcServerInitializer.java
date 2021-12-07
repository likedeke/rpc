package like.rpc.cn;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import like.rpc.cn.protocol.codec.RpcDecoder;
import like.rpc.cn.protocol.codec.RpcEncoder;
import like.rpc.cn.protocol.model.rpc.RpcRequest;
import like.rpc.cn.protocol.model.rpc.RpcResponse;

import java.util.Map;

public class RpcServerInitializer extends ChannelInitializer<SocketChannel> {

    private final Map<String,Object> handlerMap;

    public RpcServerInitializer(Map<String,Object> handlerMap){
        this.handlerMap = handlerMap;
    }

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();
        pipeline.addLast(new LengthFieldBasedFrameDecoder(65536,0,4,0,0));
        pipeline.addLast(new RpcDecoder(RpcRequest.class));
        pipeline.addLast(new RpcEncoder(RpcResponse.class));
        pipeline.addLast(new RpcServerHandler(handlerMap));
    }
}
