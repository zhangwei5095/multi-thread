package com.jd.direct;

import java.nio.ByteBuffer;

public class DirectBufferTest {
    private static int NUM_THREADS = 2;

    public static void main(String[] args) throws InterruptedException {
        for(int i = 0; i < NUM_THREADS; i++) {
            Thread thread = new Thread("Test thread " + i) {
                public void run() {
                    int i = 0;
                    try {
                        while(true) {
                            ByteBuffer bb = ByteBuffer.allocateDirect(1024 * 1024);
                            //ByteBuffer bb = ByteBuffer.allocate(2014*1024);
                            i++;
                            System.gc();
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
