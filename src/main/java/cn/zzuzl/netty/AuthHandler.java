package cn.zzuzl.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class AuthHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (!SessionUtil.hasLogin(ctx.channel())) {
            System.out.println("没有登陆，关闭连接");
            ctx.channel().close();
        } else {
            System.out.println("已登录，无需校验，热拔插");
            ctx.pipeline().remove(this);
            super.channelRead(ctx, msg);
        }

    }
}
