package net.Socket;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPThreadServer {
    private ServerSocket serverSocket = null ;
    public TCPThreadServer(int port) throws IOException {
        serverSocket = new ServerSocket(port);
    }
    public void start() throws IOException {
        System.out.println("服务器启动~");
        while (true) {
            //1.通过accept来获取到客户端的连接
            Socket clientSocket = serverSocket.accept();
            //2.创建一个单独的线程来处理当前的客户单响应
            // 创建线程的目的是为了让服务器既能和具体的客户端进行良好的通信
            // 又能够及时的再次执行到accept.
            Thread t1 = new Thread(()->{
                processConnect(clientSocket);
            });
            t1.start();

        }

    }

    private void processConnect(Socket clientSocket) {
        System.out.printf("[%s:%d]客户端上线~\n",clientSocket.getInetAddress().toString(),clientSocket.getPort());
        try(BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
            while (true) {
               //1.读取请求
               String request = bufferedReader.readLine();
               //2.根据请求计算响应
                String response = process(request);
                //3.把响应写回给客户端
                bufferedWriter.write(response+"\n");
                bufferedWriter.flush();
                System.out.printf("[%s:%d] req : %s resp:%s \n",
                        clientSocket.getInetAddress().toString(),clientSocket.getPort()
                ,request,response);
            }

        } catch (IOException e) {
            System.out.printf("[%s:%d]客户端下线~\n",
                    clientSocket.getInetAddress().toString(),clientSocket.getPort());
        }
    }

    private String process(String request) {
        return request;
    }

    public static void main(String[] args) throws IOException {
        TCPThreadServer server = new TCPThreadServer(9090);
        server.start();
    }
}
