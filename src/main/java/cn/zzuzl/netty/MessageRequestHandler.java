package cn.zzuzl.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Date;

public class MessageRequestHandler extends SimpleChannelInboundHandler<MessageRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, MessageRequestPacket messageRequestPacket) throws Exception {
        channelHandlerContext.channel().writeAndFlush(receive(messageRequestPacket));
    }

    private MessageResponsePacket receive(MessageRequestPacket messageRequestPacket) {
        MessageResponsePacket resp = new MessageResponsePacket();
        System.out.println(new Date() + ":收到客户端消息：" + messageRequestPacket.getMessage());
        resp.setMessage(messageRequestPacket.getMessage());

        return resp;
    }
}
