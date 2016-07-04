package com.jd.join;

import java.util.concurrent.TimeUnit;

/**
 * Created by caozhifei on 2016/7/4.
 */
public class JoinTest {
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName()+" 执行1结束");
            }
        },"join-1");
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName()+" 执行2结束");
            }
        },"join-2");
        t1.start();
        t2.start();
        t1.join(1000);
        t2.join(1000);
        System.out.println(Thread.currentThread().getName()+" 执行完成");
    }
}
