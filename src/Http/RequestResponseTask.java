package com.peixinchen.tomcat;

import com.peixinchen.standard.Servlet;
import com.peixinchen.standard.http.HttpServletRequest;
import com.peixinchen.standard.http.HttpServletResponse;

import java.net.Socket;

public class RequestResponseTask implements Runnable {
    private final Socket socket;

    public RequestResponseTask(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
//        // 1. 解析并得到请求对象
//        HttpServletRequest request = null;
//        // 2. 实例化一个响应对象
//        HttpServletResponse response = null;
//        // 3. 根据 request.getContextPath() 找到哪个 Context 进行处理
//        Context context = null;
//        // 4. 根据 request.getServletPath() 找到 Context 中的哪个 HttpServlet 进行处理
//        Servlet servlet = null;
//        // 5. 调用 servlet.service(request, response)，交给业务处理
//        servlet.service(request, response);
//        // 6. 根据 response 对象中的数据，发送 HTTP 响应
//        sendResponse(response);
    }
}
