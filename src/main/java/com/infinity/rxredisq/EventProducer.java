package com.infinity.rxredisq;


import rx.Observable;

public interface EventProducer {
    public Observable<Long> publish(final String key, final String message);
}
