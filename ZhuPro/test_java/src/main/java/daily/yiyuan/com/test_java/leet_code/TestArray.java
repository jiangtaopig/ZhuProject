package daily.yiyuan.com.test_java.leet_code;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2020/5/12
 * 测试数组的算法
 */
public class TestArray {
    public static void main(String[] args) {

        int[] num1 = {2, 4, 7, 0, 0, 0, 0};
        int[] num2 = {1, 2, 5, 6};
        int m = 3;
        int n = 4;
        int[] res = mergeSortedArray(num1, m, num2, n);
        for (int i = 0; i < res.length; i++) {
            System.out.println("res[" + i + "]=" + res[i]);
        }

        int[] moveArray = moveArray(new int[]{0, 1, 0, 2, 0, 4, 0, 0, 8, 1});
        for (int i = 0; i < moveArray.length; i++){
            System.out.print(moveArray[i]+", ");
        }
    }

    /**
     * 合并2个有序数组， 假设数组1 的长度能够包含数组2  ；num1 = [2, 3, 4, 0, 0, 0, 0], num2 = [1, 2, 5, 6]
     * 合并完成后 num1 = [1, 2, 2, 3, 4, 5, 6]
     *
     * @param arr1
     * @param m    表示数组1中元素数量，即 3 个
     * @param arr2
     * @param n    表示数组2中元素的数量，即 4 个
     * @return 思路，依次从后往前比较2个数组元素的大小，大的放在arr1的最后，
     * 如果比较完成后发现 n > 0 则表示数组2 中剩下的元素都小于数组1中的最小数了，直接按顺序插到数组1
     */
    private static int[] mergeSortedArray(int[] arr1, int m, int[] arr2, int n) {
        int len = m + n;
        int tmp;
        // m 和 n 表示的是数组的长度， 所以不用大于等于0
        while (m > 0 && n > 0) {
            if (arr1[m - 1] >= arr2[n - 1]) {
                tmp = arr1[m - 1];
                m--;
            } else {
                tmp = arr2[n - 1];
                n--;
            }
            arr1[--len] = tmp;
        }
        // 比较完成后，如果发现 n > 0, 则表示数组2中剩下的数都小于等于数组1中的最小元素，直接依次添加到数组1
        if (n > 0) {
            for (int i = 0; i < n; i++) {
                arr1[i] = arr2[i];
            }
        }
        return arr1;
    }

    /**
     * 算法描述：一个数组中 只有元素0和非0元素；不使用另外的数组，将非0的元素全部移到右边，为0的元素全放左边
     *
     * @param num
     * @return
     * 从数组后面开始遍历，不为0的移动到数组的后边，用right指针指向数组中待移入位置，遍历完成后，如果right > 0，那么边上剩下的全是0；
     * 那么从0依次放入数组中即可
     */
    private static int[] moveArray(int[] num) {
        int len = num.length;
        int right = len - 1;
        for (int i = len - 1; i >= 0; i--) {
            if (num[i] != 0){
                num[right] = num[i];
                right--;
            }
        }
        if (right > 0){
            //这里面一定是小于等于
            for (int i = 0; i <= right; i++){
                num[i] = 0;
            }
        }
        return num;
    }

    /**
     * 找最长的上升子序列
     * 1、5、3、6、7、8、2  结果是 3 6 7 8
     *
     * @param arr
     */
    public static void findMaxSequence(int[] arr) {
        int start = 0;
        int n = arr.length;
        int x = 0, y , cnt = 0;
        int max = 0; // 表示最长上升子序列的个数

        for (int i = 0; i < n - 1; i++) {
            y = i + 1;
            if (arr[i + 1] > arr[i]) {
                cnt++;
                if (cnt > max) {
                    max = cnt;
                    start = x;
                }
            } else {
                cnt = 0;
                x = y;
            }
        }

        System.out.print("maxSubSequence : ");
        for (int i = start; i <= start + max; i++) {
            System.out.print(arr[i] + " ");
        }
    }
}
