package cn.zzuzl.zk;

import org.apache.zookeeper.*;

import java.io.IOException;

public class ZkDemo {
    private static final int SESSION_TIMEOUT = 30000;

    private ZooKeeper zooKeeper = null;

    private Watcher watcher = new Watcher() {
        @Override
        public void process(WatchedEvent watchedEvent) {
            System.out.println(watchedEvent.toString());
        }
    };

    private void zkOperations() throws KeeperException, InterruptedException {
        System.out.println("create");
        zooKeeper.create("/zoo2", "abc".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        System.out.println("查看是否创建成功");
        System.out.println(new String(zooKeeper.getData("/zoo2", false, null)));
        System.out.println("修改节点");
        zooKeeper.setData("/zoo2", "xyz".getBytes(), -1);
        System.out.println("查看是否修改成功");
        System.out.println(new String(zooKeeper.getData("/zoo2", false, null)));
        System.out.println("delete");
        zooKeeper.delete("/zoo2", -1);
        System.out.println("查看节点是否被删除");
        System.out.println("节点状态:" + zooKeeper.exists("/zoo2", false));
    }

    private void createZkInstance() throws IOException {
        zooKeeper = new ZooKeeper("localhost:2181", SESSION_TIMEOUT, this.watcher);
    }

    private void closeZk() throws InterruptedException {
        zooKeeper.close();
    }

    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
        ZkDemo zkDemo = new ZkDemo();
        zkDemo.createZkInstance();
        zkDemo.zkOperations();
        zkDemo.closeZk();
    }
}
