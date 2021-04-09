package 线程;

public class ThreadDemo7 {
    static class WaitTask implements Runnable {
        private Object locker ;

        public WaitTask(Object locker) {
            this.locker = locker;
        }

        @Override
        public void run() {
            while (true) {
                synchronized (locker) {
                    System.out.println("等待前");
                    try {
                        locker.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("等待后");
                }

            }
        }
    }
    static class NotifyTask implements Runnable {
        private Object locker ;

        public NotifyTask(Object locker) {
            this.locker = locker;
        }

        @Override
        public void run() {
            synchronized (locker) {
                System.out.println("notify 开始");
                try {
                    locker.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("notify 结束");
            }
        }
    }
    public static void main(String[] args) {
    }
}
