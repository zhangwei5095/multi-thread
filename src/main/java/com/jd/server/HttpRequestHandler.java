package com.jd.server;

import java.io.*;
import java.net.Socket;

/**
 * Created by caozhifei on 2016/6/30.
 */
public class HttpRequestHandler implements Runnable {
    private String basePath;
    private Socket socket;

    public HttpRequestHandler(String basePath, Socket socket) {
        this.basePath = basePath;
        this.socket = socket;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("HttpRequestHandler{");
        sb.append("basePath='").append(basePath).append('\'');
        sb.append(", socket=").append(socket);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public void run() {
        String line = null;
        BufferedReader br = null;
        BufferedReader reader = null;
        PrintWriter out = null;
        InputStream in = null;
        try{
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String header = reader.readLine();
            String filePath = basePath + header.split(" ")[1];
            System.out.println("get filePath = "+filePath);
            out =new  PrintWriter(socket.getOutputStream());
            if(filePath.endsWith("jpg") || filePath.endsWith("ico")){
                in = new FileInputStream(filePath);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                int i=0;
                while ((i = in.read()) != -1){
                    baos.write(i);
                }
                byte[] array = baos.toByteArray();
                out.println("HTTP/1.1 200 OK");
                out.println("Server: Molly");
                out.println("Content-Type: image/jpeg");
                out.println("Content-length: "+array.length);
                out.println("");
                socket.getOutputStream().write(array, 0, array.length);
            }else{
                br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath)));
                out = new PrintWriter(socket.getOutputStream());
                out.println("HTTP/1.1 200 OK");
                out.println("Server: Molly");
                out.println("Content-Type: text/html; charset=UTF-8");
                out.println("");
                while ((line = br.readLine()) != null){
                    out.println(line);
                }
                out.flush();
            }
        }catch (Exception e){
            out.println("HTTP/1.1 500");
            out.println("");
            out.flush();
            e.printStackTrace();
        }finally {
            close(br, in, reader, out);
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



    private  void close(Closeable... closeables){
        if(closeables != null){
            for(Closeable closeable:closeables){
                try {
                    if(closeable != null) {
                        closeable.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
