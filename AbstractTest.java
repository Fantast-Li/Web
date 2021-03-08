package com.li;

abstract class Shape { //抽象类的定义 用abstract 所修饰的类  该类可以被继承

    public abstract void draw();//abstract 修饰的方法 抽象方法  通如果一个人普通类继成抽象类必须重写抽象方法
    public void eat(){
        System.out.println("这是一个吃货");
    }
        }
class Cycle extends Shape{
    @Override
    public void draw() {
        System.out.println("○");
    }
}
class Rect extends Shape{
    @Override
    public void draw() {
        System.out.println("方块");
    }
}


public class AbstractTest {
    public static void drawMap(Shape shape){
        shape.draw();
    }
    public static void main (String []args){
        // Shape shape = new shape(); 错误 抽象类不能直接实例化
       Cycle  cycle = new Cycle();
       Shape shape = new Rect();
       drawMap(shape);
       cycle.eat();
       drawMap(cycle);
    }

}
