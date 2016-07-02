package com.jd.notify;

import java.util.concurrent.TimeUnit;

/**
 * Created by caozhifei on 2016/6/28.
 */
public class SleepUtils {
    public static final void second(long seconds){
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
