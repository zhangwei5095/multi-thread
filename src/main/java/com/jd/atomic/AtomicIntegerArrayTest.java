package com.jd.atomic;

import java.util.concurrent.atomic.AtomicIntegerArray;

/**
 * Created by caozhifei on 2016/7/4.
 */
public class AtomicIntegerArrayTest {
    static int[] array = new int[]{1,2};
    static AtomicIntegerArray ai = new AtomicIntegerArray(array);

    public static void main(String[] args) {
        ai.getAndSet(1,6);
        System.out.println(ai.get(1));
        System.out.println(array[1]);
    }
}
