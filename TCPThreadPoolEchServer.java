package net.Socket;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;

public class TCPThreadPoolEchServer {
    private ServerSocket serverSocket = null;
    public TCPThreadPoolEchServer(int port) throws IOException {
        serverSocket = new ServerSocket(port);
    }
    public void start() throws IOException {
        System.out.println("服务器启动了！");
        //创建一个线程池
//        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(10,15,100, TimeUnit.SECONDS,
//                new LinkedBlockingQueue<>(10));
//        threadPoolExecutor.submit(()->{
//
//        });
        //因为不知道到客户端会连接多少 所以用newCachedThreadPool
        ExecutorService threadPool = Executors.newCachedThreadPool();
        while (true) {
            Socket clientSocket = serverSocket.accept();
            threadPool.submit(()->{
                prcessConnect(clientSocket);
            });
        }


    }

    private void prcessConnect(Socket clientSocket) {
        System.out.printf("[%s:%d] 客户端上线了~",clientSocket.getInetAddress().toString(),clientSocket.getPort());
        try(
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()))) {
            while (true) {
                //1.读取请求
                String request = bufferedReader.readLine();
                //2.处理请求
                String response = process(request);
                //3.将结果返回给客户端
                bufferedWriter.write(response+ "\n");
                bufferedWriter.flush();
                //打印日志
                System.out.printf("[%s:%d] req:%s  resp:%s" ,clientSocket.getInetAddress().toString(),clientSocket.getPort()
                ,request,response);
                
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String process(String request) {
        return request;
    }
}
