package cn.zzuzl.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.Charset;
import java.util.Date;

public class ServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf) msg;

        Packet packet = PacketCodeC.INSTANCE.decode(buf);
        LoginResponsePacket resp = new LoginResponsePacket();
        if (packet instanceof LoginRequestPacket) {
            LoginRequestPacket requestPacket = (LoginRequestPacket) packet;

            resp.setVersion(requestPacket.getVersion());
            if (valid(requestPacket)) {
                resp.setSuccess(true);
                resp.setReason("登陆成功");
                LoginUtil.markAsLogin(ctx.channel());
            } else {
                resp.setSuccess(false);
                resp.setReason("用户名或密码错误");
            }
            ByteBuf out = PacketCodeC.INSTANCE.encode(resp);
            ctx.channel().writeAndFlush(out);
        } else if (packet instanceof MessageRequestPacket) {
            MessageRequestPacket requestPacket = (MessageRequestPacket) packet;
            System.out.println(new Date() + ":收到客户端消息:" + requestPacket.getMessage());

            MessageResponsePacket responsePacket = new MessageResponsePacket();
            responsePacket.setMessage("服务端回复【" + requestPacket.getMessage() + "】");
            ByteBuf out = PacketCodeC.INSTANCE.encode(responsePacket);
            ctx.channel().writeAndFlush(out);
        } else {
            System.out.println("无法识别的消息类型");
        }
    }

    private boolean valid(LoginRequestPacket requestPacket) {
        return true;
    }

    private ByteBuf getByteBuf(ChannelHandlerContext ctx) {
        // 获取二进制抽象
        ByteBuf buffer = ctx.alloc().buffer();

        // 准备数据，指定字符串的字符集为utf-8
        byte[] bytes = "hi".getBytes();

        // 填充buf
        buffer.writeBytes(bytes);

        return buffer;
    }
}
