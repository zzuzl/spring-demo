package cn.zzuzl;

import org.junit.Test;

public class TestJoin {

    @Test
    public void testJoin() throws InterruptedException {
        System.out.println(Thread.currentThread().getName());
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i=0;i<1000;i++) {
                    System.out.println(Thread.currentThread().getName() + i);
                }
            }
        },"thread1");

        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i=0;i<1000;i++) {
                    System.out.println(Thread.currentThread().getName() + i);
                }
            }
        },"thread2");
        thread.start();
        thread2.start();

        System.out.println("join");
        thread.join();
        thread2.join();
        System.out.println("end");
    }

    @Test
    public void testString() {
        String s1 = new String("1");
        s1.intern();
        String s2 = "1";
        System.out.println(s1 == s2);
    }
}
