package Http;


/*
实现自己的http服务器
 */


import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class MyHttpServer {

    // TCP 的服务器, 按照HTTP 协议的规则进行数据的获取和返回 = HTTP 服务器
    private ServerSocket serverSocket = null;
    public MyHttpServer(int port) throws IOException {
        //创建一个TCP的服务器
        serverSocket = new ServerSocket(port);
    }
    // 创建一个开始的方法
    public void start() throws IOException {
        System.out.println("服务器已启动");
        // 运用线线程池来提高服务  一个请求过来 使用一个线程
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(100,100,100,
                TimeUnit.SECONDS, new LinkedBlockingQueue<>(1000));
        while(true) {
            Socket clientSocket = serverSocket.accept();
            threadPoolExecutor.submit(()->{
               try {
                   processSocket(clientSocket);
               } catch (IOException e) {
                   e.printStackTrace();
               }
            });
        }
    }

    private void processSocket(Socket clientSocket) throws IOException {

        try (
                BufferedReader reader  = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter  writer = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
        ) {
            //获取首行信息   request 请求行 Method URL Version
            String firstLine = reader.readLine();
            String[] firstLineArr = firstLine.split(" ");
            //请求方法
            String method = firstLineArr[0];
            //URl
            String url = firstLineArr[1];
            //版本号
            String version = firstLineArr[2];
            System.out.println(String.format("method=%s,url=%s,version=%s",method,url,version));
            // 2.请求头
            Map<String,String> reqHead = new HashMap<>();
            String item ;
            while ((item = reader.readLine()) !=null && item.length() > 0) {
                String[] itemArr = item.split(": ");
                reqHead.put(itemArr[0],itemArr[1]);
            }
            //4.body 暂不考虑
            //二 响应
            writer.println("HTTP/1.1 200 OK"); //1.首行信息
            //响应头
            // Content—Type
            // Content-Length.
            writer.println("Content-Type: text/html;charset=utf-8");

            String body = "<h1>你好，咪咪老婆<h1>";
            writer.println("Content-Length: "+ body.getBytes().length); // 注意 这里是字节长度，不是字符长度
            // 空行
            writer.println();
            //4.body
            writer.println(body);
            //刷新缓冲区
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            clientSocket.close();
        }
    }

    public static void main(String[] args) throws IOException {
        MyHttpServer myHttpServer = new MyHttpServer(9090);
        myHttpServer.start();
    }
}

