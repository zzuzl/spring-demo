package cn.zzuzl.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

import java.net.InetSocketAddress;

public class EchoClient {
    private String host;
    private int port;

    public EchoClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public static void main(String[] args) throws Exception {
        new EchoClient("127.0.0.1", 8080).start();
    }

    public void start() throws Exception {
        Bootstrap bootstrap = new Bootstrap();
        EventLoopGroup loopGroup = new NioEventLoopGroup();

        bootstrap.group(loopGroup)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel socketChannel) throws Exception {
                ByteBuf delimiter = Unpooled.copiedBuffer("$_".getBytes());
                // socketChannel.pipeline().addLast(new DelimiterBasedFrameDecoder(1024, delimiter));
                // socketChannel.pipeline().addLast(new StringDecoder());
                socketChannel.pipeline().addLast(new EchoClientHandler());
            }
        });
        ChannelFuture future = bootstrap.connect(host, port).sync();
        future.channel().closeFuture().sync();

        // 关闭资源
        loopGroup.shutdownGracefully();
    }
}
