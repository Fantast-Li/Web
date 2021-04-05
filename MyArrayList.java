package ArraryList;

import java.util.Arrays;

public class MyArrayList {
private Integer[] arrays ;
private int size ;
MyArrayList () {
    arrays = new Integer[10] ;
    size = 0 ;
}

    @Override
    public String toString() {
        return "MyArrayList{" +
                "arrays=" + Arrays.toString(arrays) +
                ", size=" + size +
                '}';
    }
// array的 常用方法

    /**
     * 插入元素是fou成功
     * @param e
     * @return
     */
    public  boolean add (Integer e) {
        //进来先判断是否需要扩容
        ensoreCapactity(size);
        arrays[size] = e ;
        size++;
        return true;
    }

    /**
     * 按下标插入
     * @param index
     * @param e
     */
    public void add (int index , Integer e){
        // 先看index 是否正常 不正常用异常提醒
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("index ="+index+",size="+size);
        }
        //看添加一位是否需要扩容
        ensoreCapactity(size+1);
        //向后移动 使用复制数组即可
        System.arraycopy(arrays,index,arrays,index+1,size-index);  //size - index  不能是array.length
        arrays[index] = e ;
        size++;
    }

    /**
     * 按下标删除
     * @param index
     * @return
     */
    public Integer remove (int index) {
        // 判断下标是否合法
        checkIndex(index);
        Integer e = arrays[index];
        System.arraycopy(arrays,index+1,arrays,index,size-index-1);
        //将末尾元素置空
        arrays[--size] = null ;
        size--;
        return e ;
    }

    /**
     * 按元素删除
     * @param e
     * @return
     */
    public boolean remove (Integer e) {
        // 遍历arraylist 找到 e 的下标 比较的时候用 equals
        for (int i = 0; i < size ; i++) {
            if (arrays[i].equals(e)){
                //如果相等的话 i 就是e 的下标 可以直接删除
                System.arraycopy(arrays,i+1,arrays,i,size-i-1);
                // 将末尾元素置空
                arrays[--size] = null;
                size--;
                return true;
            }
        }
        return false;
    }

    /**
     * 按下标获取元素
     * @param index
     * @return
     */
    public Integer get (int index) {
        checkIndex(index);
        return arrays[index];
    }

    /**
     * 修改下标元素
     * @param index
     * @param e
     * @return
     */
    public Integer set (int index , Integer e) {
        checkIndex(index);
        //保存原下标的值
        Integer result = arrays[index];
        arrays[index] = e;
        return result;
    }

    /**
     * 元素是否存在List里
     * @param e
     * @return
     */
    public boolean contains (Integer e ) {

        return indexOf(e)!=-1;
    }
    public int size() {
        return  size ;
    }

    /**
     * 元素是否含有
     * @return
     */
    public boolean isEmpty () {
        return size==0;
    }

    /**
     * 从前面找 返回第一个碰见e元素下标
     * @param e
     * @return
     */
    public int indexOf(Integer e) {
        for (int i = 0; i < size; i++) {
            if (arrays[i].equals(e)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 从最后开始找起 返回第一个碰见e元素下标
     * @param e
     * @return
     */
    public int lastIndexOf (Integer e ) {
        for (int i = size; i >0 ; i--) {
            if (arrays[i].equals(e)){
                return i ;
            }
        }
        return -1;
    }
    // 复用方法
    // 检查下标是否合法
    private void checkIndex (int index) {
        if (index < 0 || index >= size) { // 是size 不能是arrays.length
            throw new IndexOutOfBoundsException("index="+index + ",size = "+size);
        }
    }
    private void ensoreCapactity(int size) {
        if (size <= arrays.length) {
            return;
        }
        arrays = Arrays.copyOf(arrays,arrays.length+arrays.length/2);
    }

    public static void main(String[] args){
        MyArrayList arrayList = new MyArrayList();
        arrayList.add(1);
        arrayList.add(2);
        arrayList.add(3);
        arrayList.add(0,0);
        arrayList.add(4,4);
        arrayList.remove((Integer) 0);

        System.out.println(arrayList);

    }

}
