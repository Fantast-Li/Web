package 线程;

public class ThreadDemo1 {
    public static void main(String[] args) {
        //Thread.State 是一个枚举类型 ，  枚举表示现实中可以穷尽列举出来的    十二月  性别
        for(Thread.State state: Thread.State.values() ) {
            System.out.println(state);
        }
    }
}
