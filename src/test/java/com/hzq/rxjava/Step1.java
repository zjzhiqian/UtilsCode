package com.hzq.rxjava;

import net.sf.cglib.core.Local;
import rx.Observable;
import rx.functions.Action1;

import java.time.LocalDateTime;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Created by hzq on 18/6/7.
 */
public class Step1 {


    /**
     * 同步执行,完成后调用onComplete() 异常时调用onError();
     */
    public void test1() {
//        Observable<String> o = Observable.from("a", "b", "c");
//        List<Integer> list = Lists.newArrayList(1, 3, 2, 9);
//        Observable<Integer> o2 = Observable.from(list);
        Observable<String> observable = Observable.from("Ben", "George");
        observable.subscribe(s -> {
            System.err.println("start-" + s + "-" + LocalDateTime.now());
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.err.println("end-" + s + "-" + LocalDateTime.now());
            if ("1".equals("1")) {
                throw new RuntimeException("exception");
            }
        }, s -> {
            s.printStackTrace();
            System.err.println("on Error " + "-" + LocalDateTime.now());
        }, () -> {
            System.err.println("on Complete " + "-" + LocalDateTime.now());
        });
    }

    /**
     * Synchronous Observable Example
     */
    public static void test2() throws InterruptedException {
        Observable<Integer> observable = Observable.create(t -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            for (int i = 0; i < 5; i++) {
                if (!t.isUnsubscribed()) {
                    t.onNext(i);
                }
            }
            if (!t.isUnsubscribed()) {
                t.onCompleted();
            }
        });
        observable.subscribe(s -> {
            System.err.println(s.intValue());
        }, Throwable::printStackTrace, () -> {
            System.err.println("complete ");
        });
        System.err.println("end");
    }

    /**
     * Asynchronous Observable Example
     */
    public static void test3() throws InterruptedException {
        nonBlockingObservable().subscribe(s -> {
            System.err.println(s.intValue());
        }, Throwable::printStackTrace, () -> {
            System.err.println("complete ");
        });
        System.err.println("end");
    }

    private static Observable<Integer> nonBlockingObservable() {
        return Observable.create(t -> {
            new Thread(() -> {
                try {
                    TimeUnit.MILLISECONDS.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                for (int i = 0; i < 30; i++) {
                    if (t.isUnsubscribed()) {
                        return;
                    }
                    t.onNext(i);
                }
                if (!t.isUnsubscribed()) {
                    t.onCompleted();
                }
            }).start();
        });
    }

    /**
     * Asynchronous Observable Example
     */
    public static void test4() throws InterruptedException {
        nonBlockingObservable()
                .skip(10)
                .take(5)
                .map(s -> "result:" + s)
                .subscribe(System.err::println, Throwable::printStackTrace, () -> {
                    System.err.println("complete ");
                });
        System.err.println("end");
    }


    private static Observable<String> namedObservable(String name) {
        return Observable.create(t -> {
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            t.onNext(name);
        });
    }

    /**
     * zip
     */
    public static void test5() throws InterruptedException {
        System.err.println(LocalDateTime.now());
        Observable
                .zip(namedObservable("username"), namedObservable("book"), namedObservable("video"), (o1, o2, o3) -> o1 + ":" + o2 + ":" + o3)
                .map(t -> "result    " + t)
                .subscribe(System.err::println, Throwable::printStackTrace, () -> {
                    System.err.println("complete ");
                });
        System.err.println(LocalDateTime.now());
    }


    /**
     * reduce
     */
    public static void test6() throws InterruptedException {
        System.err.println(LocalDateTime.now());
        Observable
                .merge(namedObservable("username"), namedObservable("book"))
                .reduce("", (o1, o2) -> o1 + ":" + o2)
                .map(t -> "result   " + t)
                .subscribe(System.err::println, Throwable::printStackTrace, () -> { //????咋不打印result呢
                    System.err.println("complete ");
                });
        System.err.println(LocalDateTime.now());
    }

//    /**
//     * error
//     */
//    public static void test7() throws InterruptedException {
//        System.err.println(LocalDateTime.now());
//        Observable.create(t -> {
//            if ("1".equals("1")) {
//                throw new RuntimeException("exp");
//            }
//            t.onNext("231");
//        }).onErrorResumeNext(t -> {
//            System.err.println("exception:" + t.getClass().getName());
//            return Observable.error(t);
//        }).subscribe(System.err::println, Throwable::printStackTrace, () -> {
//            System.err.println("complete ");
//        });
//
//        System.err.println(LocalDateTime.now());
//    }


    public static void test8() {
        Observable.empty();
        Observable.never();
        Observable.error(new RuntimeException("xx"));
    }

    public static void test9() {
        Future<String> f = new Future<String>() {
            @Override
            public boolean cancel(boolean mayInterruptIfRunning) {
                return false;
            }

            @Override
            public boolean isCancelled() {
                return false;
            }

            @Override
            public boolean isDone() {
                return false;
            }

            @Override
            public String get() throws InterruptedException, ExecutionException {
                return "9312312";
            }

            @Override
            public String get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
                return null;
            }
        };
        Observable.from(f)
                .subscribe(System.err::println);
    }

    public static void test10() throws InterruptedException {
        Observable.interval(1, TimeUnit.SECONDS)
                .subscribe(System.err::println);
        TimeUnit.SECONDS.sleep(999999);
    }


    public static void test11() throws InterruptedException {
    }

    public static void main(String[] args) throws InterruptedException {
        test2();
//        test3();
//        test4();
//        test5();
//        test6();
//        test7();
//        test8();
//        test9();
//        test10();
//        test11();
    }

}
