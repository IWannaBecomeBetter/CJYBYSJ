package com.bs.cache;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.io.*;

/**
 * 普通数据缓存
 */
@Repository
public class CommonCache {

    @Resource
    private RedisTemplate<String, Object> commonRedisTemplate;

    public Object get(Object key) {
        final String keyf = (String) key;
        Object object;
        object = commonRedisTemplate.execute(new RedisCallback<Object>() {
            public Object doInRedis(RedisConnection connection)
                    throws DataAccessException {

                byte[] key = keyf.getBytes();
                byte[] value = connection.get(key);
                if (value == null) {
                    return null;
                }
                return toObject(value);

            }
        });

        return object;
    }

    public void put(Object key, Object value) {
        final long liveTime = 7 * 24 * 60 * 60;

        this.put(key, value, liveTime);
    }

    public void put(Object key, Object value, final long liveTime) {
        final String keyf = (String) key;
        final Object valuef = value;

        commonRedisTemplate.execute(new RedisCallback<Long>() {
            public Long doInRedis(RedisConnection connection)
                    throws DataAccessException {
                byte[] keyb = keyf.getBytes();
                byte[] valueb = toByteArray(valuef);
                connection.set(keyb, valueb);
                if (liveTime > 0) {
                    connection.expire(keyb, liveTime);
                }
                return 1L;
            }
        });
    }

    /**
     * 描述 : <Object转byte[]>. <br>
     * <p>
     * <使用方法说明>
     * </p>
     *
     * @param obj
     * @return
     */
    private byte[] toByteArray(Object obj) {
        byte[] bytes = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(obj);
            oos.flush();
            bytes = bos.toByteArray();
            oos.close();
            bos.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return bytes;
    }

    /**
     * 描述 : <byte[]转Object>. <br>
     * <p>
     * <使用方法说明>
     * </p>
     *
     * @param bytes
     * @return
     */
    private Object toObject(byte[] bytes) {
        Object obj = null;
        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
            ObjectInputStream ois = new ObjectInputStream(bis);
            obj = ois.readObject();
            ois.close();
            bis.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return obj;
    }

    public void evict(Object key) {
        final String keyf = (String) key;
        commonRedisTemplate.execute(new RedisCallback<Long>() {
            public Long doInRedis(RedisConnection connection)
                    throws DataAccessException {
                return connection.del(keyf.getBytes());
            }
        });
    }

    public void clear() {
        commonRedisTemplate.execute(new RedisCallback<String>() {
            public String doInRedis(RedisConnection connection)
                    throws DataAccessException {
                connection.flushDb();
                return "ok";
            }
        });
    }
}
