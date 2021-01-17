package heap;

import java.util.Arrays;

public class HeapOperation {
    public static void swap(long[] array, int x, int y) {
        long t = array[x];
        array[x] = array[y];
        array[y] = t;

    }

    public static void shiftDown(long[] array, int size, int index) {
        //1. 先看要调整的节点是不是叶子结点    看其有没有左孩子节点  如果有  则不是  没有则是   是叶子结点的还结束调整
        while (true) {
            int leftIndex = index * 2 + 1;
            if (leftIndex >= size) { // 判断是不是叶子结点
                return;
            }
            //2.  不是叶子节点  找到index 的左右孩子节点  找出比较小的节点  (可以设置最小的为左节点   如果没有右节点 则左节点最小 否则 比较)
            int minIndex = leftIndex;
            int rightIndex = index * 2 + 2;
            if (rightIndex < size && array[rightIndex] < array[leftIndex]) {
                minIndex = rightIndex;
            }
            // 3. 用找出的最小节点 与index的节点的值在进行 比较  如果index的值小 结束调整 如果minIndex小的话  将minIndex 变位Index 然后开始新的循环
            if (array[index] <= array[minIndex]) {
                return;
            }
            swap(array, minIndex, index);
            index = minIndex;
        }
    }
    public static void createHeap(long[] array ,int size) {  // 建堆
        //用size- 1的小标 来找到 有子节点的父亲节点(之前的小标只要向下调整即可)
        int parents = ((size-1)-1)/2;
        // 用for 循环 逐个向下调整
        for (int i = parents; i >= 0; i--) {
            shiftDown(array , size , i);
        }
    }
    public static void shiftUp (long []array , int index ) { // 向上调整
        while (true) {
            // 1. 判断该节点是不是根节点如果是根节点 则直接退出
            if (index == 0) {
                return;
            }
            // 2. 和该节点的父亲的值进行比较 如果值大于等于其父亲节点 则 返回     这里不用比较左右孩子的节点因为 之前是个堆 所有如果有左孩子肯定比父亲节点大
            // 如果小于 则交换其父亲节点和index 的值     将index和父亲节点进行
            int parents = (index - 1) / 2;
            if (array[parents] >= array[index]) {
                return;
            } else {
                swap(array, parents, index);
            }
            index = parents;
        }
    }

    public static void main(String[] args) {
        long [] array = {1,5,9,23,85,41,5,3,5,4};
        createHeap(array,10);
        System.out.println(Arrays.toString(array));
    }
}