package cn.zzuzl;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

public class MultiPortEcho {

    private int[] ports;
    private ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

    public MultiPortEcho(int[] ports) {
        this.ports = ports;
    }

    public static void main(String[] args) throws Exception {
        new MultiPortEcho(new int[]{9000,9001,9002}).go();
    }

    public void go() throws Exception {
        Selector selector = Selector.open();
        for (int i=0;i<ports.length;i++) {
            int port = ports[i];

            ServerSocketChannel ssc = ServerSocketChannel.open();
            ssc.configureBlocking(false);

            ServerSocket ss = ssc.socket();
            InetSocketAddress inetSocketAddress = new InetSocketAddress(port);
            ss.bind(inetSocketAddress);

            ssc.register(selector, SelectionKey.OP_ACCEPT);
            System.out.println("going to listen port" + port);
        }
        while (true) {
            int num = selector.select();
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()) {
                SelectionKey selectionKey = iterator.next();
                if ((selectionKey.readyOps() & SelectionKey.OP_ACCEPT) == SelectionKey.OP_ACCEPT) {
                    ServerSocketChannel channel = (ServerSocketChannel) selectionKey.channel();
                    SocketChannel accept = channel.accept();
                    accept.configureBlocking(false);
                    SelectionKey newKey = accept.register(selector, SelectionKey.OP_READ);
                    iterator.remove();
                } else if ((selectionKey.readyOps() & SelectionKey.OP_READ) == SelectionKey.OP_READ) {
                    SocketChannel channel = (SocketChannel) selectionKey.channel();
                    int byteEchoed = 0, r = 0;
                    byteBuffer.clear();
                    while ((r = channel.read(byteBuffer)) != -1) {
                        byteBuffer.flip();
                        channel.write(byteBuffer);
                        byteEchoed += r;
                    }
                    System.out.println("read:" + byteEchoed);
                    iterator.remove();
                }
            }
        }
    }
}
