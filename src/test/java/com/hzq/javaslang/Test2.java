package com.hzq.javaslang;

import javaslang.*;
import javaslang.control.Either;
import javaslang.control.Option;
import javaslang.control.Try;
import jdk.management.resource.ResourceId;
import org.apache.commons.io.FileUtils;
import org.junit.Test;

import java.io.File;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.function.Predicate;

import static javaslang.API.*;
import static javaslang.Predicates.instanceOf;
import static javaslang.Predicates.isIn;

/**
 * Created by hzq on 17/10/11.
 */
public class Test2 {

    @Test
    /**
     * Tuple
     */
    public void test1() {
        //Create a tuple
        Tuple2<String, Integer> java8 = Tuple.of("java8", 8);
        System.out.println(java8);
        //Map a tuple component-wise  or   Map a tuple using one mapper
        Tuple2<String, Integer> map = java8.map(s -> s.substring(0, 2) + "vr", i -> i + 3);
        Tuple2<String, Integer> map2 = java8.map((s, i) -> Tuple.of(s.substring(0, 2) + "vr", i + 3));
        System.out.println(map);
        System.out.println(map2);
        System.out.println("===============");
        //Transform a tuple
        String transform = java8.transform((s, i) -> s.substring(2) + "vr " + i / 8);
        System.out.println(transform);
        System.out.println("===============");
    }


    @Test
    /**
     * function
     */
    public void test2() {
        Function2<Integer, Integer, String> sum = (a, b) -> a + b + "";
        System.out.println(sum.apply(3, 4));
        System.out.println("===============");
        Function3<String, String, String, String> func3 = Function3.of(this::threeParameter);
        System.out.println(func3.apply("2", "3", "6"));
        System.out.println("===============");
        System.out.println(func3.apply("2").apply("3").apply("6"));
        System.out.println("Composition===============");
        //Composition
        Function1<Integer, Integer> plusOne = a -> a + 1;
        Function1<Integer, Integer> multiplyByTwo = a -> a * 2;
        Function1<Integer, Integer> add1AndMultiplyBy2 = plusOne.andThen(multiplyByTwo);
        System.out.println((add1AndMultiplyBy2.apply(2)));
        System.out.println("Lifting===============");
        //Lifting
        Function2<Integer, Integer, Integer> divide = (a, b) -> a / b;
        Function2<Integer, Integer, Option<Integer>> safeDivide = Function2.lift(divide);
        Option<Integer> i1 = safeDivide.apply(1, 0); //i1.isEmpty() = true
        Option<Integer> i2 = safeDivide.apply(4, 2); // i2.get()
        System.out.println("i1 is empty : " + i1.isEmpty());
        System.out.println("i2 value : " + i2.get());
        Function2<Integer, Integer, Option<Integer>> summary = Function2.lift(this::sum);
        Option<Integer> result = summary.apply(1, -2);
        System.out.println("result is empty: " + result.isEmpty());
        System.out.println("Partial application===============");
        //Partial application
        Function2<String, String, String> func2 = func3.apply("2");//get func2;
        Function1<String, String> func1 = func2.apply("3"); //get func1
        System.out.println(func1.apply("6"));
        System.out.println("Currying===============");
        //Currying
        Function3<Integer, Integer, Integer, Integer> sum_curry = (a, b, c) -> a + b + c;
        Function1<Integer, Function1<Integer, Integer>> add2_curry = sum_curry.curried().apply(2);
        System.out.println(add2_curry.apply(3).apply(4));
        System.out.println("Memoization===============");
        //Memoization(Memoization is a form of caching. A memoized function executes only once and then returns the result from a cache.)
        Function0<Double> cache =
                Function0.of(Math::random).memoized(); //executes only once
        System.out.println(cache.apply());
        System.out.println(cache.apply());
    }


    private String threeParameter(String p1, String p2, String p3) {
        return p1 + p2 + p3;
    }

    int sum(int first, int second) {
        if (first < 0 || second < 0) {
            throw new IllegalArgumentException("Only positive integers are allowed");
        }
        return first + second;
    }

    @Test
    /**
     * values
     */
    public void test3() {
        System.out.println("Option===============");
        //Option (different from java8)
        Optional.of("foo")
                .map(s -> (String) null)
                .map(s -> s.toUpperCase() + "bar"); //java8
        Option<String> op = Option.of("foo");
        try {
            op.map(s -> (String) null) //some(null)
                    .map(s -> s.toUpperCase() + "bar"); //javaslang (nullPoint exception)
        } catch (NullPointerException e) {
            System.out.println("null point exception");
        }

        Option.of("foo").map(s -> (String) null) // -->some(null)
                .flatMap(s -> Option.of(s) //-->none()
                        .map(t -> t.toUpperCase() + "bar")); //-->none()

        System.out.println("Try===============");
        //Try
        Try.of(() -> {
            throw new RuntimeException("exception");
        }).getOrElse(3);

        //Try and deal Exception
        Integer result = Try.of(() -> {
            if ("1".equals("1")) {
                throw new RuntimeException("runtime exception");
            }
            return 1;
        }).recover(x -> Match(x).of(
                Case($(instanceOf(RuntimeException.class)), t -> {
                    System.out.println("runtime exception");
                    t.printStackTrace();
                    return 2;
                }),
                Case($(instanceOf(Throwable.class)), t -> {
                    t.printStackTrace();
                    return 3;
                })
        )).get();
        System.out.println(result);
        System.out.println("Lazy===============");
        //Lazy
        Lazy<Double> lazy = Lazy.of(Math::random);
        System.out.println(lazy.isEvaluated());
        System.out.println(lazy.get()); //generate
        System.out.println(lazy.isEvaluated());
        System.out.println(lazy.get()); //memorized
        Lazy.val(() -> "Yay!", CharSequence.class); //another style
        System.out.println("Either===============");
        //Either
        Either<String, Integer> either = compute("1").right().map(r -> r * 2).toEither();
        System.out.println(either);
        compute("1").right().map(r -> {
            System.out.println("map right  " + r);
            return r * 2;
        });
        Either<Integer, Integer> integers = compute("2").left().map(l -> {
            System.out.println("map left   " + l);
            return 3;
        }).toEither();
        System.out.println("Future===============");
        //Future
//        .....
        String s = Match(9).of(
                Case($(1), "one"),
                Case($(2), "two"),
                Case($(), "?")  //没有这个时 not match会抛异常
        );

        Void of = Match("-h").of(
                Case($(isIn("-h", "--help")), o -> run(this::displayHelp)),
                Case($(isIn("-v", "--version")), o -> run(this::displayVersion)),
                Case($(), o -> run(() -> {
                    throw new IllegalArgumentException("h");
                }))
        );
        System.out.println(of);

    }

    private Either<String, Integer> compute(String id) {
        if ("1".equals(id)) {
            return Either.right(3);
        }
        return Either.left("error");
    }

    void displayHelp(){
        System.out.println("help======");
    }
    void displayVersion(){
        System.out.println("version======");
    }
}
