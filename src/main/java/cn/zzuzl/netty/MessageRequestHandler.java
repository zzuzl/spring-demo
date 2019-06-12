package cn.zzuzl.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Date;

public class MessageRequestHandler extends SimpleChannelInboundHandler<MessageRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, MessageRequestPacket messageRequestPacket) throws Exception {
        Session session = SessionUtil.getSession(channelHandlerContext.channel());

        MessageResponsePacket responsePacket = new MessageResponsePacket();
        responsePacket.setFromUserId(session.getUserId());
        responsePacket.setFromUsername(session.getUsername());
        responsePacket.setMessage(messageRequestPacket.getMessage());

        Channel channel = SessionUtil.getChannel(messageRequestPacket.getToUserId());

        if (channel != null && SessionUtil.hasLogin(channel)) {
            channel.writeAndFlush(responsePacket);
        } else {
            System.err.println("[" + messageRequestPacket.getToUserId() + "] 不在线,发送失败");
        }

    }

    private MessageResponsePacket receive(MessageRequestPacket messageRequestPacket) {
        MessageResponsePacket resp = new MessageResponsePacket();
        System.out.println(new Date() + ":收到客户端消息：" + messageRequestPacket.getMessage());
        resp.setMessage(messageRequestPacket.getMessage());

        return resp;
    }
}
