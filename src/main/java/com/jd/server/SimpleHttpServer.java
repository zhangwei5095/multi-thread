package com.jd.server;


import java.io.File;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by caozhifei on 2016/6/30.
 */
public class SimpleHttpServer {
    static ThreadPool<HttpRequestHandler> threadPool = new DefaultThreadPool(10);
    static String basePath;
    static ServerSocket serverSocket;
    static int port = 8080;

    public static void setBasePath(String basePath) {
        if(basePath != null && new File(basePath).exists() && new File(basePath).isDirectory()) {
            SimpleHttpServer.basePath = basePath;
        }
    }

    public static void setPort(int port) {
        if(port > 0) {
            SimpleHttpServer.port = port;
        }
    }

    public static void start() throws Exception{
        serverSocket = new ServerSocket(port);
        Socket socket = null;
        while ((socket = serverSocket.accept()) != null){
            threadPool.execute(new HttpRequestHandler(basePath,socket));
        }
        serverSocket.close();
    }

    public static void main(String[] args) {
        String basePath = "D:/model";
        setPort(80);
        setBasePath(basePath);
        try {
            start();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
