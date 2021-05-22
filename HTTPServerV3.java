package net.Socket.HTTPserver.httpSeverV3;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HTTPServerV3 {
    static class User{
        @Override
        public String toString() {
            return "User{" +
                    "name='" + name + '\'' +
                    ", password='" + password + '\'' +
                    ", age=" + age +
                    ", sex='" + sex + '\'' +
                    '}';
        }

        public String name ;
        public String password;
        public int age ;
        public String sex;
    }
    private ServerSocket serverSocket = null;
    //使用这个hash表来管理所有的会话
    // 每个键值对对应每个用户的会话、
    private Map<String,User> sessions = new HashMap<>();
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
            if (request.getMethod().equalsIgnoreCase("GET")) {
                doGet(request, response);
                System.out.println("response: "+response);
            } else if (request.getMethod().equalsIgnoreCase("POST")) {
                System.out.println("这是Post 请求");
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
        if (request.getUrl().startsWith("/login")) {
            //处理登录情况
            //此处需要检查用户名密码
            // 请求提交的用户用和密码是HTTP 请求中的body中
            // 而body 中的数据，是被Request  解析了一个放在parameters 这个HashMap中
            // 通过parameter hash 表就能得到用户发送的用户名和密码是啥 .
            String userName = request.getParameter("username");
            String password = request.getParameter("password");
            System.out.println("userName: "+userName);
            System.out.println("password: "+password);
            String result = "登录失败11";
            if ("aaa".equals(userName)&&"aaa".equals(password)){
                result = "登录成功!欢迎你"+userName;
                //hash 表中的key希望是一个唯一的字符串
                // 二个用户的会话的sessionid 不能重复.
                String sessionId = generateSessionId();
                response.setHeader("Set-Cookie","sessionId="+sessionId  );
                //如果登录成功，就往sessions中插入一个键值对，表示这个用户的session
                User user = new User();
                user.name = userName;
                user.password = "aaa";
                user.age=20;
                user.sex = "男";
                sessions.put(sessionId,user);
                System.out.println("新建sessionId: " + sessionId);
            }
            //此处在处理登录的逻辑中，直接返回一个303 的响应，重定向到check页面
            response.setStatus(303);
            response.setMessage("See Other");
            response.setHeader("Location","/check");
        }
    }

    private void doGet (Request request, Response response) throws IOException {
        if (request.getUrl().startsWith("/test")) {
            System.out.println(4);
            response.setStatus(200);
            response.setMessage("OK");
            response.setHeader("Content-Type: ","text/html; charset=utf-8");
            response.writeBody("<html>");
            response.writeBody("200 OK");
            response.writeBody("</html>");
        } else if (request.getUrl().startsWith("/index.html")) {
            // 如果path 是指向index.html这样的资源
            // 就从磁盘上访问这个文件，把文件内容读取出来，并且写回到客户端这里
            response.setStatus(200);
            response.setMessage("OK");
            response.setHeader("Content-Type: ","text/html;charset=utf-8");
            //把文件读取来 依次写入到Response中的body中
            //要想读文件，需要先获取文件对应的inputStream 对象
            InputStream inputStream =   HTTPServerV3.class.getClassLoader().getResourceAsStream("index.html");
            //将inputStream 也包装成BufferReader 然后按行进行读取
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line ="";
            while ((line=bufferedReader.readLine())!=null ){
                response.writeBody(line+"\n");
            }
            bufferedReader.close();
        } else if (request.getUrl().startsWith("/check")) {
            //通过这个页面来检查用户的登录状态.
            //此处直接检查 请求 中的Cookie中所包含的用户信息。
            String sessionId = request.getCookie("sessionId");
            String result = "";
            if (sessionId == null) {
                result = "登录失败222";
            } else {
                User user = sessions.get(sessionId);

                if (user==null){
                    result = "登录失败！";
                } else {
                    result = String.format("登录成功! name: %s,age: %d,sex :%s"
                    ,user.name,user.age,user.sex);
                }
            }
            response.setStatus(200);
            response.setMessage("OK");
            response.setHeader("Content-Type: ", "text/html;charset=utf-8");
            response.writeBody("<html>");
            response.writeBody(result);
            response.writeBody("</html>");
        }

    }
    //通过这个方法来生成唯一一个的sessionId ，保证二次调用生成的id是不重复的
    //核心思路 ： 时间戳+随机数
    private static String generateSessionId(){
        long time = System.currentTimeMillis();
        Random random = new Random();
        //生成0-100000之间的随机整数
        int r = random.nextInt(100000);
        return ""+time+r;
    }
    public static void main(String[] args) throws IOException {
        HTTPServerV3 httpServerV3 = new HTTPServerV3(9091);
        httpServerV3.start();
    }
}