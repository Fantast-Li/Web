package net.Socket.HTTPserver.httpSeverV3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class Request {
    private String method;
    private String url;
    private String version;
    private Map<String,String> headers = new HashMap<>() ;
    private Map<String,String> parameters = new HashMap<>();
    //cookie本来是一个字符串，但是这个字符串中我们有经常按照键值对的方式来组织
    private Map<String ,String> cookies = new HashMap<>();
    private String body = "";

    public static Request build(InputStream inputStream) {
        Request request = new Request();
        try{
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            //1.处理首行
            String firstLine = bufferedReader.readLine();
            String[] firstLineToken = firstLine.split(" ");
            request.method = firstLineToken[0];
            request.url = firstLineToken[1];
            request.version = firstLineToken[2];
            //2.解析url中的参数
            int pos = request.url.indexOf("?");
            if (pos!=-1) {
                //找到了
                String parameters = request.url.substring(pos+1);
                parseParameters(parameters,request.parameters);
            }
            //3.处理header
            String line = "";
            while ((line = bufferedReader.readLine())!=null && line.length()!=0) {
                String[] headerToken = line.split(": ");
                request.headers.put(headerToken[0],headerToken[1]);
            }
            //4.处理cookie （cookie只是一个字符串，具体里面是什么格式，都是由程序员自己约定的）
            // 就可以用按照url中的parameters 的方式来组织cookie的内容.
            String cookie = request.headers.get("Cookie");
            if (cookie!=null) {
                //cookie 在请求中存在 ，就按照之前约定的方式来解析一下
                parseParameters(cookie,request.cookies);
            }
            //5.处理body中的数据.Post 来处理  get没有body
            if (request.method.equalsIgnoreCase("post")){   //忽略大小写 POSt
                // 如果不是post请求，就不处理body
                // body的长度取决于Content-Length.
                int len = Integer.parseInt(request.headers.get("Content-Length"));
                //len 表示是body的长度，单位是字节
                // 下面new的数组长度，也是len，但是是len 这么长的字符
                // 换句话说，下面的缓冲区长度的字节数实际比body的长度要长的.
                char[] buffer = new char[len];
                bufferedReader.read(buffer,0,len);
                request.body = new String(buffer);
                //body的格式取决于Content-Type.此处为了简单，只考虑一种body的格式
                //form 表单提交数据的格式（形如 username222=aaa&password1234=123）
                //parameters 中即包含了url中的参数，也包含了body中的数据
                parseParameters(request.body,request.parameters);

            }



        } catch (IOException e) {
            e.printStackTrace();
        }
        return request;
    }

        private static void parseParameters(String input, Map<String, String> output) {

             //1.先按照& 切分
            String[] kvTokens = input.split("&");
            // 2.再按照= 切分.
            for (String kv:kvTokens) {
                String[] result = kv.split("=");
                output.put(result[0],result[1]);
            }
        }

    public String getMethod() {
        return method;
    }

    public String getUrl() {
        return url;
    }

    public String getVersion() {
        return version;
    }

    public String getBody() {
        return body;
    }
    public String getHeader(String key) {
        return headers.get(key);
    }
    public String getCookie(String key) {
        return cookies.get(key);
    }
    public String getParameter(String key) {
        return parameters.get(key);
    }

    @Override
    public String toString() {
        return "Request{" +
                "method='" + method + '\'' +
                ", url='" + url + '\'' +
                ", version='" + version + '\'' +
                ", headers=" + headers +
                ", parameters=" + parameters +
                ", cookies=" + cookies +
                ", body='" + body + '\'' +
                '}';
    }
}
