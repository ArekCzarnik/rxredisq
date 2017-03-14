package com.infinity.rxredisq;

import com.lambdaworks.redis.RedisClient;
import com.lambdaworks.redis.api.StatefulRedisConnection;
import com.lambdaworks.redis.api.rx.RedisReactiveCommands;
import rx.Observable;
import rx.subjects.PublishSubject;

import java.util.concurrent.TimeUnit;

public class RedisEventConsumer implements EventConsumer<String> {


    private final RedisReactiveCommands<String, String> reactive;
    private final PublishSubject<String> subject;

    public RedisEventConsumer(final String key) {
        RedisClient client = RedisClient.create("redis://localhost");
        StatefulRedisConnection<String, String> connection = client.connect();
        reactive = connection.reactive();
        subject = PublishSubject.create();
        Observable.interval(1, TimeUnit.SECONDS).flatMap(aLong -> reactive.lpop(key))
                .subscribe(s -> subject.onNext(s),throwable -> subject.onError(throwable));
    }



    @Override
    public Observable<String> consume() {
        return subject;
    }

}
