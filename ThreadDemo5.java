package 线程;

import java.util.Scanner;

public class ThreadDemo5 {
    public static void main(String[] args) {
        Object locker1 = new Object();
        Object locker2 = new Object();
        Thread t1 = new Thread(()->{
            Scanner scanner = new Scanner(System.in);
            System.out.println("请输入一个整数:");
            synchronized (locker1) {
                int num = scanner.nextInt();
                System.out.println("t1:"+num);
            }
        });
        t1.start();
        Thread t2 = new Thread(()->{
            while (true) {
                synchronized (locker1) {
                    System.out.println("t2");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        t2.start();
    }
}
