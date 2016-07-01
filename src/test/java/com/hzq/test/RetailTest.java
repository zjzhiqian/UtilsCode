package com.hzq.test;

import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * 交集测试
 * Created by hzq on 16/6/14.
 */
public class RetailTest {


    @Test
    public void testArrayRetain() {
        final ArrayList<Integer> integers = Lists.newArrayList(2, 5, 1, 3);
        final ArrayList<Integer> integers1 = Lists.newArrayList(1, 2, 5);
        System.out.println(getRetain(integers, integers1));
    }


    private List<Integer> getRetain(List<Integer> list1, List<Integer> list2) {
        int m = 0;
        for (int i = 0; i < list1.size(); i++) {
            if (list2.contains(list1.get(i))) {
                list1.set(m++, list1.get(i));
            }
        }
        return list1.subList(0, m);
    }

    @Test
    public void testMax() {
        final ArrayList<Integer> integers = Lists.newArrayList(2, 3, 2, -2, 2, 2, 4, -3);
        calcMax(integers.toArray());
    }

    private void calcMax(Object[] integers) {

        Integer maxVal = 0, tmpVal = 0;
        for (Object integer : integers) {
            tmpVal += (Integer) integer;
            if (tmpVal > maxVal)
                maxVal = tmpVal;
            if (tmpVal < 0)
                tmpVal = 0;
        }
        System.out.println(maxVal);
    }


}
