package com.hzq;


import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.*;

/**
 * BaseTest
 * Created by hzq on 16/6/9.
 */
public class BaseTest {

    @Test
    public void test01() {
        final Function<String, Integer> strToInt = String::length;

        final String s = new StringBuilder().append("1").append("3").toString();
        final String s1 = new StringBuffer().append("3").append("3223").toString();
    }


    public <T> Collection inttt(Collection<T>... lists) {
        Collection<T> ts = Arrays.stream(lists).min(comparing(Collection::size)).orElseThrow(RuntimeException::new);
        return Arrays.stream(lists)
                .map(list -> CollectionUtils.intersection(list, ts))
                .reduce(CollectionUtils::intersection).orElseThrow(RuntimeException::new);
    }


    public static void main(String[] args) {
//        final BigDecimal divide = new BigDecimal(3).divide(new BigDecimal(5),4, BigDecimal.ROUND_HALF_DOWN);

        final BigDecimal divide = new BigDecimal(3).divide(new BigDecimal(5), 3, BigDecimal.ROUND_HALF_DOWN);

        System.out.println(divide);


        String[] split = "hzqqsiiiijfiih".split("");
        Map<String, Long> collect = Arrays.stream(split).collect(groupingBy(String::toString, counting()));
        Set<Map.Entry<String, Long>> entries = collect.entrySet();
        String maxKey = "";
        Long maxVal = null;
        for (Map.Entry<String, Long> entry : entries) {
            if (maxVal == null) {
                maxVal = entry.getValue();
                maxKey = entry.getKey();
                continue;
            }
            if (entry.getValue() > maxVal) {
                maxVal = entry.getValue();
                maxKey = entry.getKey();
            }
        }


        Optional<Map.Entry<String, Long>> reduce = Arrays.stream(split).collect(groupingBy(String::valueOf, counting())).entrySet().stream()
                .reduce((a, b) -> {
                    if (a.getValue().compareTo(b.getValue()) > 0) {
                        return a;
                    } else {
                        return b;
                    }
                });
        System.out.println(reduce.get().getKey() + "...." + reduce.get().getValue());


//        System.out.println("maxKey : " + maxKey + " maxVal : " + maxVal);


        LinkedList<String> lin = new LinkedList<>();
        lin.add("a");
        lin.add("aa");
        lin.add("b");
        lin.add("a");
        Iterator<String> iterator = lin.iterator();
        while (iterator.hasNext()) {
            String next = iterator.next();
            if (next.contains("a")) iterator.remove();
        }
        System.out.println(lin);


//        IntStream.iterate(0, t -> t++).forEach(System.out::println);
    }


    @Test
    public void test02() {
        int in = 19;
        in++;
        while (isNotPrime(in)) in++;
        System.out.println(in);
    }

    public boolean isNotPrime(int next) {
        int nextRoot = (int) Math.sqrt((double) next);
        return IntStream.range(2, nextRoot).anyMatch(i -> next % i == 0);
    }




}

