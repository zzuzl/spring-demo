package cn.zzuzl;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Set;

/**
 * Created by zhanglei53 on 2018/1/22.
 */
public class NioTest {

    public static void main(String[] args) throws Exception {
        copyFileNio("/Users/zhanglei53/files/1.txt", "/Users/zhanglei53/files/2.txt");
    }

    /**
     * nio复制文件
     * @param src
     * @param target
     * @throws Exception
     */
    public static void copyFileNio(String src, String target) throws Exception {
        FileInputStream fi = new FileInputStream(src);
        FileOutputStream fo = new FileOutputStream(target);

        FileChannel fiChannel = fi.getChannel();
        FileChannel foChannel = fo.getChannel();

        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        while (fiChannel.read(byteBuffer) != -1) {
            // 重设buffer的position=0，limit=position
            byteBuffer.flip();
            foChannel.write(byteBuffer);
            // 写完要重置buffer,position=0,limit=capacity
            byteBuffer.clear();
        }
        fiChannel.close();
        foChannel.close();
        fi.close();
        fo.close();
    }

    public void nioTest() throws IOException {
        SocketChannel channel = SocketChannel.open();
        // 非阻塞模式
        channel.configureBlocking(false);
        channel.connect(null);

        Selector selector = Selector.open();
        // 向channels注册selector及感兴趣的连接事件
        channel.register(selector, SelectionKey.OP_CONNECT);

        // 阻塞等待感兴趣的事件发生，超时时间
        int nKeys = selector.select(10000);
        if (nKeys > 0) {
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            for (SelectionKey key : selectionKeys) {
                // 对于发生连接的事件
                if (key.isConnectable()) {
                    SocketChannel sc = (SocketChannel) key.channel();
                    sc.configureBlocking(false);
                    // 继续注册感兴趣的事件
                    sc.register(selector, SelectionKey.OP_READ);
                    sc.finishConnect();
                } else if(key.isReadable()) {
                    // 有流可读取
                    ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                    SocketChannel sc = (SocketChannel) key.channel();
                    int readBytes = 0;
                    try {
                        int ret = 0;
                        try {
                            while ((ret = sc.read(byteBuffer)) > 0) {
                                readBytes += ret;
                            }
                        } finally {
                            byteBuffer.flip();
                        }
                    } finally {
                        if (byteBuffer != null) {
                            byteBuffer.clear();
                        }
                    }
                } else if (key.isWritable()) {
                    // 有流可写
                }
            }
        }
    }
}