package com.hzq;


import org.apache.commons.collections.CollectionUtils;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;

import static java.util.Comparator.comparing;

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

        final BigDecimal divide = new BigDecimal(3).divide(new BigDecimal(5),3,BigDecimal.ROUND_HALF_DOWN);

        System.out.println(divide);
    }
}
