package cn.zzuzl.netty;

import java.util.List;

public class CreateGroupRequestPacket extends Packet {
    private List<Integer> userIdList;

    @Override
    public Byte getCommand() {
        return Command.GROUP_REQUEST;
    }

    public List<Integer> getUserIdList() {
        return userIdList;
    }

    public void setUserIdList(List<Integer> userIdList) {
        this.userIdList = userIdList;
    }
}
