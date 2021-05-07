package 线程;

import sun.nio.ch.ThreadPool;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolDemo14 {
    public static void main(String[] args) {
        ThreadFactory factory = new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                //指定线程的一些属性，优先级，命名规则
                Thread thread = new Thread(r);
                //执行了线程池的命名规则
                thread.setName("自定义的名字:"+r.hashCode());
                //设置优先级
                thread.setPriority(10); //最大为10  默认5
                return thread;
            }
        };
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(2,
                2,1000, TimeUnit.SECONDS,new LinkedBlockingQueue<>(10),factory);


        for (int i = 0; i < 2; i++) {
            threadPoolExecutor.submit(()->{
                System.out.println("我是线程:"+Thread.currentThread().getName());
            });
        }
        threadPoolExecutor.shutdown();
    }
}
