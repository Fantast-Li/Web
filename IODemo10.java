package IO;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;

//字节写入编码问题
public class IODemo10 {
    public static void main(String[] args) throws FileNotFoundException {
        String cnString = "我爱咪咪";
        String filePath ="D:\\iotext\\1\\cn.txt";
        try(FileOutputStream fileOutputStream =
                    new FileOutputStream(filePath)) {
//           bbia
            fileOutputStream.write(cnString.getBytes("utf-8"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Scanner scanner = new Scanner(new File(filePath));
        System.out.println(scanner.nextLine());
    }
}
