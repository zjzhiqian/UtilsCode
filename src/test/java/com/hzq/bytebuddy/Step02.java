package com.hzq.bytebuddy;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.FixedValue;
import net.bytebuddy.implementation.MethodDelegation;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static net.bytebuddy.matcher.ElementMatchers.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

/**
 * Created by hzq on 16/11/11.
 */
public class Step02 {


    private static final String PATH = "/Users/hzq/Desktop";

    @Test
    /**
     *  拦截方法
     */
    public void test01() throws IllegalAccessException, InstantiationException, NoSuchFieldException, IOException {
        String result = new ByteBuddy()
                .subclass(Object.class)
                .name("com.hzq.xxx")
                .make()
                .load(getClass().getClassLoader())
                .getLoaded()
                .newInstance()
                .toString();
        System.out.println(result);

        String result2 = new ByteBuddy()
                .subclass(Object.class)
                .name("com.hzq.yyy2")
                //参数是一个 ElementMatcher 获取一个选中method named("").and(returns(String.class).and(takesArguments(0)))
                .method(named("toString").and(returns(String.class).and(takesArguments(0))))
                //参数是一个 Implementation
                .intercept(FixedValue.value("return value"))  //覆盖实现  返回一个固定值
                .make()
                .load(getClass().getClassLoader())
                .getLoaded()
                .newInstance()
                .toString();
        System.out.println(result2);

        //named("toString") 拦截了所有名称为 toString的方法(包括重载方法)
        System.out.println(new ByteBuddy().subclass(TestClass01.class)
                .method(named("toString")).intercept(FixedValue.value("999"))
                .make()
                .load(getClass().getClassLoader())
                .getLoaded()
                .newInstance()
                .toString());
        System.out.println(new ByteBuddy().subclass(TestClass01.class)
                .method(named("toString")).intercept(FixedValue.value("999"))
                .make()
                .load(getClass().getClassLoader())
                .getLoaded()
                .newInstance()
                .toString("123"));


    }

    @Test
    /**
     *  方法匹配规则: 类的所有方法优先匹配最后一个,依次向上,所以最后一个应该是最具体的方法
     *  defineMethod("hzq", String.class, 2)method name,method return type ,method modifiers
     *  匹配完成后绑定规则:
     *      1. @IgnoreForBinding 的方法不会被绑定   @BindingPriority 高优先级的有限被绑定
     *      2. 相同的方法名优先
     *      3. 通过@Argument注解绑定到了 过滤到了两个相同的方法  更具体(more specific)的参数 优先被绑定  (和java编译器的算法相同)
     *      4. 参数同等specific 绑定了更多参数的方法优先
     *
     */
    public void test02() throws IllegalAccessException, InstantiationException, NoSuchFieldException, IOException {
        DynamicType.Unloaded<Foo> unloaded = new ByteBuddy()
                .subclass(Foo.class)
                .name("com.hzq.bytebuddy.yyy")
                .method(isDeclaredBy(Foo.class)).intercept(FixedValue.value("One!")) //order 3
                .method(named("foo")).intercept(FixedValue.value("Two!")) //order 2
                .method(named("foo").and(takesArguments(1))).intercept(FixedValue.value("Three!")) //order1
                .make();
        unloaded.saveIn(new File(PATH));

        //defineMethod 重新定义一个方法
        DynamicType.Unloaded<Object> unloaded2 = new ByteBuddy()
                .subclass(Object.class)
                .name("com.hzq.yyy2")
                .defineMethod("hzq", String.class, 2).intercept(FixedValue.value("123"))
                .make();
        unloaded2.saveIn(new File(PATH));

    }

    @Test
    /**
     * FixedValue
     * Byte Buddy's assignment behavior is customizable. Again, Byte Buddy only provides a sane default
     * which mimics the assignment behavior of the Java compiler. Consequently, Byte Buddy allows an
     * assignment of a type to any of its super types and it will also consider to box primitive values
     * or to unbox their wrapper representations. Note however, that Byte Buddy does currently not fully
     * support generic types and will only consider type erasures. Therefore, it is possible that Byte Buddy
     * causes heap pollution. Instead of using the predefined assigner, you can always implement your own
     * Assigner which is capable of type transformations that are not implicit in the Java programming
     * language. We will look into such custom implementations in the last section of this tutorial. For
     * now, we settle for mentioning that you can define such custom assigners by calling withAssigner
     * on any FixedValue implementation.
     */
    public void test03() throws IllegalAccessException, InstantiationException, NoSuchFieldException, IOException {

    }

    @Test
    /**
     * 代理方法实现 //TODO Target的方法为什么必须是static的
     *  Byte Buddy does not require target methods to be named equally to a source method.
     *
     */
    public void test04() throws IllegalAccessException, InstantiationException, NoSuchFieldException, IOException {
        DynamicType.Unloaded<Source> unloaded = new ByteBuddy()
                .subclass(Source.class)
                .name("com.hzq.bytebuddy.yyy5")
                .method(named("hello")).intercept(MethodDelegation.to(Target.class))
                .make();
        unloaded.saveIn(new File(PATH));

        String result = unloaded.load(getClass().getClassLoader())
                .getLoaded()
                .newInstance()
                .hello("World");
        assertEquals(result, "Hello World!");
    }

    @Test
    /**
     *  使用@Argument(1)注解 调换参数顺序
     *  @AllArguments 的使用方法
     *  @This 注解的用法  A typical reason for using the @This annotation to gain access to an instance's fields.
     */
    public void test05() throws IllegalAccessException, InstantiationException, NoSuchFieldException, IOException {
        DynamicType.Unloaded<Source> unloaded1 = new ByteBuddy()
                .subclass(Source.class)
                .name("com.hzq.bytebuddy.yyy6")
                .method(named("test1")).intercept(MethodDelegation.to(Target.class))
                .make();
        unloaded1.saveIn(new File(PATH));
        System.out.println(unloaded1.load(getClass().getClassLoader())
                .getLoaded()
                .newInstance()
                .test1("first", "second", "third"));
        System.out.println("=================================");
        //@AllArguments的用法
        //Parameters that carry the @AllArguments annotation must be of an array type
        //and are assigned an array containing all of the source method's arguments.
        //For this purpose, all source method parameters must be assignable to the array's component type
        DynamicType.Unloaded<Source2> unloaded2 = new ByteBuddy()
                .subclass(Source2.class)
                .name("com.hzq.bytebuddy.yyy7")
                .method(named("test1")).intercept(MethodDelegation.to(Target2.class))
                .make();
        unloaded2.saveIn(new File(PATH));
        System.out.println(unloaded2.load(getClass().getClassLoader())
                .getLoaded()
                .newInstance()
                .test1("first", "second", "third"));

        System.out.println("=================================");
        //@this
        DynamicType.Unloaded<Foo2> unloaded = new ByteBuddy()
                .subclass(Foo2.class)
                .name("com.hzq.bytebuddy.yyy8")
                .method(isDeclaredBy(Foo2.class)).intercept(MethodDelegation.to(Bar2.class))
                .make();
        unloaded.saveIn(new File(PATH));
        Foo2 instance = unloaded.load(getClass().getClassLoader()).getLoaded().newInstance();
        System.out.println(instance.toString());
        System.out.println(instance.foo());
        assertThat(instance.foo(), is((Object) instance));
    }

    @Test
    /**
     *  @Origin注解
     *  Parameters that are annotated with @Origin must be of used on any of the types
     *  Method, Constructor, Executable, Class, MethodHandle, MethodType, String or int.
     *  Depending on the parameter's type, it is assigned a Method or Constructor reference
     *  to the original method or constructor that is now instrumented or a reference to
     *  the dynamically created Class. When using Java 8, it is also possible to receive
     *  either a method or constructor reference by using the Executable type in the interceptor.
     *  If the annotated parameter is a String, the parameter is assigned the value that
     *  the Method>'s toString method would have returned. In general, we recommend the use of
     *  these String values as method identifiers wherever possible and discourage the use of
     *  Method objects as their lookup introduces a significant runtime overhead. To avoid this
     *  overhead, the @Origin annotation also offers a property for caching such instances for reuse.
     *  Note that the MethodHandle and MethodType are stored in a class's constant pool such that classes
     *  using these constants must at least be of Java version 7. When using the @Origin annotation on a
     *  parameter of type int, it is assigned the modifier of the instrumented method.
     */
    public void test06() throws IllegalAccessException, InstantiationException, NoSuchFieldException, IOException {


    }



}
