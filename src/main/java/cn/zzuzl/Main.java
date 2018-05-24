package cn.zzuzl;

import java.lang.reflect.Method;

/**
 * Created by zhanglei53 on 2018/1/23.
 */
public class Main {
    private Integer a = null;
    private static ForReflection testObj = new ForReflection();
    private static Method method = null;
    private static final int JIT_COUNT = 20700;

    public Main() {
        System.out.println("main()");
    }

    public static void main(String[] args) throws Exception {
        System.out.println(Main.class.getClassLoader());
        System.out.println(Main.class.getClassLoader().getParent());
        System.out.println(Main.class.getClassLoader().getParent().getParent());

        // Main main = new Main();
        // main.print("hello");
        method = ForReflection.class.getMethod("execute", String.class);
        long start = System.currentTimeMillis();
        testDirectlyCall();
        System.out.println("testDirectlyCall:" + (System.currentTimeMillis() - start));
        start = System.currentTimeMillis();

        testNoCacheCall();
        System.out.println("testNoCacheCall:" + (System.currentTimeMillis() - start));
        start = System.currentTimeMillis();

        testCacheCall();
        System.out.println("testCacheCall:" + (System.currentTimeMillis() - start));
    }

    public void print(String str) {
        System.out.println(str);
        if (a == null) {
            throw new RuntimeException("error");
        }
    }

    /**
     * 直接调用
     */
    public static void testDirectlyCall() {
        for (int i = 0; i < JIT_COUNT; i++) {
            testObj.execute("hello");
        }
        System.out.println();
    }

    /**
     * 缓存method调用
     */
    public static void testCacheCall() throws Exception {
        for (int i = 0; i < JIT_COUNT; i++) {
            method.invoke(testObj, "hello");
        }
        System.out.println();
    }

    /**
     * 不缓存method调用
     */
    public static void testNoCacheCall() throws Exception {
        for (int i = 0; i < JIT_COUNT; i++) {
            Method method1 = ForReflection.class.getMethod("execute", String.class);
            method1.invoke(testObj, "hello");
        }
        System.out.println();
    }

}

class ForReflection {
    public void execute(String message) {
        String s = this.toString() + message;
    }
}