package socket;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("127.0.0.1",8080);
        //socket 已经建立连接
        InputStream inputStream = socket.getInputStream();
        Scanner scanner = new Scanner(inputStream,"UTF-8");


        OutputStream outputStream = socket.getOutputStream();
        Writer writer = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8);
        PrintWriter printWriter = new PrintWriter(writer);
        printWriter.println("你好我是中国人"); //向服务器发送消息
        printWriter.flush();
        String message = scanner.nextLine(); //从服务器读取消息
        System.out.println(message);
        socket.close();
    }
}
