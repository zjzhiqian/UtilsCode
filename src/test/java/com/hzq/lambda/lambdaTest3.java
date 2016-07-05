package com.hzq.lambda;

import com.google.common.collect.Lists;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;

/**
 * LambdaTest3
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
     * stream
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
        Stream.of("dewd", "ddd").map(String::toUpperCase).peek(System.out::println).collect(toList());
    }

    @Test
    /**
     * 2.3 filter,map,flatMap
     */
    public void test03() {
        final List<String> rsList = Arrays.asList("123", "456", "abc");
//        final Stream<Stream<String>> mapStream = rsList.stream().map(LambdaTest3::characterStream);
//        对每个结果调用flatMap
//        final Stream<String> flatMapStream = rsList.stream().flatMap(LambdaTest3::characterStream);
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
        Stream.of(1, 2, 3, 4, 5).skip(1).forEach(System.out::print);
        System.out.println("");
        System.out.println("===");
        //连接流
        Stream.concat(Stream.of(2), Stream.of(2)).forEach(System.out::print);
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
     * 2.7使用Optional
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

        //Optional的用处
        final Optional<String> empty = Optional.empty();
        final String s = empty.orElseGet(() -> {
            System.out.println(".....run this method");
            return "123";
        });
        Assert.assertTrue(s.equals("123"));
        final String s1 = empty.orElse("213");
        Assert.assertTrue(s1.equals("213"));

        //在空值的时候抛出异常
        final String s2 = empty.orElseThrow(() -> new RuntimeException("为空,抛出异常"));

        //一个Optional对象调用方法,flatMap方法(参数类型T,返回类型Optional)
        Optional.empty().flatMap((arg) -> Optional.of(new Object()));
    }

    @Test
    /**
     * 2.8聚合操作 reduce
     */
    public void test08() {
        final Integer rs = Stream.of(1, 2, 3, 4, 5, 6).reduce((x, y) -> x + y).get();
        Assert.assertTrue(rs == 21);
        final Integer reduce = Stream.of(1, 2, 3, 4, 5, 6).reduce(3, (x, y) -> x + y);
        Assert.assertTrue(reduce == 24);
        final Integer reduce1 = Stream.of("aa", "a", "f").reduce(0, (total, word) -> total + word.length(), (total1, total2) -> total1 + total2);
        final Integer reduce2 = Arrays.asList("1", "2", "3").stream().reduce(0, (total, word) -> total + word.length(), (total1, total2) -> total1 + total2);
        Assert.assertTrue(reduce1 == 4);
        Assert.assertTrue(reduce2 == 3);
    }

    @Test
    /**
     * 收集结果到[]和List中
     */
    public void test09() {
        //收集到String[]中
        final String[] strings = Stream.of("1", "2", "3").toArray(String[]::new);
        for (int i = 0; i < strings.length; i++) {
            System.out.println(strings[i]);
        }
        System.out.println("======");

        final HashSet<Object> hashSet = Stream.of("1", "2", "3").collect(HashSet::new, HashSet::add, HashSet::addAll);
        final Set<String> hashSet2 = Stream.of("1", "2", "3").collect(Collectors.toSet());


        Stream.of("1", "2", "3").collect(ArrayList::new, ArrayList::add, ArrayList::addAll);

        System.out.println(hashSet);
        System.out.println(hashSet2);

        //forEach  forEach和forEachOrder是终止操作,结束后你就不能操作这个流了(peek不会立即执行,可以对每个流执行某操作)
        Stream.of("1", "2", "3", "5", "4").forEach(System.out::print);
        System.out.println();
        Stream.of("1", "2", "3", "5", "4").forEachOrdered(System.out::print);
        System.out.println();

        System.out.println("======");
        Stream.of("a", "v").map(String::toUpperCase).forEach(System.out::print);
        Stream.of("a", "v").peek(String::toUpperCase).forEach(System.out::print);
        System.out.println();
        System.out.println("======");


    }

    @Test
    /**
     * 收集结果到Map中  Collectors.toMap可以进行分组
     */
    public void test10() {

        User a = new User("name1", 1);
        User b = new User("name2", 2);
        User c = new User("name1", 3);
        final List<User> users = Arrays.asList(a, b, c);
        b.setName(null);


        final List<User> collect = users.stream().filter(arg -> arg.getAge() > 1).collect(toList());
        System.out.println(collect);

        //分组
        Map<String, List<User>> lists = users.stream().collect(toMap(User::getName, Lists::newArrayList, (oldVal, newVal) -> {
            final ArrayList<User> users1 = Lists.newArrayList(oldVal);
            users1.addAll(newVal);
            return users1;
        }));
        System.out.println(lists);
        System.out.println("=======");
    }

    @Test
    /**
     * 分组,分片 Collectors.grouping分组
     */
    public void test11() {
        User a = new User("name1", 1);
        User b = new User("name2", 2);
        User c = new User("name1", 3);
        User d = new User("name30", 3);
        final List<User> users = Arrays.asList(a, b, c, d);

        //根据年龄分组 groupingByConcurrent会得到一个Concurrent的Map
        final Map<Integer, List<User>> collect = users.stream().collect(groupingBy(User::getAge));
        System.out.println(collect);
        System.out.println("collect0========");
        //根据年龄3岁分组
        final Map<Boolean, List<User>> collect1 = users.stream().collect(groupingBy(user -> user.getAge() == 3));
        System.out.println(collect1);
        System.out.println("collect1========");
        //分组计数  counting
        final Map<String, Long> collect2 = users.stream().collect(groupingBy(User::getName, counting()));
        System.out.println(collect2);
        System.out.println("collect2========");
        //分组求和  summingInt
        final Map<String, Integer> collect3 = users.stream().collect(groupingBy(User::getName, summingInt(User::getAge)));
        System.out.println(collect3);
        System.out.println("collect3========");
        //分组得出最大值 maxBy
        final Map<String, Optional<User>> collect4 = users.stream().collect(groupingBy(User::getName, maxBy(Comparator.comparing(User::getAge))));
        System.out.println(collect4);
        System.out.println("collect4========");
        //分组,返回统计信息 summarizingInt 包含数量,sum,min,max,average
        final Map<String, IntSummaryStatistics> collect5 = users.stream().collect(groupingBy(User::getName, summarizingInt(User::getAge)));
        System.out.println(collect5);
        System.out.println("collect5========");
        //mapping 先根据Age进行分组,然后根据Name的length比较得出最大值 使用mapping
        final Map<Integer, Optional<String>> collect6 = users.stream().collect(groupingBy(User::getAge, mapping(User::getName, maxBy(Comparator.comparing(String::length)))));
        System.out.println(collect6);
        System.out.println("collect6========");
        //reducing 根据name分组,然后把每个进行分组聚合
        final Map<Integer, String> collect7 = users.stream().collect(groupingBy(User::getAge, reducing("", User::getName, (s, t) -> s.length() == 0 ? t : s + "," + t)));
        System.out.println(collect7);
        System.out.println("collect7========");
        final Map<Integer, String> collect8 = users.stream().collect(groupingBy(User::getAge, mapping(User::getName, joining(","))));
        System.out.println(collect8);
        System.out.println("collect8========");
    }

    @Test
    /**
     * 原始类型流
     */
    public void test12() {
        IntStream.of(3, 2);
        Arrays.stream(new Integer[]{1, 2, 3}, 1, 3);
        IntStream.range(0, 100);
        //返回数组
        final int[] ints = IntStream.rangeClosed(0, 100).toArray();
        //返回OptionalInt
        final OptionalInt any = IntStream.rangeClosed(0, 100).findAny();
//        any.getAsInt();
        final IntSummaryStatistics intSummaryStatistics = IntStream.rangeClosed(0, 100).summaryStatistics();

        final IntStream intStream = Stream.of("1", "2", "323").mapToInt(String::length);
        intStream.forEach(System.out::print);


    }

    @Test
    /**
     * 并行流 .parallel方法可以把串行流转换为并行流
     */
    public void test13() {
        //调用.parallel,groupingByConcurrent
        final ConcurrentMap<Integer, List<String>> collect = Stream.of("1", "2", "23", "3").parallel().collect(groupingByConcurrent(String::length));
        System.out.println(collect);
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


    class User {
        private String name;
        private Integer age;

        User(String name, Integer age) {
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }

        @Override
        public String toString() {
            return "User{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    '}';
        }
    }


}