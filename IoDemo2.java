package IO;

import java.io.File;

public class IoDemo2 {
    public static void main(String[] args) {
        //遍历一个文件底下的所有文件名
        String filePath = "D:\\iotext\\1";
        File file = new File(filePath);
        listFileDemo(file);
    }

    private static void listFileDemo(File file) {
        //文件类型 只可能有二个 : 文件，目录
        for (File f : file.listFiles()) {
            if (f.isFile()) {
                System.out.println(f.getName());
            } else {
                //这个文件是目录，遍历底下的文件
                listFileDemo(f);
            }
        }
    }
}
