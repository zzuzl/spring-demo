package cn.zzuzl;

import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by zhanglei53 on 2018/1/26.
 */
public class CollectionTest {

    @Test
    public void testList() {
        // HashSet 基于HashMap实现，无序，非线程安全
        Set<String> set = new HashSet<String>();

        // treeSet 基于treeMap实现，有序，非线程安全
        Set<String> treeSet = new TreeSet<String>();

        // 非线程安全
        Map<String, String> hashMap = new HashMap<String, String>(5);
        hashMap.put("a", "1");

        // 非线程安全
        Map<String, String> treeMap = new TreeMap<String, String>();
    }

    @Test
    public void testConcurrent() {
        // 线程安全，并发高
        ConcurrentMap<String, String> map = new ConcurrentHashMap<String, String>();

        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<String>();

        BlockingQueue<String> queue = new ArrayBlockingQueue<String>(5);

        AtomicInteger atomicInteger = new AtomicInteger(1);
    }

    @Test
    public void testThread() {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 3, 1L, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(10));
        executor.execute(null);

        FutureTask<String> task = new FutureTask<String>(null);
    }

    @Test
    public void testLock() throws InterruptedException {
        ReentrantLock lock = new ReentrantLock();
        lock.lock();
        lock.unlock();

        Condition condition = lock.newCondition();
        condition.await();

        condition.signal();
    }

    @Test
    public void testSerializable() throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
        objectOutputStream.writeObject(new Object());
        objectOutputStream.close();
        outputStream.close();
        byte[] bytes = outputStream.toByteArray();
    }
}