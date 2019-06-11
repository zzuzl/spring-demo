package cn.zzuzl.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.apache.zookeeper.Login;

import java.nio.charset.Charset;
import java.util.Date;
import java.util.UUID;

public class ClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(new Date() + ":客户端开始登陆");

        LoginRequestPacket packet = new LoginRequestPacket();
        packet.setUserId(1);
        packet.setUsername("falsh");
        packet.setPassword("pwd");

        ByteBuf buf = PacketCodeC.INSTANCE.encode(packet);

        // 写数据
        ctx.channel().writeAndFlush(buf);
    }

    /*@Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf) msg;

        Packet packet = PacketCodeC.INSTANCE.decode(buf);

        if (packet instanceof LoginResponsePacket) {
            LoginResponsePacket resp = (LoginResponsePacket) packet;

            if (resp.isSuccess()) {
                System.out.println(new Date() + ":客户端登陆成功");
                LoginUtil.markAsLogin(ctx.channel());
            } else {
                System.out.println(new Date() + ":客户端登陆失败：" + resp.getReason());
            }
        } else if (packet instanceof MessageResponsePacket) {
            MessageResponsePacket responsePacket = (MessageResponsePacket) packet;
            System.out.println(new Date() + ":收到服务端的消息：" + responsePacket.getMessage());
        } else {
            System.out.println("客户端未知的消息类型");
        }
    }*/

}
