package com.hzq.lambda;

import com.google.common.collect.Lists;
import com.hzq.entity.User;
import org.junit.Test;

import java.util.function.Function;
import java.util.function.IntPredicate;
import java.util.function.IntSupplier;
import java.util.function.Predicate;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.Comparator.comparing;


/**
 * hzq
 * Created by hzq on 16/5/20.
 */
public class LambdaTest4 {

    @Test
    public void test01() {
        //返回值是boolean 可以用Predicate 或  Function<User, Boolean> 接收
        final Function<User, Boolean> isHigh = User::isHigh;
        Predicate<User> us = User::isHigh;
        System.out.println(isHigh.apply(new User()));
        System.out.println(us.test(new User()));

        final IntPredicate intPredicate = (int i) -> i % 2 == 0;
        System.out.println(intPredicate.test(100000));

        int i = 3;
        final IntSupplier doubleSupplier = () -> i;
//        i = 4;


        Lists.newArrayList(1, 3, 4, 5).sort(comparing(Integer::toHexString).reversed().thenComparing(Integer::byteValue));


        final Stream<int[]> limit = Stream.iterate(new int[]{0, 1}, t -> new int[]{t[1], t[0] + t[1]}).limit(3);


        final Predicate<User> isHigh1 = User::isHigh;
        isHigh1.and(User::isHigh).or(User::isHigh);


    }

}
