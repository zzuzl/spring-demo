package cn.zzuzl.hbase;

import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.junit.Test;

import java.io.IOException;

public class HbaseDemo {

    @Test
    public void testPut() throws IOException {
        Connection connection = ConnectionFactory.createConnection();
        Table table = connection.getTable(TableName.valueOf("users"));

        Put put = new Put(Bytes.toBytes("row1"));
        put.addColumn(Bytes.toBytes("info"), Bytes.toBytes("name"), Bytes.toBytes("aname"));
        table.put(put);
        table.close();
        connection.close();
    }

    @Test
    public void testGet() throws IOException {
        Connection connection = ConnectionFactory.createConnection();
        Table table = connection.getTable(TableName.valueOf("users"));

        Get get = new Get(Bytes.toBytes("row1"));
        Result result = table.get(get);
        System.out.println(result);
        table.close();
        connection.close();
    }

}
