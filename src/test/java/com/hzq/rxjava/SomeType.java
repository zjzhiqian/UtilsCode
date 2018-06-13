package com.hzq.rxjava;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func0;

import java.io.IOException;

/**
 * Created by hzq on 18/6/8.
 */
public class SomeType {
    private String value;

    public void setValue(String value) {
        this.value = value;
    }

    public Observable<String> justObservable() {
        return Observable.just(value);
    }

    public Observable<String> createObservable() {
        return Observable.create(subscriber -> {
            subscriber.onNext(value);
            subscriber.onCompleted();
        });

    }

    public Observable<String> deferObservable() {
        return Observable.defer(() -> Observable.just(value));
    }


    public static void main(String[] args) {
        //just 和create的不同  create在订阅的时候才会去获取值,just直接在创建的时候获取值
        //create的需要手动调用onCompleted()
        SomeType instance = new SomeType();
        Observable<String> value = instance.justObservable();
        instance.setValue("Some Value");
        value.subscribe(System.out::println);

        instance = new SomeType();
        value = instance.createObservable();
        instance.setValue("Some Value");
        value.subscribe(System.out::println);

        //使用defer也会在subscribe的时候去获取值
        instance = new SomeType();
        value = instance.deferObservable();
        instance.setValue("Some Value");
        value.subscribe(System.out::println);
    }
}
