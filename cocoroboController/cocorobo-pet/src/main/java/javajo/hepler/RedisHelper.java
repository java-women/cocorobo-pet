package javajo.hepler;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisShardInfo;

/**
 * Redis接続サービス
 * Created by maaya on 2016/09/25.
 */
public class RedisHelper {
    private static Jedis jedis;

    private static final String HOST_NAME = "javajo-redis.redis.cache.windows.net";
    private static final int PORT = 6379;
    private static final String AUTH = "aEQzn3vwwAhPxi/9OM4pZnpGm+f1fDvhe0AtXhCD/jM=";


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
}
