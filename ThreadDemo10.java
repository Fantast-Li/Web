package 线程;
//懒汉模式 改进版
public class ThreadDemo10 {
    static class Singleton {
        private volatile static Singleton instance = null ;
        private Singleton () {

        }
        public Singleton getInstance() {
            if (instance==null) {
                synchronized (Singleton.class) {
                    if (instance==null) {
                        instance = new Singleton();
                    }
                }
            }
            return instance;
        }
    }
}
