package IO;

import java.io.File;
import java.io.IOException;

public class IoDemo1 {
    public static void main(String[] args) throws IOException {
//        createFileDemo();    创建文件
//          createFileDemo1();   //创建文件相对路径
//        createFileDemo3();
        createFileDemo2();
    }

    private static void createFileDemo2() throws IOException {
        String filePath = "E:\\project\\OJ\\io2.txt";
        File file = new File(filePath);
        if (!file.exists()) {
            Boolean f = file.createNewFile();
            System.out.println("文件创建成功"+filePath + "结果为："+f);
        } else {
            System.out.println(filePath+",文件已经存在");
        }
    }

    private static void createFileDemo3() {
        String filePath = "E:\\project\\OJ";
        File file = new File(filePath);
        if (file.isFile()) {
            System.out.println("是文件");
        } else if (file.isDirectory()) {
            System.out.println("是一个文件目录");
        } else {
            System.out.println("不是文件");
        }
//        if (file.exists()){
//            System.out.println("文件已经存在");
//        } else {
//            System.out.println("文件不存在");
//        }

    }

    private static void createFileDemo1() throws IOException {
//        String fileName = "io.text";
//        //不传路径就是当前文件的目录
        //上一级目录的B目录里创建文件
        String fileName = "../OJ/io2.txt";
        File file = new File(fileName);
        file.createNewFile();
    }

    private static void createFileDemo() throws IOException {
        //文件路径
        String filePath = "D:\\iotext\\";
        //文件名
        String fileName = "text.txt";
        File file = new File(filePath+fileName);
        Boolean b = file.createNewFile();
        if (b) {
            System.out.println("文件已经被创建");
        } else {
            System.out.println("文件创建失败");
        }
    }
}