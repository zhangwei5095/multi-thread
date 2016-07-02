package com.jd.pool;

import java.sql.Connection;
import java.util.LinkedList;

/**
 * Created by caozhifei on 2016/6/29.
 */
public class ConnectionPool {
    private LinkedList<Connection> pool = new LinkedList<Connection>();
    private int initialSize = 10;

    /**
     * 初始化连接池
     * @param initialSize
     */
    public ConnectionPool(int initialSize) {
        if(initialSize > 0) {
            this.initialSize = initialSize;
        }
        for(int i=0;i<initialSize;i++){
            pool.addLast(ConnectionDriver.createConnection());
        }
    }

    /**
     * 释放连接池中的连接
     * @param connection
     */
    public void releaseConnection(Connection connection){
        if(connection != null){
            synchronized (pool){
                //连接放回连接池
                pool.addLast(connection);
                //连接放回连接池后，需要通知其他线程连接池中已经归还了一个连接，可以获取连接了
                pool.notifyAll();
            }
        }
    }
    public Connection borrowConnection(long mills) throws InterruptedException{
        synchronized (pool){
            if(mills <= 0){
                while (pool.isEmpty()){
                    pool.wait();
                }
                return pool.removeFirst();
            }else{
                long future = System.currentTimeMillis() + mills;
                long remaining = mills;
                while (pool.isEmpty() && remaining > 0){
                    pool.wait(remaining);
                    remaining = future - System.currentTimeMillis();
                }
                Connection result = null;
                if(!pool.isEmpty()){
                    result = pool.removeFirst();
                }
                return result;
            }
        }
    }
}
