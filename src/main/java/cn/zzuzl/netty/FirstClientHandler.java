package cn.zzuzl.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.Charset;
import java.util.Date;

public class FirstClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(new Date() + ":客户端写出数据");

        // 获取数据
        ByteBuf buf = getByteBuf(ctx);

        // 写数据
        ctx.channel().writeAndFlush(buf);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf) msg;

        System.out.println(new Date() + ":客户端读取到数据 -> " + buf.toString(Charset.forName("utf-8")));
    }

    private ByteBuf getByteBuf(ChannelHandlerContext ctx) {
        // 获取二进制抽象
        ByteBuf buffer = ctx.alloc().buffer();

        // 准备数据，指定字符串的字符集为utf-8
        byte[] bytes = "hello".getBytes(Charset.forName("utf-8"));

        // 填充buf
        buffer.writeBytes(bytes);

        return buffer;
    }
}
