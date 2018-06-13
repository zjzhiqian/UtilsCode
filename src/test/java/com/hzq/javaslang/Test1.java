package com.hzq.javaslang;

import javaslang.Function2;
import javaslang.Tuple;
import javaslang.Tuple2;
import javaslang.collection.*;
import javaslang.collection.Stream;
import org.junit.Test;

import java.util.Comparator;
import java.util.function.DoubleBinaryOperator;
import java.util.stream.*;

/**
 * Created by hzq on 17/10/11.
 */
public class Test1 {


    @Test
    /**
     * list  list.tail
     */
    public void test1() {
        List<Integer> of = List.of(3, 4, 7);
        System.out.println(of);
        System.out.println(of.prepend(2));
        System.out.println(of.append(5));
        System.out.println(of.tail()); //去除第一个元素的list
        System.out.println(of.head()); //获取第一个元素
    }

    @Test
    /**
     * queue
     */
    public void test2() {
        Queue<Integer> of = Queue.of(3, 4, 5);
        System.out.println(of);
        System.out.println(of.enqueue(6, 7));
        System.out.println(of);
        System.out.println(of.dequeue());//tuple2
        System.out.println(of);
        //empty to dequeue will throw exception
        System.out.println(Queue.empty().dequeueOption().isEmpty());
    }

    @Test
    /**
     * treeSet
     */
    public void test3() {
        SortedSet<Integer> of = TreeSet.of(2, 3, 1, 2);
        System.out.println(of);
        TreeSet<Integer> of2 = TreeSet.of((Comparator<Integer>) (a, b) -> b - a, 2, 3, 6, 9);
        System.out.println(of2);
        System.out.println(of2.add(5));
        System.out.println(of2);
    }


    @Test
    /**
     * Tuple
     */
    public void test4() {
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
        Function2<Integer, Integer, String> sum = (a, b) -> a + b + "";
        System.out.println(sum.apply(3, 4));
        System.out.println("===============");


        Map<Integer, List<Integer>> tuple2s = List.of(1, 2, 3, 4).groupBy(i -> i % 2);
        System.out.println(tuple2s);
        List<Tuple2<Character, Long>> tuple2s1 = List.of('a', 'b', 'c').zipWithIndex();
        System.out.println(tuple2s1);
    }

    @Test
    /**
     * better than java8
     */
    public void test999() {
        System.out.println(Stream.of(3, 4, 5).map(Object::toString).toList());

        List<String> words = List.of("213123", "66666");
        System.out.println(words
                .intersperse(",")
                .fold("prefix_|", String::concat));
        System.out.println(words.mkString(","));
        System.out.println(List.empty().mkString("   k"));//result is empty

    }


}
