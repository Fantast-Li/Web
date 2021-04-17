package 线程;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPoolDemo2 {
    public static void main(String[] args) {
        //创建线程池1 固定大小的线程池
        //线程数量为10 的线程池
        ExecutorService threadPool = Executors.newFixedThreadPool(10);
        for (int i = 0 ; i < 11 ;i++) {
            threadPool.submit(new Runnable() {
                @Override
                public void run() {
                    System.out.println("线程的名称是:"+Thread.currentThread().getName());
                }
            });
        }
    }
}
