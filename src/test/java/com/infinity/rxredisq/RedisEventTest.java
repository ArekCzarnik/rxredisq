package com.infinity.rxredisq;

import org.junit.Assert;
import org.junit.Test;
import rx.Observable;

public class RedisEventTest {

    @Test
    public void testProducer() {
        RedisEventProducer redisEventProducer = new RedisEventProducer();
        Observable.range(1, 100)
                .flatMap(integer -> redisEventProducer.publish("sms-gateway::queue", "test" + integer))
                .toBlocking()
                .subscribe(count -> System.out.println(count));

        redisEventProducer.close();
    }

    @Test
    public void testConsumer() {
        RedisEventConsumer consumer = new RedisEventConsumer("sms-gateway::queue");
        Observable<String> consume = consumer.consume();
        consumer.consume().take(100).toBlocking().subscribe(s -> System.out.println(s),
                throwable -> {
            System.out.println(throwable);
        });
        consumer.close();
    }
}
