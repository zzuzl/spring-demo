package cn.zzuzl.netty;

import cn.zzuzl.common.util.JsonUtil;
import com.google.gson.Gson;

public class JSONSerializer implements Serializer {
    @Override
    public byte getSerializerAlgorithm() {
        return SerializerAlgorithm.JSON;
    }

    @Override
    public byte[] serialize(Object object) {
        return new Gson().toJson(object).getBytes();
    }

    @Override
    public <T> T deserialize(Class<T> clazz, byte[] bytes) {
        return new Gson().fromJson(new String(bytes), clazz);
    }
}
