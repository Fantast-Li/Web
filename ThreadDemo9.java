package 线程;

public class ThreadDemo9 {
    //单例模式—— 懒汉模式
    static class Singleton {
        //当程序吧类加载的时候，并没有第一次申请内存空间
        //1.先声明一个静态的私有的Singleton对象 ，不创建，当第一次使用的时候才开始实例化对象
        private static Singleton instance = null;
        //2.构建一个私有的构造方法，是外部无法直接实例化对象
        private Singleton (){

        }
        //3.给外部提供一个获取对象的方法，当一次一使用的时候开始实例化对象
        public static Singleton getInstance() {
            if (instance==null) {
                instance = new Singleton();
            }
            return instance;
        }
    }
    public static void main(String[] args) {
        Singleton singleton = Singleton.getInstance();
        Singleton singleton1 = Singleton.getInstance();
        System.out.println(singleton==singleton1);
    }
}
