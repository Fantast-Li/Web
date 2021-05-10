package IO;

import java.io.*;

public class IODemo8 {
//    static class Person implements Serializable {
//        private int id;
//        private String sex;
//
//        public int getId() {
//            return id;
//        }
//
//        public void setId(int id) {
//            this.id = id;
//        }
//
//        public String getSex() {
//            return sex;
//        }
//
//        public void setSex(String sex) {
//            this.sex = sex;
//        }
//    }

    public static void main(String[] args) {
        //1文件地址的设定
        //声明一个序列化存储位置
        String filePath = "D:\\iotext\\1\\2\\3\\person.txt";
        IODemo7.Person person = null;
        //反序列化
        try(ObjectInputStream objectInputStream = new ObjectInputStream(
                new FileInputStream(filePath)
        )) {
            person = (IODemo7.Person) objectInputStream.readObject();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println(person.getId());
        System.out.println(person.getSex());

    }
}