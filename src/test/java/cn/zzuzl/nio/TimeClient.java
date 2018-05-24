package cn.zzuzl.nio;

public class TimeClient {
    private static final int port = 8080;

    public static void main(String[] args) throws Exception {
        new Thread(new TimeClientHandler("127.0.0.1", port)).start();
    }
}
