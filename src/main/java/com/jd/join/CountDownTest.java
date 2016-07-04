package com.jd.join;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Created by caozhifei on 2016/7/4.
 */
public class CountDownTest {
    static CountDownLatch latch = new CountDownLatch(3);

    public static void main(String[] args) throws InterruptedException {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName()+" count "+1);
                latch.countDown();
                try {
                    TimeUnit.SECONDS.sleep(3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName()+" count "+2);
                latch.countDown();
            }
        },"countDown-Thread");
        t.start();
        System.out.println(Thread.currentThread().getName()+" count "+3);
        latch.countDown();
        latch.await(1,TimeUnit.SECONDS);
        System.out.println(Thread.currentThread().getName()+" 执行完成 ");
    }
}
