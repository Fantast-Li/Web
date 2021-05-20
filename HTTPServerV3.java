package net.Socket.HTTPserver.httpSeverV3;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HTTPServerV3 {
    private ServerSocket serverSocket = null;
    public HTTPServerV3(int port) throws IOException {
        serverSocket = new ServerSocket(port);
    }
    public void start() throws IOException {
        System.out.println("服务器启动~");
        ExecutorService service = Executors.newCachedThreadPool();
        while (true) {
            Socket clientSocket = serverSocket.accept();
            service.submit(()->{
                try {
                    process(clientSocket);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    private void process(Socket clientSocket) throws IOException {
        try {
            //1.构建请求对象
            Request request = Request.build(clientSocket.getInputStream());
            System.out.println("request: " + request);
            //2.准备一个响应对象
            Response response = Response.build(clientSocket.getOutputStream());
            //3.根据请求计算响应
            System.out.println("1");
            if (request.getMethod().equalsIgnoreCase("GET")) {
                System.out.println("2");
                doGet(request, response);
                System.out.println("response: "+response);
            } else if (request.getMethod().equalsIgnoreCase("POST")) {
                doPost(request,response);
            } else {
                response.setStatus(405);
                response.setMessage("Method Not Allowed");
            }
            //4.把响应结果写回到客户端
            response.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void doPost(Request request, Response response) {

    }

    private void doGet (Request request, Response response){

        if (request.getUrl().startsWith("/test")) {
            System.out.println(4);
            response.setStatus(200);
            response.setMessage("OK");
            response.setHeader("Content-Type: ","text/html; charset=utf-8");
            response.writeBody("<html>");
            response.writeBody("200 OK");
            response.writeBody("</html>");
        }
    }

    public static void main(String[] args) throws IOException {
        HTTPServerV3 httpServerV3 = new HTTPServerV3(9091);
        httpServerV3.start();
    }
    }