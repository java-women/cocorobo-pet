package javajo.hepler;

import org.springframework.beans.factory.annotation.Value;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisShardInfo;

/**
 * Redis接続サービス
 * Created by maaya on 2016/09/25.
 */
public class RedisHelper {
    private static Jedis jedis;

    @Value("${javajo.redis.hostName}")
    private String HOST_NAME;
    @Value("${javajo.redis.port}")
    private int PORT;
    @Value("${javajo.redis.auth}")
    private String AUTH;


    /**
     * コネクションを取得します
     * @return 接続情報
     */
    public Jedis connect() {
        if (jedis != null) {
            return jedis;
        }

        JedisShardInfo settings = new JedisShardInfo(HOST_NAME, PORT);
        settings.setPassword(AUTH);
        jedis = new Jedis(settings);

        return jedis;
    }

    /**
     * Redisとの接続を解放します
     */
    public void disconnect() {
        if (jedis == null) {
            return;
        }

        jedis.quit();
    }

    /**
     * set
     * @param key
     * @param value
     */
    public void setKeyValue(String key, String value) {
        connect();

        jedis.set(key, value);
    }

    /**
     * get
     * @param key
     * @return value(写真情報Json)
     */
    public String getKeyValue(String key) {
        connect();

        return jedis.get(key);
    }

    /**
     * delete
     * @param key
     */
    public void deleteKey(String key) {
        connect();
        jedis.del(key);
    }
}
