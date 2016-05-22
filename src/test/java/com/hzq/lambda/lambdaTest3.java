package com.hzq.lambda;

import org.junit.Before;
import org.junit.Test;
import org.omg.PortableInterceptor.INACTIVE;

import java.math.BigInteger;
import java.util.*;
import java.util.stream.BaseStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Created by hzq on 16/5/22.
 */
public class lambdaTest3 {


    private static List<String> strList;

    @Before
    public void init() {
        strList = Arrays.asList("duck", "monkey", "dog", "pig");
    }


    @Test
    /**
     * stream  TODO stream和parallelStream的区别
     */
    public void test01() {
        Long count = strList.stream().filter(arg -> arg.length() > 3).count();
        Long count2 = strList.parallelStream().filter(arg -> arg.length() > 2).count();
        System.out.println(count);
        System.out.println(count2);


        //在执行stream,filter时,并没有执行,stream操作符是延迟执行的
        Stream<String> stringStream = strList.stream().filter(arg -> {
            System.out.println(3);
            return true;
        });
        System.out.println("====");
        stringStream.count();

    }

    @Test
    /**
     * 2.2创建Stream
     */
    public void test02() {
        Stream.of(new String[]{"1"});
        final IntStream intStream = Arrays.stream(new int[]{3, 2});
        infoStream(intStream);
        Stream.empty();
//        Stream integerStream = Stream.generate(() -> 2);
//        Stream randomStream = Stream.generate(Math::random);
//        Stream<BigInteger> bigIntegerStream = Stream.iterate(BigInteger.ZERO, n -> n.add(BigInteger.ONE));

    }


    @Test
    /**
     * 2.3 filter,map,flatMap
     */
    public void test03() {
        final List<String> rsList = Arrays.asList("123", "456", "abc");
//        final Stream<Stream<String>> mapStream = rsList.stream().map(lambdaTest3::characterStream);
//        对每个结果调用flatMap
//        final Stream<String> flatMapStream = rsList.stream().flatMap(lambdaTest3::characterStream);
    }

    private static Stream<String> characterStream(String s) {
        List<String> result = new ArrayList<>();
        Arrays.stream(s.split("")).forEach(result::add);
        return result.stream();
    }


    @Test
    /**
     * 2.4 limit,skip,contact 执行流操作
     */
    public void test04() {
        //裁剪前100个流
        final Stream<Double> limit = Stream.generate(Math::random).limit(3);
//        infoStream(limit);
        //裁剪流
        Stream.of(1, 2, 3).skip(1);
        //连接流
        Stream.concat(Stream.of(2), Stream.of(2));
    }

    @Test
    /**
     * 2.5有状态的转换
     */
    public void test05() {

        final Stream<String> stringStream = Stream.of("1", "2", "3", "3");
        stringStream
                .peek(System.out::println)
                .distinct()
                .peek(System.out::println)
                .count();
        System.out.println("========");

        final Stream<String> stringStream2 = Stream.of("看好3", "我去额2", "4任务认为", "3二为");
        //stream.sorted方法 返回一个新的已排序的流
        stringStream2
                .sorted(Comparator.comparing(String::length).thenComparing(arg -> arg.indexOf("二")).reversed())
                .peek(System.out::println)
                .count();
//        stringStream2.sorted(String::compareToIgnoreCase)
//                .peek(System.out::println)
//                .count();
    }

    @Test
    /**
     * 2.6聚合方法:将流聚合成为一个值,以便在程序中使用
     * 1.count 返回数量
     * 2.max 返回一个Optional的对象
     * 3.findFirst 返回第一个
     * 4.findAny  用.parallel并行 返回任意一个
     * 5.anyMatch,noneMatch,allMatch接收一个Predict的function 返回一个boolean
     */
    public void test06() {
        //.count

        //.max
        final Stream<String> stringStream = Stream.of("看好3", "我去额2", "4任务认为", "3二为");
        final Optional<String> max = stringStream
                .max(Comparator.comparing(String::length));
        if (max.isPresent()) {
            System.out.println(max.get());
        }

        //.findFirst
        final Stream<String> stringStream2 = Stream.of("看好3", "我去额2", "4任务认为", "3二为");
        final Optional<String> first = stringStream2.findFirst();
        if (first.isPresent()) {
            System.out.println(first.get());
        }

        //.parallel.findAny
        final Stream<String> stringStream3 = Stream.of("看好3", "我去额232", "4任务认为", "3二为");
        final Optional<String> any = stringStream3.parallel().findAny();
        if (any.isPresent()) {
            System.out.println(any.get());
        }
        //anyMatch,noneMatch,allMatch 返回boolean
    }


    @Test
    /**
     * 2.8使用Optional
     */
    public void test07() {
        Optional<String> str = Optional.of("123213");
        //ifPresent 接收一个Consumer 无返回值
        str.ifPresent(System.out::println);
        //.map接收一个Function<T,R>,有返回值,可能是 null的Optional
        final Optional<Integer> integer = str.map(arg -> null);
        if (!integer.isPresent()) {
            System.out.println("return null value");
        }

        System.out.println("========");

        //Optional的用处
        final Optional<String> empty = Optional.empty();
        final String s = empty.orElseGet(() -> {
            System.out.println(".....run this method");
            return "123";
        });
        System.out.println(s);
        final String s1 = empty.orElse("213");
        System.out.println(s1);

        //在空值的时候抛出异常
        final String s2 = empty.orElseThrow(() -> new RuntimeException(""));


    }


    /**
     * 使用peek打印stream信息
     *
     * @param stream
     */
    private static void infoStream(IntStream stream) {
//        stream.peek(System.out::print);
        stream.peek(System.out::println).count();
    }



}
