package cn.zzuzl.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Date;

public class MessageResponseHandler extends SimpleChannelInboundHandler<MessageResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, MessageResponsePacket messageResponsePacket) throws Exception {
        Integer fromUserId = messageResponsePacket.getFromUserId();
        String fromUsername = messageResponsePacket.getFromUsername();
        System.out.println(fromUserId + ":" + fromUsername + " -> " + messageResponsePacket.getMessage());
    }

}
