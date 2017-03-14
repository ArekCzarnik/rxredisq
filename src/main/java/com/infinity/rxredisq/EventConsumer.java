package com.infinity.rxredisq;


import rx.Observable;

public interface EventConsumer<T> {

    public Observable<T> consume();
    public void close();
}
