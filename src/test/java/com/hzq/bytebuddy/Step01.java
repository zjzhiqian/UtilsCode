package com.hzq.bytebuddy;

import com.fasterxml.jackson.databind.util.ClassUtil;
import com.hzq.bytebuddy.entity.Father;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.NamingStrategy;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.FixedValue;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.function.Function;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by hzq on 16/11/4.
 */
public class Step01 {

    @Test
    public void test01() throws IllegalAccessException, InstantiationException {
        Class<?> dynamicType = new ByteBuddy()
                .subclass(Father.class)
                .method(ElementMatchers.named("fatherEat"))
                .intercept(FixedValue.value("Hello World!"))
                .make()
                .load(getClass().getClassLoader())
                .getLoaded();

        Object o = dynamicType.newInstance();
        assertThat(o, instanceOf(Father.class));
        assertThat(((Father) o).fatherEat(), is("Hello World!"));
    }


    @Test
    public void test02() throws IllegalAccessException, InstantiationException {
        Class<? extends Function> apply = new ByteBuddy()
                .subclass(Function.class)
                .method(ElementMatchers.named("apply"))
                .intercept(MethodDelegation.to(new GreetingInterceptor()))
                .make()
                .load(getClass().getClassLoader())
                .getLoaded();
        System.out.println(apply.newInstance().apply("Byte Buddy"));
    }


    @Test
    /**
     * 生成动态类.class(内存中)  包名策略,指定父类
     * 默认生成 : 父类名称 + $ByteBuddy$SpyAA46L
     * 如果父类是java.lang包下的 : net.bytebuddy.renamed.java.lang.xxx$ByteBuddy$CydL9ksK
     *
     * DynamicType.Unloaded 本质上就是一个以2进制存储的 .class文件
     * unloaded.saveIn(new File("/Users/hzq/Desktop"));  将class文件保存下来
     * unloaded.inject(new File("/Users/hzq/Desktop"));  将class文件 放到已经存在的jar里面
     */
    public void test03() throws IllegalAccessException, InstantiationException, IOException {

        DynamicType.Unloaded<Object> unloaded = new ByteBuddy()
                .with(new NamingStrategy.SuffixingRandom("ccc"))
                .with(new NamingStrategy.AbstractBase() {
                    @Override
                    protected String name(TypeDescription superClass) {
                        return "com.hzq.bytebuddy.entity." + superClass.getSimpleName();
                    }
                })   //指定生成策略
                .subclass(Object.class)  //父类
//                .name("com.hzq.bytebuddy.entity.Father") //指定类名
                .make();

        unloaded.saveIn(new File("/Users/hzq/Desktop"));
        unloaded.inject(new File("/Users/hzq/Desktop"));

        Class<?> loaded = unloaded.load(getClass().getClassLoader()).getLoaded();
        System.out.println(1);


        //领域模型,不可变
//        ByteBuddy byteBuddy = new ByteBuddy();
//        byteBuddy.withNamingStrategy(new NamingStrategy.SuffixingRandom("suffix"));
//        DynamicType.Unloaded<?> dynamicType = byteBuddy.subclass(Object.class).make(); 这段代码 withNamingStrategy 会返回一个新的ByteBuddy 而不是对原有的byteBuddy作出改变

    }

//    redefinition   //添加字段,方法,覆盖原有方法
//    rebasing       //保留原有的方法,生成一个private的别名方法  然后别名方法(修改实现),内部可以调用这个方法
//    new ByteBuddy().subclass(Foo.class)
//    new ByteBuddy().redefine(Foo.class)
//    new ByteBuddy().rebase(Foo.class)

    @Test
    /**
     * load一个 .class
     */
    public void test04() throws IllegalAccessException, InstantiationException {

    }

}




