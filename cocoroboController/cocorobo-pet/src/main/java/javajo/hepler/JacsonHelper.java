package javajo.hepler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import javajo.model.redis.RedisValue;

import java.io.IOException;
import java.util.Map;

/**
 * Created by maaya on 2016/09/25.
 */
public class JacsonHelper {

    /**
     * Object -> Json
     *
     * @param obj
     * @return
     * @throws JsonProcessingException
     */
    public String objectForJson(Object obj) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(obj);
    }

    /**
     * RedisのValueデータ変更処理
     * JSON -> Object
     *
     * @param json
     * @return
     * @throws IOException
     */
    public RedisValue jsonForRedisValue(String json) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(json, RedisValue.class);
    }

    /**
     * String -> Json(RequestRedisValue)
     *
     * @param strJson
     * @return
     * @throws IOException
     */
    public RedisValue stringToJson(String strJson) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(strJson, RedisValue.class);
    }

    public Map stringForMap(String json) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(json, Map.class);
    }
}
