package cn.zzuzl.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class LoginRequestHandler extends SimpleChannelInboundHandler<LoginRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, LoginRequestPacket loginRequestPacket) throws Exception {
        channelHandlerContext.channel().writeAndFlush(login(loginRequestPacket));
    }

    private LoginResponsePacket login(LoginRequestPacket loginRequestPacket) {
        LoginResponsePacket resp = new LoginResponsePacket();
        resp.setSuccess(true);
        resp.setVersion(loginRequestPacket.getVersion());
        return resp;
    }
}
