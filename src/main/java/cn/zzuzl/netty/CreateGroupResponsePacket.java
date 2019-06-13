package cn.zzuzl.netty;

import java.util.List;

public class CreateGroupResponsePacket extends Packet {
    private boolean success;
    private String groupId;
    private List<String> usernameList;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public List<String> getUsernameList() {
        return usernameList;
    }

    public void setUsernameList(List<String> usernameList) {
        this.usernameList = usernameList;
    }

    @Override
    public Byte getCommand() {
        return Command.GROUP_RESPONSE;
    }
}
