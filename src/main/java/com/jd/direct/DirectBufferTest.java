package com.jd.direct;

import java.nio.ByteBuffer;

public class DirectBufferTest {
    private static int NUM_THREADS = 20;

    public static void main(String[] args) throws InterruptedException {
        for(int i = 0; i < NUM_THREADS; i++) {
            Thread thread = new Thread("Test thread " + i) {
                public void run() {
                    int i = 0;
                    try {
                        while(true) {
                           // synchronized (DirectBufferTest.class) {
                                ByteBuffer bb = ByteBuffer.allocateDirect(1024 * 1024);
                                //ByteBuffer bb = ByteBuffer.allocate(2014*1024);
                                i++;
                            //加上线程休眠时间后，内存溢出问题消失
                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException x) {
                                // Restore interrupt status
                                Thread.currentThread().interrupt();
                            }
                                //System.gc();
                           // }
                        }
                    } catch(Throwable t) {
                        System.err.println("Thread " + Thread.currentThread().getName() + " got an OOM on iteration " + i);
                        t.printStackTrace();
                        System.exit(1);
                    }
                }
            };
            thread.start();
        }

        Thread.sleep(60 * 1000);
        System.out.println("No errors after 60 seconds.");
        //System.exit(0);
    }
}
