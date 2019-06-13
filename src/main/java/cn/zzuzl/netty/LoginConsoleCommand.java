package cn.zzuzl.netty;

import io.netty.channel.Channel;

import java.util.Scanner;

public class LoginConsoleCommand implements ConsoleCommand {
    @Override
    public void exec(Scanner scanner, Channel channel) {
        System.out.println("输入用户名登陆");
        String line = scanner.nextLine();
        LoginRequestPacket packet = new LoginRequestPacket();
        packet.setUsername(line);
        packet.setPassword("pwd");

        channel.writeAndFlush(packet);
    }
}
