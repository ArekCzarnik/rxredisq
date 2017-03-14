package com.infinity.rxredisq;


import com.lambdaworks.redis.RedisClient;
import com.lambdaworks.redis.api.StatefulRedisConnection;
import com.lambdaworks.redis.api.rx.RedisReactiveCommands;
import rx.Observable;

public class RedisEventProducer implements EventProducer {

    private final RedisReactiveCommands<String, String> reactive;

    public RedisEventProducer() {
        RedisClient client = RedisClient.create("redis://localhost");
        StatefulRedisConnection<String, String> connection = client.connect();
        reactive = connection.reactive();
    }

    @Override
    public Observable<Long> publish(String key, String message) {
        return reactive.rpush(key,message);
    }

    public void close() {
        reactive.close();
    }

}
