package 线程;

import java.util.Scanner;

public class ThreadDemo4 {
    public static void main(String[] args) throws InterruptedException {
        //创建一个锁
        Object locker = new Object();
        Thread t1 = new Thread(() -> {
            Scanner scanner = new Scanner(System.in);
            System.out.println("请输入一个整数:");
            synchronized (locker) {
                int num = scanner.nextInt();
                System.out.println("t1:" + num);
            }
        });
        t1.start();
        Thread t2 = new Thread(()->{
          while (true) {
              synchronized (locker){
                  System.out.println("t2");
                  try {
                      Thread.sleep(1000);
                  } catch (InterruptedException e) {
                      e.printStackTrace();
                  }
              }
          }
        });
        Thread.sleep(1000);
        t2.start();


    }
}