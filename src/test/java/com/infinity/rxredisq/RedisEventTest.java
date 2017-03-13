package com.infinity.rxredisq;

import org.junit.Assert;
import org.junit.Test;
import rx.Observable;

import java.util.UUID;

public class RedisEventTest {

    @Test
    public void testProducer() {
        RedisEventProducer redisEventProducer = new RedisEventProducer();
        Observable<Long> published = redisEventProducer.publish("sms-gateway::queue", "test123");
        Assert.assertTrue(published.toBlocking().single() > 0 );
    }

    @Test
    public void testConsumer() {
        RedisEventConsumer consumer = new RedisEventConsumer();
        Observable<String> consume = consumer.consume("sms-gateway::queue");
        consume.toBlocking().subscribe(s -> {
            System.out.println(s);
        });

    }
}
