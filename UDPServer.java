package net.Socket;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

/**
 * UDP 的服务器,DatagramSocket、DatagramPacket
 */
public class UDPServer {
    //1.先初始化DatagramSocket



    private DatagramSocket datagramSocket = null;

    /**
     * 初始化连接对象DatagramSocket
     * @param port  端口号
     */
    public UDPServer (int port) throws SocketException {
        datagramSocket = new DatagramSocket(port);
    }
    //2.进入主循环进行响应的操作
    public void start() throws IOException {
        System.out.println("服务器正常启动了~");
        while (true) {
            //   a).阻塞等待客户端的连接
            DatagramPacket requestDatagramPacket = new DatagramPacket(
                    new byte[4096],4096);
            //进行阻塞  需要一个容器
            //接收
            datagramSocket.receive(requestDatagramPacket);

            //   b).接收到客户端的请求命令(具体指令)
            String requestConnect = new String(requestDatagramPacket.getData());
            //   c）进行业务计算和处理（客户端信息的加工和处理）
            String responseConnect = process(requestConnect);
            //   d)讲服务器响应（信息）返回给客户端。
            DatagramPacket responseDatagramPacket= new DatagramPacket(
                    requestConnect.getBytes(),requestConnect.getBytes().length,
                    requestDatagramPacket.getAddress() //目标地址的（客户端）IP
                    ,requestDatagramPacket.getPort()   //目标地址的（客户端）端口号
            );
            //发送
            datagramSocket.send(responseDatagramPacket);
            System.out.printf("客户端IP:%s,客户端端口:%d,客户端的内容:%s,服务器响应的内容:%s \n",
                    requestDatagramPacket.getAddress(),requestDatagramPacket.getPort()
            ,requestConnect,responseConnect);
        }

    }
    //处理客户端的请求逻辑
    private String process(String requestConnect) {
        return requestConnect;
    }

    public static void main(String[] args) throws IOException {
        UDPServer udpServer = new UDPServer(9090);
        udpServer.start();
    }

}
