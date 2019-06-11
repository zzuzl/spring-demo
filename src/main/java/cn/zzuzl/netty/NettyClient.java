package cn.zzuzl.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

import java.util.Scanner;

public class NettyClient {

    public static void main(String[] args) {
        NioEventLoopGroup worker = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap();
        bootstrap.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                .option(ChannelOption.TCP_NODELAY, true)
                .option(ChannelOption.SO_KEEPALIVE, true);

        // 指定线程模型
        bootstrap.group(worker)
                // 指定IO类型为nio
                .channel(NioSocketChannel.class)
                // io处理逻辑
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    public void initChannel(SocketChannel ch) {
                        ch.pipeline().addLast(new PacketDecoder());
                        ch.pipeline().addLast(new LoginResponseHandler());
                        ch.pipeline().addLast(new MessageResponseHandler());
                        ch.pipeline().addLast(new PacketEncoder());
                    }
                });

        connect(bootstrap, "127.0.0.1", 8000, 0);
    }

    private static void connect(Bootstrap bootstrap, String host, int port, int retry) {
        bootstrap.connect(host, port).addListener(new GenericFutureListener<Future<? super Void>>() {
            @Override
            public void operationComplete(Future<? super Void> future) throws Exception {
                if (future.isSuccess()) {
                    Channel channel = ((ChannelFuture) future).channel();
                    startConsoleThread(channel);
                }
            }
        });
    }

    private static void startConsoleThread(Channel channel) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (!Thread.interrupted()) {
                    if (LoginUtil.hasLogin(channel)) {
                        System.out.println("输入消息发送到服务端");
                        Scanner scanner = new Scanner(System.in);
                        String line = scanner.nextLine();

                        MessageRequestPacket packet = new MessageRequestPacket();
                        packet.setMessage(line);
                        channel.writeAndFlush(PacketCodeC.INSTANCE.encode(packet));
                    }
                }
            }
        }).start();
    }
}
