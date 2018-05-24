package cn.zzuzl;

import com.google.common.base.Joiner;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.PairFunction;
import scala.Tuple2;

import java.io.IOException;
import java.util.*;

/**
 * Created by zhanglei53 on 2018/1/8.
 */
public class SparkTop5 {
    public static final String inputPath = "/Users/zhanglei53/files/1.txt";

    public static void main(String[] args) throws IOException {
        System.out.println(inputPath);

        SparkConf conf = new SparkConf().setAppName("Top5").setMaster("local[2]");
        // 连接spark master
        final JavaSparkContext context = new JavaSparkContext(conf);

        // 创建RDD
        JavaRDD<String> lines = context.textFile(inputPath, 1);

        // 从RDD创建键-值对
        JavaPairRDD<String, Tuple2<Integer, Integer>> pairs = lines.mapToPair(new PairFunction<String, String, Tuple2<Integer, Integer>>() {
            @Override
            public Tuple2<String, Tuple2<Integer, Integer>> call(String s) throws Exception {
                String[] tokens = s.split(" ");
                System.out.println(Arrays.toString(tokens));
                Integer time = Integer.parseInt(tokens[1]);
                Integer value = Integer.parseInt(tokens[2]);

                Tuple2<Integer, Integer> timeValue = new Tuple2<Integer, Integer>(time, value);
                return new Tuple2<String, Tuple2<Integer, Integer>>(tokens[0], timeValue);
            }
        });

        // 验证
        List<Tuple2<String, Tuple2<Integer, Integer>>> output = pairs.collect();
        for (Tuple2<String, Tuple2<Integer, Integer>> t : output) {
            Tuple2<Integer, Integer> tuple2 = t._2;
            System.out.println(Joiner.on(",").join(t._1, tuple2._1, tuple2._2));
        }

        // 按name对JavaPairRDD元素分组
        JavaPairRDD<String, Iterable<Tuple2<Integer, Integer>>> groups = pairs.groupByKey();

        // 通过collect验证上一步
        System.out.println("debug1");
        List<Tuple2<String, Iterable<Tuple2<Integer, Integer>>>> output2 = groups.collect();
        for (Tuple2<String, Iterable<Tuple2<Integer, Integer>>> t : output2) {
            Iterable<Tuple2<Integer, Integer>> list = t._2;
            System.out.println(t._1);
            for (Tuple2<Integer, Integer> t2 : list) {
                System.out.println(t2._1 + " = " + t2._2);
            }
            System.out.println("=====");
        }

        // 在内存中排序
        JavaPairRDD<String, Iterable<Tuple2<Integer, Integer>>> sorted = groups.mapValues(new Function<Iterable<Tuple2<Integer, Integer>>, Iterable<Tuple2<Integer, Integer>>>() {
            @Override
            public Iterable<Tuple2<Integer, Integer>> call(Iterable<Tuple2<Integer, Integer>> v) throws Exception {
                List<Tuple2<Integer, Integer>> newList = new ArrayList<Tuple2<Integer, Integer>>();
                for (Tuple2<Integer, Integer> t : v) {
                    newList.add(t);
                }

                Collections.sort(newList, new Comparator<Tuple2<Integer, Integer>>() {
                    @Override
                    public int compare(Tuple2<Integer, Integer> o1, Tuple2<Integer, Integer> o2) {
                        return o1._1.compareTo(o2._1);
                    }
                });


                return newList;
            }
        });

        // 通过collect验证上一步
        System.out.println("debug2");
        List<Tuple2<String, Iterable<Tuple2<Integer, Integer>>>> output3 = sorted.collect();
        for (Tuple2<String, Iterable<Tuple2<Integer, Integer>>> t : output3) {
            Iterable<Tuple2<Integer, Integer>> list = t._2;
            System.out.println(t._1);
            for (Tuple2<Integer, Integer> t2 : list) {
                System.out.println(t2._1 + " = " + t2._2);
            }
            System.out.println("=====");
        }
        System.in.read();
    }

}