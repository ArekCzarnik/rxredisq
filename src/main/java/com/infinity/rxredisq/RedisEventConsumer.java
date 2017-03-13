package com.infinity.rxredisq;

import com.lambdaworks.redis.RedisClient;
import com.lambdaworks.redis.api.StatefulRedisConnection;
import com.lambdaworks.redis.api.rx.RedisReactiveCommands;
import rx.Observable;

public class RedisEventConsumer implements EventConsumer<String> {


    private final RedisReactiveCommands<String, String> reactive;

    public RedisEventConsumer() {
        RedisClient client = RedisClient.create("redis://localhost");
        StatefulRedisConnection<String, String> connection = client.connect();
        reactive = connection.reactive();
    }

    @Override
    public Observable<String> consume(final String key) {
        return reactive.lpop(key);
    }
}
