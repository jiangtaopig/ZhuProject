package daily.yiyuan.com.test_java.collection;

import java.util.Arrays;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2019/9/17
 */
public class MyArrayList<E> {
    private static final Object[] DEFAULTCAPACITY_EMPTY_ELEMENTDATA = {};
    transient Object[] elementData;
    private int size;

    public MyArrayList(){
        elementData = DEFAULTCAPACITY_EMPTY_ELEMENTDATA;
    }

    private static int calculateCapacity(Object[] var0, int var1) {
        return var0 == DEFAULTCAPACITY_EMPTY_ELEMENTDATA ? Math.max(10, var1) : var1;
    }

    private void ensureCapacityInternal(int var1) {
        this.ensureExplicitCapacity(calculateCapacity(this.elementData, var1));
    }

    private void ensureExplicitCapacity(int var1) {
//        ++this.modCount;
        if (var1 - this.elementData.length > 0) {
            this.grow(var1);
        }

    }

    private void grow(int var1) {
        int var2 = this.elementData.length;
        int var3 = var2 + (var2 >> 1);
        if (var3 - var1 < 0) {
            var3 = var1;
        }

        this.elementData = Arrays.copyOf(this.elementData, var3);
    }

    public boolean add(E var1) {
        this.ensureCapacityInternal(this.size + 1);
        this.elementData[this.size++] = var1;
        return true;
    }
}
