package 线程;

import java.util.Scanner;

public class ThreadDemo6 {
    public static class Counter {
        volatile public int count = 0;
    }

    public static void main(String[] args) {
        Counter counter = new Counter();
        Thread t1 = new Thread(()->{
            while (counter.count == 0 ) {

            }
            System.out.println("循环结束");
        });
        t1.start();
        Thread t2 = new Thread(()->{
            Scanner scanner = new Scanner(System.in);
            System.out.println("请输入一个整数:");
            counter.count = scanner.nextInt();
        });
        t2.start();

    }
}