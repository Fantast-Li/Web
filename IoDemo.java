package IO;

import java.io.File;

public class IoDemo {
    public static void main(String[] args) {
//        mkTest();  创建目录
        // 文件的地址
        String filePath = "D:\\iotext\\1";
        File file = new File(filePath);
        // 获取当前的绝对路径
        System.out.println(file.getAbsolutePath());
        //获取上级目录  必须是绝对路径
        System.out.println(file.getParent());

    }

    private static void mkTest() {
        //文件的地址
        String filePath = "D:\\iotext\\1\\2\\3";
        //文件对象
        File file = new File(filePath);
        //创建多目录
        Boolean result = file.mkdirs();
//        //创建目录（单文件目录）
//        Boolean result = file.mkdir();
        if (result) {
            System.out.println("文件创建成功");
        } else {
            System.out.println("文件创建失败");
        }
    }
}
