package 线程;

import java.util.concurrent.PriorityBlockingQueue;

//定时器
public class ThreadDemo12 {
    //需要一个类来描述往定时器存放的任务
    static class Task implements Comparable<Task> {
        //command 表示任务具体干嘛
        private Runnable command;
        //time 表示啥时候可以执行这个任务
        private long time ;
        // 参数time 表示多少ms 后开始执行任务
        //成员的time是个绝对的时间
        public Task(Runnable command, long time) {
            this.command = command;
            this.time = time+System.currentTimeMillis();
        }
        public void run() {
            command.run();
        }

        @Override
        public int compareTo(Task o) {
            //时间越小优先级越高
            return (int) (this.time - o.time);
        }
    }

    static class Timer {
        //锁 用于辅助解决“忙等”问题
        Object mailBox = new Object();
        //优先级对列(带阻塞的) Task  必须有优先级 ，即具有比较能力
        //优先对列中包含了都有那些任务需要执行 .
        private PriorityBlockingQueue<Task> queue = new PriorityBlockingQueue<>();
        //还需要有一个线程来扫描队首元素
        class Worker extends Thread {
            @Override
            public void run() {
                while (true) {
                    try {
                        //进行take 操作 获取队首元素  ，也相当于把元素从对列中删除了
                        // 如果task 时间还长的时候，需要把task 在塞回对列.
                        Task task = queue.take();
                        long curTime = System.currentTimeMillis();
                        if (task.time > curTime) {
                            //执行任务的时机还没成熟
                            queue.put(task);
                            //发现时间还没到 ，就借助wait让线程能耐心等待
                            synchronized (mailBox) {
                                mailBox.wait(task.time-curTime);
                            }
                        } else {
                            //执行任务
                            task.run();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        public Timer() {
            Worker worker = new Worker();
            worker.start();
        }
        //还要提供一个用于注册任务的 核心接口
        //command 表示执行的操作是啥 after 表示多少时间后 执行（相对时间）
        public void schedule (Runnable command,long after) {
            Task task = new Task(command,after);
            queue.put(task);
            //新的任务可能比原来的任务更早去执行每次注册新任务都唤醒一下扫描线程
            synchronized (mailBox){
                mailBox.notify();
            }
        }
     }

    public static void main(String[] args) {
        Timer timer = new Timer();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println("闹钟响了");
                timer.schedule(this,3000);
            }
        };
        System.out.println("开始计时");
        timer.schedule(runnable,3000);
    }
}
