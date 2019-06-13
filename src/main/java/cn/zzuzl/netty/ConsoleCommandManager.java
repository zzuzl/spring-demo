package cn.zzuzl.netty;

import io.netty.channel.Channel;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ConsoleCommandManager implements ConsoleCommand {
    private Map<String, ConsoleCommand> map = new HashMap<>();

    public ConsoleCommandManager() {
        // map.put("sendToUser", )
        map.put("createGroup", new CreateGroupConsoleCommand());
    }

    @Override
    public void exec(Scanner scanner, Channel channel) {
        System.out.println("输入命令：");
        String command = scanner.next();

        ConsoleCommand consoleCommand = map.get(command);

        if (consoleCommand != null) {
            consoleCommand.exec(scanner, channel);
        } else {
            System.err.println("无法识别[" + command + "]指令，请重新输入");
        }
    }
}
