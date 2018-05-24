import cn.zzuzl.domain.vo.ThreeTuple;
import cn.zzuzl.domain.vo.TwoTuple;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Stopwatch;
import com.google.common.collect.Lists;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2017/9/9.
 */
public class SimpleTest {

    public static void main(String[] args) {
        TwoTuple<Integer, String> twoTuple = new TwoTuple<>(1, "1");
        System.out.println(twoTuple);

        ThreeTuple<Integer, String, Float> threeTuple = new ThreeTuple<Integer, String, Float>(twoTuple, 0.01f);
        System.out.println(threeTuple);
    }

    @Test
    public void genSql() {
        for (int i = 0; i < 200; i++) {
            String sql = String.format("delete from duedetail_%d where orderId not in (22371155,22372715,22371907,22371902,22371190,22371894,22371184,22372706,22371900,22371893,22371892,22371891,22371896,22371182,22372681,22371878,22371886,22371169,22371872,22371873,22372680,22371877,22371153,22371870,22371868,22371697,22371007,22371070,22371764,22371783,22371083,22371082,22371781,22371782,22371780,22371063,22371068,22371069,22371774,22371066,22372604,22371769,22371064,22371768,22372600,22371067,22371049,22371050,22371046,22372585,22371051,22371059,22372599,22372516,22371057,22371058,22372572,22371056,22371742,22371036,22371032,22372545,22372502,22372507,22371005,22371001,22372476,22370832,22370927,22372414,22372381,22371554,22370867,22371504,22370280,22371501,22370823,22370260,22370257,22371483,22370799,22370798,22371465,22370788,22370230,22370223,22371437,22370762,22371435,22370758,22370207,22370754,22371352,22371387,22371373,22370160,22370159,22370700,22371353,22370684,22370145);", i);
            System.out.println(sql);
        }
    }

    @Test
    public void testFor() {
        List<Integer> list = Lists.newArrayList(1, 2, 2, 3, 5);
        /*for(Integer value :list) {
            if(value == 2) {
                list.remove(value);
            }
        }*/
        /*for (int i = 0; i < list.size(); i++) {
            Integer value = list.get(i);
            if(value == 2) {
                list.remove(i);
            }
        }*/
        System.out.println(list);
    }

    @Test
    public void testJson() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Stopwatch stopwatch = Stopwatch.createStarted();
        Item item = new Item("122", "22311", new BigDecimal(1313));
        System.out.println(mapper.writeValueAsString(item));
        System.out.println(stopwatch.stop().elapsed(TimeUnit.MILLISECONDS));
    }
}
