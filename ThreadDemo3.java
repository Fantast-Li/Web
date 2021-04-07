package 线程;

public class ThreadDemo3 {
    //计数器
    public static class Counter {
        public int count = 0 ;
        //增加方法
        public void increase() {
            count++;
        }
    }
    public static void main(String[] args) throws InterruptedException {
        Counter counter = new Counter();
        Thread thread1 = new Thread(()->{
            for (int i = 0; i < 10000 ; i++) {
                counter.increase();
            }
        });
        thread1.start();
        Thread thread2 = new Thread(()->{
            for (int i = 0; i < 10000 ; i++) {
                counter.increase();
            }
        });
        thread2.start();
        thread1.join();
        thread2.join();
        System.out.println(counter.count);
    }
}
