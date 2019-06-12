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
import org.apache.zookeeper.Login;

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
        Scanner scanner = new Scanner(System.in);
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (!Thread.interrupted()) {
                    if (!SessionUtil.hasLogin(channel)) {
                        System.out.println("输入用户名登陆");
                        String line = scanner.nextLine();
                        LoginRequestPacket packet = new LoginRequestPacket();
                        packet.setUsername(line);
                        packet.setPassword("pwd");

                        channel.writeAndFlush(packet);
                        waitForLoginResponse();
                    } else {
                        Integer toUserId = scanner.nextInt();
                        String message = scanner.next();

                        MessageRequestPacket packet = new MessageRequestPacket();
                        packet.setToUserId(toUserId);
                        packet.setMessage(message);
                        channel.writeAndFlush(packet);
                    }
                }
            }
        }).start();
    }

    private static void waitForLoginResponse() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {

        }
    }
}
