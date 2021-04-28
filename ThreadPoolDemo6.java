package 线程;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolDemo6 {
    public static void main(String[] args) {
        //线程池的创建
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(10,
                15,
                100,
                TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(10));
        for (int i = 0; i < 21 ; i++) {
            threadPoolExecutor.submit(()->{
                System.out.println("线程的名称:" + Thread.currentThread().getName());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
    }
    
}
