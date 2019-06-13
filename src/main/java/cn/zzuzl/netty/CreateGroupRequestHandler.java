package cn.zzuzl.netty;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CreateGroupRequestHandler extends SimpleChannelInboundHandler<CreateGroupRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, CreateGroupRequestPacket msg) throws Exception {
        List<Integer> userIdList = msg.getUserIdList();

        List<String> userNameList = new ArrayList<>();
        ChannelGroup group = new DefaultChannelGroup(ctx.executor());

        for (Integer userId : userIdList) {
            Channel channel = SessionUtil.getChannel(userId);
            if (channel != null) {
                group.add(channel);
                userNameList.add(SessionUtil.getSession(channel).getUsername());
            }
        }

        CreateGroupResponsePacket resp = new CreateGroupResponsePacket();
        resp.setSuccess(true);
        resp.setGroupId(UUID.randomUUID().toString());
        resp.setUsernameList(userNameList);

        group.writeAndFlush(resp);

        System.out.println("群创建成功，id：" + resp.getGroupId());
        System.out.println("群里有：" + resp.getUsernameList());

    }
}
