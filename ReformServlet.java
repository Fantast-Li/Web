package peixinchen.standard.src.peixinchen.webapps.dictionary;

import peixinchen.standard.src.peixinchen.standard.ServletException;
import peixinchen.standard.src.peixinchen.standard.http.HttpServlet;

public class ReformServlet extends HttpServlet {
    public ReformServlet() {
        System.out.println("我是 ReformServlet 的构造方法");
    }

    @Override
    public void init() throws ServletException {
        System.out.println("我是 ReformServlet 的 init 方法");
    }
}
