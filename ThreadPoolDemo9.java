package 线程;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolDemo9 {
    public static void main(String[] args) throws InterruptedException {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(5,5,100,
                TimeUnit.SECONDS, new LinkedBlockingQueue<>(5));
        for (int i = 0; i < 10; i++) {
            final int index = i;
            threadPoolExecutor.submit(()->{
                System.out.println("任务打印:" + index);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        //1.shundown
        Thread.sleep(700);
        //threadPoolExecutor.shutdown
        threadPoolExecutor.shutdownNow();
    }
}
