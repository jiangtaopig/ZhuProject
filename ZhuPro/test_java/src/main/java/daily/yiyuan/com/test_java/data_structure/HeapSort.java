package daily.yiyuan.com.test_java.data_structure;

import java.util.Arrays;

/**
 * Created by zhu on 2019/3/6.
 * 堆排序
 * <p> //https://blog.csdn.net/varyall/article/details/80992679 ------------------建堆过程参考
 * 堆是一棵顺序存储的完全二叉树。完全二叉树中所有非叶子结点的值均不大于（或不小于）其左、右孩子节点的值。
 * 其中每个节点的值小于等于其左、右孩子的值，这样的堆称为小根堆；
 * 其中每个节点的值大于等于其左、右孩子的值，这样的堆称为大根堆；
 */

public class HeapSort {
    private int A[];
    private int heapSize;

    protected int parent(int i) {
        return (i - 1) / 2;
    }

    protected int left(int i) {
        return 2 * i + 1;
    }

    protected int right(int i) {
        return 2 * i + 2;
    }

    protected void maxHeapify(int i) {
        int l = left(i);
        int r = right(i);
        int largest = i;
        if (l <= heapSize - 1 && A[l] > A[i])
            largest = l;
        if (r <= heapSize - 1 && A[r] > A[largest])
            largest = r;
        if (largest != i) {
            int temp = A[i];
            // swap
            A[i] = A[largest];
            A[largest] = temp;
            this.maxHeapify(largest);
        }
    }

    /**
     * 构建堆的过程：
     * 1、从最后一个非叶子结点开始，然后比较该结点和子节点的大小，结点最大的值来替换该结点的值；
     * 2、如果子节点的值大于该结点的值，则比较子节点和它子节点的大小，结点最大的值来替换该结点的值
     */
    public void buildMaxHeap(int[] A) {
        this.A = A;
        this.heapSize = A.length;

        for (int i = parent(heapSize - 1); i >= 0; i--) {
            maxHeapify(i);
        }
        System.out.println(".............建大根堆后...............");
        for (int j = 0; j < A.length; j++) {
            System.out.println("A[" + j + "] = " + A[j]);
        }
    }

    /**
     * 大根堆建完后，最大值在根顶
     * 堆排序步骤：
     * 1.用根顶元素和最后一个叶子结点值替换，那么最后的叶子结点就是最大值了，是有序的了，下次排序不需要动它了
     * 2.从堆顶开始重新创建大根堆，因为替换了A[0]和A[length-1],所以从堆顶开始
     */
    public void heapSort() {
        int step = 1;
        for (int i = A.length - 1; i > 0; i--) {
            int temp = A[i];
            A[i] = A[0];
            A[0] = temp;
            heapSize--;//用根顶元素和最后一个叶子结点值替换，那么最后的叶子结点就是最大值了，是有序的了，下次排序不需要动它了
            System.out.println("Step: " + (step++) + Arrays.toString(A));
            maxHeapify(0);//从堆顶开始重新创建大根堆，因为替换了A[0]和A[length-1],所以从堆顶开始
        }
        System.out.println(".............排序后...............");
        for (int j = 0; j < A.length; j++) {
            System.out.println("A[" + j + "] = " + A[j]);
        }
    }
}


