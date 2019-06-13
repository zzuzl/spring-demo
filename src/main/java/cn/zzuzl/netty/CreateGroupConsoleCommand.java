package cn.zzuzl.netty;

import io.netty.channel.Channel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class CreateGroupConsoleCommand implements ConsoleCommand {
    private static final String USER_ID_SPLITER = ",";

    @Override
    public void exec(Scanner scanner, Channel channel) {
        System.out.println("【拉人群聊】输入userId列表，用逗号隔开：");
        String userIds = scanner.next();
        List<String> list = Arrays.asList(userIds.split(USER_ID_SPLITER));

        CreateGroupRequestPacket req = new CreateGroupRequestPacket();

        List<Integer> idList = new ArrayList<>(list.size());
        for (String s : list) {
            idList.add(Integer.parseInt(s));
        }

        req.setUserIdList(idList);
        channel.writeAndFlush(req);
    }
}
