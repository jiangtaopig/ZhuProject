package daily.yiyuan.com.test_java.data_structure;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2019/9/26
 */
public class QuickSort {
    //快速排序的算法思想：假设有数据 6， 3， 7, 4, 9, 5；
    //首先找一个一个基准数，一般就是待排序数组的第一位，也就是6；
    //接下来就是要将数组中小于6的数放在6的左边，大于6的数放在6的右边，即：3， 4， 5， 6， 7, 9；
    //具体操作如下：首先设置2个哨兵i, 和 j,分别指向数据的第一位和最后一位，即 i 指向 6， j 指向 5
    //由于基准数 是最左边的数，所以让哨兵 j 先行动，执行 j--,直到 j指向的数小于6才停止行动，然后哨兵 i 开始行动，执行 i++，直到i指向的数大于6才停下来，然后交换i和j指向的数；
    //执行完上面的交换步骤后，i 指向 7，j 指向 5, 交换他们所指向的数： 6， 3， 5， 4，9， 7；然后哨兵 j继续行动，直到j指向4，停下来，i开始行动，i++也指向4，那么交换i指向的数和基准数6
    //即 4， 3， 5， 6， 9 ，7 ；这样6的左边都小于6，右边都大于6；同样的方式对6左边的4， 3, 5进行排序，对右边的9， 7进行排序，

    public static void main(String [] args){
        int [] arr = {6, 3, 7, 4, 9, 6};
        quickSort(arr, 0, arr.length-1);
        for (int i = 0; i < arr.length; i++){
            System.out.println("arr["+i+"] = "+arr[i]);
        }
    }

    private static void quickSort(int [] arr, int low, int high){
        if (low > high){
            return;
        }
        int tmp = arr[low];
        int i = low;
        int j = high;

        while (i < j){
            //先处理右边
            while (arr[j] >= tmp && i < j){
                --j;
            }
            //处理右边的
            while (arr[i] <= tmp && i < j){
                ++i;
            }
            //如果满足条件则交换 i 和 j 的数据
            if (i < j){
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }

        arr[low] = arr[i];
        arr[i] = tmp;

        //递归排序基准数左边的数据
        quickSort(arr, low, j-1);
        //递归排序基准数右边的数据
        quickSort(arr, j+1, high);
    }
}
