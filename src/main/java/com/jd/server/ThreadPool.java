package com.jd.server;

/**
 * Created by caozhifei on 2016/6/30.
 */
public interface ThreadPool<Job extends Runnable> {
    //执行job，job需要实现Runnable
    void execute(Job job);
    //关闭线程池
    void shutdown();
    //增加工作线程
    void addWorkers(int num);
    //减少工作线程
    void removeWorker(int num);
    //获取正则等待执行的任务数量
    int getJobSize();
}
