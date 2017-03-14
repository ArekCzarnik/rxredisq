package com.infinity.rxredisq;

import org.junit.Assert;
import org.junit.Test;
import rx.Observable;

import java.util.UUID;

public class RedisEventTest {

    @Test
    public void testProducer() {
        RedisEventProducer redisEventProducer = new RedisEventProducer();
        for (int i = 0; i < 100; i++) {
            Observable<Long> published = redisEventProducer.publish("sms-gateway::queue", "test"+i);
            Assert.assertTrue(published.toBlocking().single() > 0 );
        }
    }

    @Test
    public void testConsumer() {
        RedisEventConsumer consumer = new RedisEventConsumer("sms-gateway::queue");
        Observable<String> consume = consumer.consume();
        consume.take(100).toBlocking().subscribe(s -> System.out.println(s));
    }
}
