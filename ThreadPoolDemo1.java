package 线程;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

//实现一个  线程池
public class ThreadPoolDemo1 {
    //创建一个类来描述线程池中的线程长什么样
    static class Worker extends Thread {
        private BlockingDeque<Runnable> queue = null;

        public Worker(BlockingDeque<Runnable> queue) {
            this.queue = queue;
        }
        @Override
        public void run() {
            try {
                while (!Thread.currentThread().isInterrupted()) {
                    //queue 是个阻塞对列, 当任务对列为空的时候，worker 线程们就会在take 方法
                    //阻塞，直到有新的任务加入到queue中 worker才会被唤醒。
                    Runnable runnable = queue.take();
                    runnable.run();
                }
            } catch (InterruptedException e) {
//                e.printStackTrace();
                //catch到异常就说明线程被终止了
            }
        }
    }

    //创建一个类 来描述线程池
    static class MyThreadPool {
        //线程池内置的任务对列 ，每个工作线程都要从这个任务对列中获取到任务并执行
        private BlockingDeque<Runnable> queue = new LinkedBlockingDeque<>();
        //包含了当前线程中有那些工作线程
        private List<Worker> workers = new ArrayList<>();
        //限制线程池中 ，最多有10个线程
        private int maxWorkerCount = 10 ;

        // 接口有二个
        //给线程池中创建新的线程

        public void execute (Runnable command) {
            if (workers.size() < maxWorkerCount) {
                //此时workers 的线程数目不足, 创建新的worker 添加到线程池中
                Worker worker = new Worker(queue);
                worker.start();
                workers.add(worker);
                System.out.println("创建了新的线程");
            }
            try {
                queue.put(command);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //关闭线程池中
        public void shutdown() {
            //这个方法要关闭线程池，把线程池中所有的 worker(线程)都干掉
            for (Worker worker: workers) {
                worker.interrupt();
            }
            for (Worker worker : workers) {
                try {
                    worker.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            //当shutdown 执行完毕 ， 就能保证所有线程都被收回
        }

        public static void main(String[] args) throws InterruptedException {
            MyThreadPool myThreadPool = new MyThreadPool();
            for (int i = 0; i < 100; i++) {
                myThreadPool.execute(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("这是要执行的任务");
                    }
                });
            }
            Thread.sleep(5000);
            myThreadPool.shutdown();
        }
    }
}