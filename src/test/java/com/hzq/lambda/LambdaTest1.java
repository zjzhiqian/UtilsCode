package com.hzq.lambda;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * Created by hzq on 16/5/20.
 */
public class LambdaTest1 {


    @Test
    /**
     * lambda表达式语法
     * lambda 排序 解决内部类问题
     */
    public void test01() {
        //lambda没有参数 ()->{};
        //lambda参数类型可推断 (args)->{args++;}
        //不需要为lambda表达式返回类型,可以从上下文推断出来(在某些分支中没有返回值是不合法的 比如if)
        List<Integer> list = Arrays.asList(2, 3, 1);
//        list.sort((com1, com2) -> Integer.compare(com1, com2));
        System.out.println(list);
    }


    @Test
    /**
     *
     * 1.3函数式接口
     * 个人理解 lambda是个FunctionalInterface,并且只有一个抽象方法
     */
    public void test03() {
        BiFunction<Integer, Integer, Integer> fun = Integer::compare;

        Runnable run = () -> {
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
        Callable call = () -> {
            TimeUnit.SECONDS.sleep(3);
            return null;
        };
        new Thread(run).run();
        new Thread(() -> System.out.println(3)).start();
        ExecutorService service = Executors.newFixedThreadPool(3);
        service.submit(call);
        service.shutdown();
    }

    @Test
    /**
     *1.4方法引用
     * (1)对象::实例方法
     * (2)类::静态方法
     * (3)类::实例方法  这时 第一个参数会成为执行方法的对象 比如 String::compareToIgnoreCases
     *
     * 如果有重载方法,编译器会试图从上下文中找到最匹配的一个方法
     * 方法引用不会单独存在,他会被转换为函数式接口的实例
     */
    public void test04() {
        List<Integer> list = Arrays.asList(2, 3, 1);
        list.sort(Integer::compare); //类::静态方法
        System.out.println(list);

        List<String> strList = Arrays.asList("1", "6", "5");

//        BiFunction<String,String,Integer> strFun = String::compareToIgnoreCase;

        strList.sort(String::compareToIgnoreCase); //类::实例方法
        System.out.println(strList);

        //new Thread 里面需要一个lambda表达式
        new Thread(System.out::println).start(); //对象::实例方法  注意:runnable方法没有参数,所以会调用println();
        new Thread(System.out::println).start();

    }


    @Test
    /**
     * 1.5构造器引用  buttion[]::new
     * TODO 构造器引用
     */
    public void test05() {
    }


    @Test
    /**
     * 1.6变量作用于
     * 在lambda中使用this,并不是调用Runnbale的this,而是这个类的this
     * lambda表达式会有闭包
     */
    public void test06() {

        // 这段代码无法通过编译,j必须是final的
//        int j = 0;
//        new Thread(() -> {
//            j++;
//        });

        //巧妙的方式
        int[] count = new int[1];
        new Thread(() -> count[0]++);//通过引用改变值
    }


}