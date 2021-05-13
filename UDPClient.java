package net.Socket;

import java.io.IOException;
import java.net.*;
import java.util.Scanner;

public class UDPClient {
    //1.初始化DatagramSocket
    //2.进入主循环执行相应流程
    //  2.1.先构建命令
    //  2.2.发送命令（服务器端发送命令）
    //  2.3 阻塞等待服务器端 响应
    //  2.4 服务器信打印。
    private DatagramSocket datagramSocket = null ;
    public UDPClient () throws UnknownHostException, SocketException {
        datagramSocket = new DatagramSocket();
    }
    public void start(String serverIP , int serverPort) throws IOException {
        //进入主循环执行相应的流程
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("请输入信息->");
            //2.1 得到用户输入的字符串
            String requestContext = scanner.nextLine();
            //构建传输的对象
            DatagramPacket requestDatagramPacket = new DatagramPacket(
                    requestContext.getBytes(),requestContext.getBytes().length
                    ,InetAddress.getByName(serverIP),serverPort //服务器IP ，端口号
            );
            //2.2将命令发送给服务器
            datagramSocket.send(requestDatagramPacket);
            //2.3 等待服务器响应
            DatagramPacket responsePacket = new DatagramPacket(
                    new byte[4096],4096
            );
            datagramSocket.receive(responsePacket);
            String responseContext = new String(responsePacket.getData());
            System.out.println("服务器响应结果："+responseContext);
        }
    }

    public static void main(String[] args) throws IOException {
        //服务器IP
        final String serverIP = "127.0.0.1";
        //服务器端口号
        final int serverPost = 9090;
        UDPClient udpClient = new UDPClient();
        udpClient.start(serverIP,serverPost);
    }

}
