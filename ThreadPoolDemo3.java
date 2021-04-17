package 线程;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPoolDemo3 {
    public static void main(String[] args) {
        ExecutorService threadPool = Executors.newCachedThreadPool();
        for (int i = 0; i < 16 ; i++) {
            threadPool.submit(()->{
                System.out.println("线程的名称:"+Thread.currentThread().getName());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
    }
}
