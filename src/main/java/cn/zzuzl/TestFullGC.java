package cn.zzuzl;

/**
 * Created by zhanglei53 on 2018/2/6.
 */
public class TestFullGC {
    public static void main(String[] args) throws InterruptedException {
        // List<MemoryObject> list = new ArrayList<MemoryObject>(6);
        /*for (int i = 0; i < 0; i++) {
            list.add(new MemoryObject(1024 * 1024));
        }*/
        // Thread.sleep(2000);
        // 让上面的对象尽可能转入旧生代
        /*System.gc();
        System.gc();
        Thread.sleep(2000);
        list.clear();
        for (int i = 0; i < 10; i++) {
            list.add(new MemoryObject(1024 * 1024));
            if (i % 3 == 0) {
                list.remove(0);
            }
        }
        Thread.sleep(5000);*/
    }
}

class MemoryObject {
    private byte[] bytes;

    public MemoryObject(int size) {
        this.bytes = new byte[size];
    }
}