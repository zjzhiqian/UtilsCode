package com.hzq.bytebuddy;

import com.hzq.bytebuddy.entitypack.DefaultMethod;
import net.bytebuddy.implementation.bind.annotation.*;

import java.lang.reflect.Method;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.function.Function;

/**
 * Entites
 * Created by hzq on 16/11/9.
 */
public class Entites {


}


class Father {
    public String fatherEat() {
        return "father eat";
    }
}


class TestClass01 {
    public String toString() {
        return "123";
    }

    public String toString(String name) {
        return "456";
    }
}


class Source {
    public String hello(String name) {
        return name;
    }

    public String test1(String str1, String str2, String str3) {
        return str1 + str2 + str3;
    }
}

class Target {

    public static String hello(String name, @Origin Method method) {
        return "Hello " + name + "!";
    }

    public static String test1(@Argument(1) String str1, String str2, @Argument(0) String str3) {
        return str1 + str2 + str3;
    }
}


class Source2 {

    public String test1(String str1, String str2, String str3) {
        return str1 + str2 + str3;
    }
}

class Target2 {

    public static String test1(@AllArguments String[] a) {
        return a[0] + a[1] + a[2];
    }
}


class Source3 {

    public String test1(String str1, String str2, String str3) {
        return str1 + str2 + str3;
    }
}

class Target3 {

    public static String test1(String str1, String str2, String str3) {
        return str1 + str2 + str3;
    }
}


class Bar {
    public String bar() {
        return "bar declared";
    }

    public String toString() {
        return "bar";
    }
}

class Foo {
    public String bar() {
        return null;
    }

    public String foo() {
        return null;
    }

    public String foo(Object o) {
        return null;
    }
}


class Foo2 {

    public Object foo() {
        return null;
    }
}

class Bar2 {

    public static Object qux(@This Foo2 foo2) {
        return foo2;
    }

    public static Object baz(@This Void v) {
        return v;
    }
}

class MemoryDatabase {
    public List<String> load(String info) {
        System.out.println("loading......" + info);
        return null;
    }
}

class LoggerInterceptor {
    public static List<String> log(@SuperCall Callable<List<String>> zuper)
            throws Exception {
        System.out.println("Calling database");
        try {
            return zuper.call();
        } finally {
            System.out.println("Returned from database");
        }
    }
}

class ChangingLoggerInterceptor {
    public static List<String> log(@Super MemoryDatabase zuper, String info) {
        System.out.println("Calling database");
        try {
            return zuper.load(info + " (logged access)");
        } finally {
            System.out.println("Returned from database");
        }
    }
}


class Loop {
    public String loop(String value) {
        return value + 1;
    }

    public int loop(int value) {
        return value + 1;
    }
}


class Interceptor {
    @RuntimeType
    public static Object intercept(@RuntimeType Object value) {
        System.out.println("Invoked method with: " + value);
        return value;
    }
}


class RunnableClass {
    public static Object intercept(@DefaultCall Runnable runnable) {
        System.out.println("before Invoked method ");
        runnable.run();
        return "123";
    }
}


class DefaultCallSuper {
    public static Object log(@Super DefaultMethod method) {
        System.out.println("Calling database");
        try {
            return method.caca();
        } finally {
            System.out.println("Returned from database");
        }
    }
}

class ForwardingLoggerInterceptor {

    private MemoryDatabase memoryDatabase;

    ForwardingLoggerInterceptor(MemoryDatabase memoryDatabase) {
        this.memoryDatabase = memoryDatabase;
    }


    public List<String> log(@Pipe Function<MemoryDatabase, List<String>> pipe) {
        System.out.println("Calling database");
        try {
            return pipe.apply(memoryDatabase);
        } finally {
            System.out.println("Returned from database");
        }
    }
}