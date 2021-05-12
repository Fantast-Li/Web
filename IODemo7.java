package IO;

import java.io.*;

public class IODemo7 {
    static class Person implements Serializable {
        //解决版本不一致的终极解决方案
        public final static long serialVersionUID = 1L;

        private int id ;
        private String name;
        private String sex;
        public static int count = 1 ;

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static void main(String[] args) {
        Person person = new Person();
        person.setId(7);
        person.setName("小咪");
        //序列化存放的地址
        String filePath = "D:\\iotext\\1\\2\\3\\person.txt";
        try(ObjectOutputStream objectOutputStream = new ObjectOutputStream(
                new FileOutputStream(filePath)))
        {
            //序列化
            objectOutputStream.writeObject(person);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
