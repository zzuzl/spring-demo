package cn.zzuzl.common.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Administrator on 2017/9/10.
 */
public class JsonUtil {
    private static Logger logger = LoggerFactory.getLogger(JsonUtil.class);
    private static ObjectMapper mapper = new ObjectMapper();

    public static String toJson(Object o) {
        String result = null;
        try {
            result = mapper.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            logger.error("toJson,exception,", e);
            result = "";
        }
        return result;
    }
}
