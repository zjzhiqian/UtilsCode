package com.hzq.base.enums;

import java.util.Random;

/**
 * enum 使用接口组织分组
 * Created by hzq on 16/5/11.
 */


interface Food {
    enum fruit implements Food {
        APPLE,
        PEACH;
    }

    enum vegetab implements Food {
        POTATO,
        TOMATO;
    }
}

enum Groups {
    FRUIT(Food.fruit.class),
    VEGET(Food.vegetab.class);

    private Food[] values;

    Groups(Class<? extends Food> clazz) {
        values = clazz.getEnumConstants();
    }

    public Food random() {
        return values[new Random().nextInt(values().length)];
    }
}

public class EnumGroup1 {

    public static void main(String... agrs) {
        for (Groups groups : Groups.values()) {
            System.out.println(groups.random());
        }
    }


}
