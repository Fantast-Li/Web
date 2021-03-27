package v2;

import java.io.*;
import java.net.Socket;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class RequestResponseTask implements Runnable {
    private static final String DOC_BASE = "E:\\project\\HTTP研发阶段代码\\docBase";
    private final Socket socket;

    public RequestResponseTask(Socket socket) {
        this.socket = socket;
    }

    //Map<suffix , contentType>
    private static final Map<String, String> mimeTypeMap = new HashMap<>();

    static {
        mimeTypeMap.put("text", "text/plain");
        mimeTypeMap.put("html", "text/html");
        mimeTypeMap.put("js", "application/javascript");
        mimeTypeMap.put("jpg", "image/jpeg");
    }

    @Override
    public void run() {
        try {

            System.out.println("一条TCP已经建立");
            //解析HTTP 请求能力 --->解析出来路径
            InputStream inputStream = socket.getInputStream();
            Scanner scanner = new Scanner(inputStream, "UTF-8");
            scanner.next();//读取出来的是方法，暂时不用，所以没保存
            String path = scanner.next(); // 读出路径
            System.out.println(path);
            String filePath = DOC_BASE + path; //用户请求的静态资源对应的路径
            // 0.暂时先不考虑目录，文件是一个目录的情况
            //1. 判断该文件是否存在 ---file
            File resource = new File(filePath);
            System.out.println(resource);
            if (resource.exists()) {
                //直接读取内容，并写入 response body 中n
                OutputStream outputStream = socket.getOutputStream();
                Writer writer = new OutputStreamWriter(outputStream, "UTF-8");
                PrintWriter printWriter = new PrintWriter(writer);
                String contentType = "text/plain";
                //找到路径对应的后缀（字符串）处理
                if (path.contains(".")) {
                    int i = path.lastIndexOf(".");
                    String suffix = path.substring(i + 1);
                    contentType = mimeTypeMap.getOrDefault(suffix, contentType);
                }
                //如果 contentType 是text 开头的 是文本
                // 我们都吧字符集设置为 utf-8.
                if (contentType.startsWith("text")) {
                    contentType = contentType + "; charset=utf-8";
                }
                printWriter.printf("HTTP/1.0 200 OK\r\n");
                printWriter.printf("Content-Type: %s; \n\r", contentType);
                printWriter.printf("\n\r");
                printWriter.flush();
                // 写入 response body
                try (InputStream resourceInputStream = new FileInputStream(resource)) {
                    byte[] buffer = new byte[1024];
                    while (true) {
                        int len = resourceInputStream.read(buffer);
                        if (len == -1) {
                            break;
                        }
                        outputStream.write(buffer, 0, len);
                        System.out.println(Arrays.toString(buffer));
                    }
                    outputStream.flush();
                }


            } else {
                //response 404
                try {
                    OutputStream outputStream = socket.getOutputStream();
                    Writer writer = new OutputStreamWriter(outputStream, "UTF-8");
                    PrintWriter printWriter = new PrintWriter(writer);
                    printWriter.printf("HTTP/1.0 404 Not Found\r\n");
                    printWriter.printf("Content-Type: text/html; charset=utf-8\r\n");
                    printWriter.printf("\r\n");
                    printWriter.printf("<h1>对应资源没有找到</h1>");
                    printWriter.flush();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
                System.out.println("一条TCP已经释放");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

//
//            OutputStream outputStream = socket.getOutputStream();
//            Writer writer = new OutputStreamWriter(outputStream, "UTF_8");
//            PrintWriter printWriter = new PrintWriter(writer);
//            //响应格式 ：
//            // .响应行
//            printWriter.printf("HTTP/1.0 200 ok\r\n");
//            //响应头
//            printWriter.printf("Content-Type: text/html; charset=utf-8\r\n");
//            //写入空行代表响应头结束
//            printWriter.printf("\r\n");
//            //写响应体，html 内容
//            printWriter.printf("<h1>正常工作了</h1>");
//            //刷新  将内容发送到TCP的缓冲区
//            printWriter.flush();
//        } catch (IOException e) {
//            //因为单次的请求响应周期错误，不应该影响其他的请求响应
//            //所以，我们只做打印，不终止进程
//            e.printStackTrace();
//        } finally {
//            try {
//                socket.close();
//                System.out.println("一条TCP已经释放");
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
    }
}
