package 线程;

import java.util.Random;
import java.util.concurrent.*;

public class ThreadPoolDemo8 {
    public static void main(String[] args) {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                1,1,1000, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(10)
        );
        Callable callable = new Callable() {
            @Override
            public Object call() throws Exception {
                int num = new Random().nextInt(10);// 0-9
                System.out.println("执行了任务" + num);
                return num;
            }
        };
        Future future = threadPoolExecutor.submit(callable);
        System.out.println("返回结果:" +future);
        Object num2 = threadPoolExecutor.submit(callable);
        Object num3 = threadPoolExecutor.submit(callable);
    }
}
