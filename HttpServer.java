package peixinchen.standard.src.peixinchen.tomcat;

import peixinchen.standard.src.peixinchen.standard.Servlet;
import peixinchen.standard.src.peixinchen.standard.ServletException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HttpServer {
    public static void main(String[] args) throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, ServletException {
        // 1. 找到所有的 Servlet 对象，进行初始化
        initServer();
        for (Context context : contextList) {
            for (Servlet servlet : context.servletList) {
                System.out.println(servlet);
            }
        }
//
//        ExecutorService threadPool = Executors.newFixedThreadPool(10);
//        ServerSocket serverSocket = new ServerSocket(8080);
//
//        // 2. 每次循环，处理一个请求
//        while (true) {
//            Socket socket = serverSocket.accept();
//            Runnable task = new RequestResponseTask(socket);
//            threadPool.execute(task);
//        }
//
//        // 3. 找到所有的 Servlet 对象，进行销毁
//        for (Servlet servlet : servlets) {
//            servlet.destroy();
//        }
    }

    private static void initServer() throws IOException, ClassNotFoundException, IllegalAccessException, InstantiationException, ServletException {
        scanContexts();
        parseContextConf();
        loadServletClasses();
        instantiateServletObjects();
        initializeServletObjects();
    }

    private static void initializeServletObjects() throws ServletException {
        System.out.println("第五步：执行每个 servlet 对象的初始化");
        for (Context context : contextList) {
            context.initServletObjects();
        }
    }

    private static void instantiateServletObjects() throws InstantiationException, IllegalAccessException {
        System.out.println("第四步：实例化每个 servlet 对象");
        for (Context context : contextList) {
            context.instantiateServletObjects();
        }
    }

    private static void loadServletClasses() throws ClassNotFoundException {
        System.out.println("第三步：加载每个 Servlet 类");
        for (Context context : contextList) {
            context.loadServletClasses();
        }
    }

    private static void parseContextConf() throws IOException {
        System.out.println("第二步：解析每个 Context 下的配置文件");
        for (Context context : contextList) {
            context.readConfigFile();
        }
    }

    public static final String WEBAPPS_BASE = "D:\\课程\\2021-3-4-Java31-40班-HTTP项目\\正式项目代码\\webapps";
    private static final List<Context> contextList = new ArrayList<>();
    private static final ConfigReader configReader = new ConfigReader();
    private static void scanContexts() {
        System.out.println("第一步：扫描出所有个 contexts");
        File webappsRoot = new File(WEBAPPS_BASE);
        File[] files = webappsRoot.listFiles();
        if (files == null) {
            throw new RuntimeException();
        }

        for (File file : files) {
            if (!file.isDirectory()) {
                // 不是目录，就不是 web 应用
                continue;
            }

            String contextName = file.getName();
            System.out.println(contextName);
            Context context = new Context(configReader, contextName);

            contextList.add(context);
        }
    }
}
