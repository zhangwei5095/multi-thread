package com.jd.server;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by caozhifei on 2016/6/30.
 */
public class DefaultThreadPool<Job extends Runnable> implements ThreadPool<Job> {
    //线程池最大数量
    private static final int  MAX_WORKER_NUM = 10;
    //线程池默认线程数量
    private static final int DEFAULT_WORKER_NUM =5;
    //线程池最小数量
    private static final int MIN_WORKER_NUM = 1;
    //工作列表
    private final LinkedList<Job> jobs = new LinkedList<Job>();
    //工作者列表
    private List<Worker> workers = Collections.synchronizedList(new ArrayList<Worker>());
    //工作者线程shul
    private int workerNum =  DEFAULT_WORKER_NUM;
    private AtomicLong threadNum = new AtomicLong();

    public DefaultThreadPool(int num){
        workerNum = num > MAX_WORKER_NUM ? MAX_WORKER_NUM : num < MIN_WORKER_NUM ? MIN_WORKER_NUM : num;
        initializeWorkers(num);
    }
    @Override
    public void execute(Job job) {
        if(job != null){
            //添加一个工作，并通知其他线程
            System.out.println("try lock");
            synchronized (jobs){
                System.out.println("get lock ok");
                jobs.addLast(job);
                jobs.notify();
                System.out.println("notify ok,jobs size="+jobs.size());
            }
        }
    }

    @Override
    public void shutdown() {
        for(Worker worker:workers){
            worker.shutdown();
        }
    }

    @Override
    public void addWorkers(int num) {
        synchronized (jobs){
            if(num + this.workerNum > MAX_WORKER_NUM){
                num = MAX_WORKER_NUM - this.workerNum;
            }
            initializeWorkers(num);
            this.workerNum = num;
        }
    }

    @Override
    public void removeWorker(int num) {
        synchronized (jobs){
            if(num >= this.workerNum){
                throw new IllegalArgumentException("beyond workNum");
            }
            int count = 0;
            while (count < num){
                Worker worker = workers.get(count);
                if(workers.remove(worker)){
                    worker.shutdown();
                    count++;
                }
            }
            this.workerNum -= count;
        }
    }

    @Override
    public int getJobSize() {
        return jobs.size();
    }

    private void initializeWorkers(int num){
        for(int i = 0;i<num;i++){
            Worker worker = new Worker();
            workers.add(worker);
            Thread thread = new Thread(worker,"ThreadPool-Worker-"+threadNum.incrementAndGet());
            thread.start();
        }
    }

    class Worker implements Runnable{
        private volatile boolean running = true;
        @Override
        public void run() {
            while (running){
                Job job = null;
                System.out.println(Thread.currentThread().getName()+" is get lock");
                synchronized (jobs){
                    //如果工作者列表为空，则wait
                    System.out.println(Thread.currentThread().getName()+"  get lock ok,jobs size ="+jobs.size());
                    while (jobs.isEmpty()){
                        try {
                            jobs.wait();
                            System.out.println(Thread.currentThread().getName()+"  get lock ok,and wait");
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                            return;
                        }
                    }
                    job = jobs.removeFirst();
                }
                System.out.println(Thread.currentThread().getName()+" get lock ok,and run job");
                if(job != null){
                    job.run();
                    System.out.println(Thread.currentThread().getName()+" run "+ job.toString());
                }
            }
        }
        public void shutdown(){
            running = false;
        }
    }
}
