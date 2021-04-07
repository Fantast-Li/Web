package 线程;

public class ThreadDemo2 {
    public static void main(String[] args) {
        Thread t = new Thread(()->{
            int i = 0 ;
            for (int j = 0; j < 5 ; j++) {
              //循环内部啥也不干
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        System.out.println( t.getState());
        t.start();
        // isAlive  PCB是否存活 （只要Thread的状态不是 NEW 和 TERMINATED 就是isAlive()）
        // 什么是新的线程  将PCB对象插入列表   销毁线程 将PCB线程从链表中删除
        while (t.isAlive()) {
            System.out.println(t.getState());
        }
        System.out.println(t.getState());
    }
}
