package com.hzq.enums;


import org.apache.commons.lang3.StringUtils;

import java.util.Random;

/**
 * enum常用方法以及 enum设置属性
 * Created by hzq on 16/5/10.
 */

enum Color implements Runnable { //所有的enum类型都继承自Enum类,java不支持多继承,所以只能实现接口
    RED(0),
    YELLOW(1),
    BLACK(2);
    private Integer value;

    Color(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }


    public static Color getEnum(Integer ordinal) {
        for (Color color : values()) {
            if (color.ordinal() == ordinal) {
                return color;
            }
        }
        return null;
    }

    public static Color netx() {
        return values()[new Random(47).nextInt(values().length)];
    }

    /**
     * enume覆盖父类方法
     *
     * @return
     */
    @Override
    public String toString() {
        return this.ordinal() + "";
    }

    /**
     * enume继承接口
     */
    @Override
    public void run() {

    }
}

public class EnumClass {

    public static void main(String[] args) {
        for (Color color : Color.values()) {
            System.out.println(color.ordinal());
            System.out.println(color.compareTo(Color.YELLOW));
            System.out.println(color.equals(Color.YELLOW));
            System.out.println(color == Color.YELLOW);
            System.out.println(color.getDeclaringClass());
            System.out.println(color.name());
            System.out.println("==============");
        }
        Color color = Enum.valueOf(Color.class, "RED");
        System.out.println(color.getValue());//添加构造方法
        System.out.println(color.toString());//覆盖父类方法

//        Color.class.enumConstantDirectory().get("1"); package access

        //enum的遍历
        switch (Color.getEnum(1)) {
            case BLACK:
                break;
            case YELLOW:
                System.out.println("yellow....");
                break;
            case RED:
                break;
        }

        Color.values();
        Color.valueOf("RED");
        //enume在编译的时,编译器会为其添加vaueOf,values方法
//        final class Color extends java.lang.Enum{
//            public static final  Color RED;
//            public static final  Color YELLOW;
//            public static final  Color BLACK;
//            public static final Color[] values;
//            public static Color valueOf(java.lang.String);
//            static{};
//        }
        Color[] values2 = Color.class.getEnumConstants(); //另一种方法获得values,getEnumConstants是Class上的方法,可以对不是枚举类调用,但是会抛空指针


        System.out.println(Color.netx());
    }
}

