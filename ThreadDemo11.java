package 线程;
//阻塞对列
// 特点：1.如果当对列元素满了的时候再进行插入操作，这个时候对列会进行阻塞，直到有位置可以进行插入操作
// 2.如果当对列元素为空的时候，再进行出对列操作的时候，这个时候对列会进行阻塞，直到对列有元素为止
//对象等待集（wait notify）
public class ThreadDemo11 {
    static class BlackingQueue {
        private int[] array = new int[1000];
        private volatile int size = 0;
        private volatile int head = 0;
        private volatile int tail = 0 ;
//        进队列
        private void offer(int val) throws InterruptedException {

            synchronized (this) {
                while (size==array.length) {
                    this.wait();
                }
                array[tail] = val ;
                tail++;
                if (tail==array.length) {  // 环形对列
                    tail=0;
                }
                size++;
                //此处的通知是为了唤醒消费者来进行获取数据
                notify();
            }
        }
        //出对列
        private int poll() throws InterruptedException {
            int ret = 0 ;

            synchronized (this) {
                while (size==0) {
                    wait();
                }
                ret = array[head];
                head++;
                if (head==array.length) {
                    head=0;
                }
                size--;
                //此处是为了唤醒生产者线程来进行插入数据
                notify();

            }
            return ret;
        }

    }

    public static void main(String[] args) throws InterruptedException {
        BlackingQueue blackQueue = new BlackingQueue();
        //消费者线程
        Thread customer = new Thread(()->{
            while (true) {
                try {
                    int val = blackQueue.poll();
                    System.out.println("消费者 ："+val);
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        customer.start();
        //生产者线程
        Thread producer = new Thread(()->{
            for (int i = 0; i < 10000 ; i++) {
                try {
                    blackQueue.offer(i);
                    System.out.println("生产者:"+i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        producer.start();
        customer.join();
        producer.join();
    }
}
