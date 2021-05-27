package net.Socket;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPEchServer {
    private ServerSocket serverSocket = null ;
    //当端口号被占用的时候会报异常，无法启动时间
    public TCPEchServer(int port) throws IOException {
        serverSocket = new ServerSocket(port);
    }
    public void start() throws IOException {
        System.out.println("服务器启动~");
        //服务器启动之后会尝试循环处理多个连接
        while (true) {
            //1.先通过accept 来接收客户端的连接（接听电话）
            Socket clientSocket = serverSocket.accept();
            //循环处理多次请求
            processConnect(clientSocket);
        }
    }

    private void processConnect(Socket clientSocket) {
        //处理一次连接过程中，就需要处理多 次请求
        System.out.printf("[%s:%d] 客户端上线~\n",
                clientSocket.getInetAddress().toString(),clientSocket.getPort());
        //为了后面读写做准备，主要准备好对应的流对象
        try(BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(clientSocket.getInputStream()));
            BufferedWriter bufferedWriter = new BufferedWriter(
                    new OutputStreamWriter(clientSocket.getOutputStream()))
        ) {
            while (true) {
                //1.读取请求并解析
                String request = bufferedReader.readLine();
                if (request == null) {
                    //读到eof了，此时说明客户端已经关闭了
                    System.out.printf( "[%s:%d] 客户端下线~[1]\n",
                            clientSocket.getInetAddress().toString(),clientSocket.getPort());
                    return;
                }
                //2.根据请求计算响应
                String response = process(request);
                //3.把响应结果写回客户端
                bufferedWriter.write(response+"\n"); //此处 \n 很重要 此处的\n 是为了客户端能够readline
                bufferedWriter.flush(); //此处的flush 很重要
                //打印日志
                String log = String.format("[%s:%d] req: %s;  resp :%s",
                        clientSocket.getInetAddress().toString(),clientSocket.getPort(),
                        request,response);
                System.out.println(log);
            }

        } catch (IOException e) {
            System.out.printf( "[%s:%d] 客户端下线~[2]\n",
                    clientSocket.getInetAddress().toString(),clientSocket.getPort());
        }
    }

    private String process(String request) {
         return request;
    }

    public static void main(String[] args) throws IOException {
        //端口号的范围 0-65535
        // 端口号不能太小  （也不能使用0-》1023）给操作系统内部的应用程序 .
        TCPEchServer server = new TCPEchServer(9090);
        server.start();
    }
}
