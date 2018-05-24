package cn.zzuzl.nio;

public class TimeServer {
    private static final int port = 8080;

    public static void main(String[] args) throws Exception {
        new Thread(new MultiplexerTimeServer(port)).start();
    }
}
