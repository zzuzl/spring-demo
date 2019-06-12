package cn.zzuzl.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class LoginRequestHandler extends SimpleChannelInboundHandler<LoginRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, LoginRequestPacket loginRequestPacket) throws Exception {
        String username = loginRequestPacket.getUsername();
        Integer userId = Math.abs(username.hashCode());

        channelHandlerContext.channel().writeAndFlush(login(loginRequestPacket));
        System.out.println("客户端登陆成功: " + userId);

        Session session = new Session();
        session.setUserId(userId);
        session.setUsername(username);
        SessionUtil.markAsLogin(channelHandlerContext.channel(), session);
        SessionUtil.bindSession(session, channelHandlerContext.channel());
    }

    private LoginResponsePacket login(LoginRequestPacket loginRequestPacket) {
        LoginResponsePacket resp = new LoginResponsePacket();
        resp.setSuccess(true);
        resp.setVersion(loginRequestPacket.getVersion());
        return resp;
    }
}
