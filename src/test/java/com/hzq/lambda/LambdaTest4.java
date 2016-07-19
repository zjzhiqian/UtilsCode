package com.hzq.lambda;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.hzq.entity.User;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.reducing;
import static java.util.stream.Collectors.toList;


/**
 * hzq
 * Created by hzq on 16/5/20.
 */
public class LambdaTest4 {


    @Test
    public void test01() {
        Function<String, String> set01 = User::set01;

        List<Integer> a = Lists.newArrayList(1, 2, 3);

//        final Stream<Integer> boxed = a.stream().map(Object::toString).mapToInt(Integer::parseInt).boxed();
//        final IntStream intStream = a.stream().map(Object::toString).mapToInt(Integer::parseInt);

        final IntStream range = IntStream.range(1, 2);

    }


    @Test
    public void test02() {
        //斐波拉契数列
        final Stream<int[]> limit = Stream
                .iterate(new int[]{0, 1}, t -> new int[]{t[1], t[0] + t[1]})
                .limit(10);
        limit.forEach(t -> System.out.println(t[0] + " " + t[1]));


        List<String> b = new ArrayList<>();
        System.out.println(b.stream().collect(reducing((a, c) -> a + c)));

    }


    @Test
    public void test03() {
        final Stream<int[]> limit = Stream.iterate(new int[]{0, 1}, t -> new int[]{t[1], t[0] + t[1]}).limit(10);
        final Optional<int[]> first = limit.findFirst();
        System.out.println(first.get()[0]+"  "+first.get()[1]);

//        final Supplier<Double> random = Math::random;
    }


}
