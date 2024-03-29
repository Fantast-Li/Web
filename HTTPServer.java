package v2;



import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HTTPServer {


    public static void main(String[] args) throws IOException {
        ExecutorService threadPool = Executors.newFixedThreadPool(10);
        ServerSocket serversocket = new ServerSocket(8080);
        while (true) {
        Socket socket = serversocket.accept();
        Runnable task = new RequestResponseTask(socket);
        threadPool.execute(task);
        }
    }
}
