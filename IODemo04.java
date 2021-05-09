package IO;

import java.io.*;

public class IODemo04 {
    public static void main(String[] args) throws IOException {

        //输入文件路径
        String srcfilePath = "D:\\iotext\\1.0.png";
        //输出文件路径
        String destFilePath = "D:\\iotext\\3.0.png";
        String destFilePath2 = "D:\\iotext\\5.0.png";
        //输入流
//        copyImage(srcfilePath,destFilePath);
        long stime = System.nanoTime();
        copyImage1(srcfilePath,destFilePath); //  最终版
        long etime = System.nanoTime();
        System.out.println("不带缓冲区的执行时间"+(etime-stime));
        long stime1 = System.nanoTime();
        copyImage3(srcfilePath,destFilePath2); // 带缓冲区的
        long etime1 = System.nanoTime();
        System.out.println("带缓冲区的执行时间"+(etime1-stime1));

    }

    private static void copyImage3(String srcfilePath, String destFilePath2) {
        try(
                BufferedInputStream bufferedInputStream = new BufferedInputStream(
                        new FileInputStream(srcfilePath));
                BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(
                        new FileOutputStream(destFilePath2));
                ){
            byte[] bytes = new byte[1024];
            int count = 0 ;
            while ((count = bufferedInputStream.read(bytes))!=-1) {
                bufferedOutputStream.write(bytes,0,count );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void copyImage1(String srcfilePath, String destFilePath) {
        // try-with-resource
        try(
            FileInputStream fileInputStream = new FileInputStream(srcfilePath);
            FileOutputStream fileOutputStream = new FileOutputStream(destFilePath)){
                //写业务代码
            byte[] bytes = new byte[1024];
            int count = 0 ;
            while ((count = fileInputStream.read(bytes))!=-1) {  //read+  读取内容
                fileOutputStream.write(bytes,0,count);
            }
            } catch (Exception e ) {
            e.printStackTrace();
        }
    }

    private static void copyImage(String srcfilePath, String destFilePath) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(srcfilePath);
        //输出流
        FileOutputStream fileOutputStream = new FileOutputStream(destFilePath);
        byte[] bytes = new byte[1024];
        int  count = 0;
        while ((count = fileInputStream.read(bytes))!=-1) {
            //还有内容要一直读
            fileOutputStream.write(bytes,0,count);
        }
        //关掉流
        fileInputStream.close();
        fileOutputStream.close();
    }
}
