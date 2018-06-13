package com.hzq.rxjava;

import com.hzq.entity.User;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by hzq on 18/6/7.
 */
public class CreatingObservables {


    public static void main(String[] args) {


        User u = new User(3);
        u.setAge(10);
        Observable<User> just = Observable.just(u);
        u.setAge(11);
        just.subscribe(System.out::println);


//        Observable.create((Observable.OnSubscribe<Integer>) observer -> {
//            try {
////                if (!observer.isUnsubscribed()) {
//                for (int i = 1; i < 5; i++) {
//                    observer.onNext(i);
//                }
//                if ("1".equals("1")) {
//                    throw new RuntimeException("1");
//                }
//                observer.onCompleted();
////                }
//            } catch (Exception e) {
//                observer.onError(e);
////                throw e;
//            }
//        });
//                .subscribe(new Subscriber<Integer>() {
//                    @Override
//                    public void onNext(Integer item) {
//                        System.out.println("Next: " + item);
//                    }
//
//                    @Override
//                    public void onError(Throwable error) {
//                        System.err.println("Error: " + error.getMessage());
//                    }
//
//                    @Override
//                    public void onCompleted() {
//                        System.out.println("Sequence complete.");
//                    }
//                });
    }
}
