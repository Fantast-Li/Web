package net.Socket.HTTPserver;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HttpServerV1 {
    private ServerSocket serverSocket = null;
    public HttpServerV1 (int port) throws IOException {
        serverSocket = new ServerSocket(port);
    }
    public void start() throws IOException {
        System.out.println("服务器启动");
        ExecutorService service = Executors.newCachedThreadPool();
        while(true) {
            //1.调用accept（调用频率要足够高）
            Socket clientSocket = serverSocket.accept();
            //2.根据返回Socket对象进行具体的交互操作
            service.submit(()->{
                process(clientSocket);
            });
        }
    }

    private void process(Socket clientSocket) {
        //【短连接】处理一次请求和响应（之前写的代码，一次连接中要循环处理多个请求和响应）
        System.out.printf("[%s:%d] 客户端发来的请求!\n",clientSocket.getInetAddress().toString(),clientSocket.getPort());
        try(BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            BufferedWriter bufferedWriter= new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()))
        ) {
            //进行具体的解析请求+ 构造响应
            //1.解析请求
            // 1.1线解析首行
            // 形如： GET http://123.207.58.25/ HTTP/1.1
            String firstLine = bufferedReader.readLine();
            String[] firstLineTokes =  firstLine.split(" ");
            String method = firstLineTokes[0];
            String url = firstLineTokes[1];
            String version = firstLineTokes[2];
            // 1.2 解析请求头（header）
            Map<String,String> headers = new HashMap<>();
            String line = "";
            while ((line = bufferedReader.readLine()) !=null && line.length()!=0){
                // 如果socket 中的内容读完了，或者读到空行了，就结束循环
                //此处读到的每一行数据都是一个header中的键值对，把这个键值对插入到hash表中
                //注意！分隔符是，冒号空格，是由二个字符构成的。
                String[] headerToken = line.split(": ");
                headers.put(headerToken[0],headerToken[1]);
            }
            //1.3 解析body（这个暂时先不管，对于GET没有body）
            //1.4打印请求解析的结果
            System.out.println(method+" "+url+" "+version);
            for (Map.Entry<String,String> entry : headers.entrySet()){
                System.out.println(entry.getKey()+": "+entry.getValue());
            }
            //2.根据请求计算响应（由于当前的响应是固定的，具体计算响应的过程就没啥意义）
             //此处我们改进下代码 ，根据用户请求中的url来做出不同的处理
            String body = "";
            if(url.equals("/200")){
                bufferedWriter.write("HTTP/1.1 200 OK\n");
                body = "<html>200 OK</html>";
            } else if (url.equals("/404")){
                bufferedWriter.write("HTTP/1.1 404 Not Found\n");
                body = "<html>404 Not Found</html>";
            } else if (url.equals("/303")){
                bufferedWriter.write("HTTP/1.1 303 See Other\n");
                bufferedWriter.write("Location: http://www.sogou.com\n");
            }
            // 3.构造响应，按理说是要根据不同的请求，生成不同的响应
            //此处为了简单，先无脑生成一个helloword的网页

            bufferedWriter.write("HTTP/1.1 200 OK\n") ;
            bufferedWriter.write("Content-Length: "+body.getBytes().length+"\n");
            bufferedWriter.write("Content-Type: text/html; charset=utf-8\n");
            bufferedWriter.write("\n");
            bufferedWriter.write(body);
            bufferedWriter.flush();


        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                //服务器主动关闭连接
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws IOException {
        HttpServerV1 httpServerV1 = new  HttpServerV1(9091);
        httpServerV1.start();
    }
}