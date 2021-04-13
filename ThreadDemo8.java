package 线程;
//单例模式 饿汉模式
public class ThreadDemo8 {
        //Singleton 单例
    static class Singleton{
        //1.创建一个私有的静态本类对象
        private static Singleton instance = new Singleton();
        //当把这个类的构造方法设置为私有的时候，此时这个类就无法在类外部被实例化了
        //2.创建一个私有的构造方法
        private Singleton() {
        }
        //3.提供一个可以访问到该类的方法
        public static Singleton getInstance() {
            return instance ;
        }
    }

    public static void main(String[] args) {
        //具体用法
        //要想获取Singleton实例，就必须通过getInstance 获取。
        // 不能去new(构造方法是private的, new 的时候会直接编译出错)
        Singleton singleton = Singleton.getInstance();
        Singleton singleton1 = Singleton.getInstance();
        System.out.println(singleton==singleton1);
    }
}
