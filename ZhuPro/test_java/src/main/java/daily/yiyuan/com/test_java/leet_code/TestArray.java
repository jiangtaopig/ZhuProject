package daily.yiyuan.com.test_java.leet_code;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        for (int i = 0; i < moveArray.length; i++) {
            System.out.print(moveArray[i] + ", ");
        }

        int[] a = {6, 3, 7, 4, 9, 5};
        quickSort(a, 0, a.length - 1);

        int val = minCoin(62);
        System.out.println("---------------val----------------" + val);

        double maxLen = getMaxLength(new int[]{3, 4, 5}, 3, 4);
        System.out.println("maxLen = " + maxLen);

        int num = candyCount(new int[]{1, 2, 2, 2});
        System.out.println("...............num..........." + num);
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
     * @return 从数组后面开始遍历，不为0的移动到数组的后边，用right指针指向数组中待移入位置，遍历完成后，如果right > 0，那么边上剩下的全是0；
     * 那么从0依次放入数组中即可
     */
    private static int[] moveArray(int[] num) {
        int len = num.length;
        int right = len - 1;
        for (int i = len - 1; i >= 0; i--) {
            if (num[i] != 0) {
                num[right] = num[i];
                right--;
            }
        }
        if (right > 0) {
            //这里面一定是小于等于
            for (int i = 0; i <= right; i++) {
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
        int x = 0, y, cnt = 0;
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

    /**
     * 给定一个整数数组 nums 和一个目标值 target，请你在该数组中找出和为目标值的那 两个 整数，并返回他们的数组下标
     *
     * @param nums
     * @param target
     * @return
     */
    public static int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0, len = nums.length; i < len; i++) {
            int diff = target - nums[i];
            if (map.containsKey(diff)) {
                return new int[]{map.get(diff), i};
            }
            map.put(nums[i], i);
        }
        throw new IllegalArgumentException("no such two element");
    }

    /**
     * 三数之和等于0
     *
     * @param nums
     * @return
     */
    public static List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> ans = new ArrayList();
        int len = nums.length;
        if (nums == null || len < 3) return ans;
        Arrays.sort(nums); // 要先排序
        for (int i = 0; i < len; i++) {
            if (nums[i] > 0) break; // 如果当前数字大于0，则三数之和一定大于0，所以结束循环
            if (i > 0 && nums[i] == nums[i - 1]) continue; // 去重
            int L = i + 1;
            int R = len - 1;
            while (L < R) {
                int sum = nums[i] + nums[L] + nums[R];
                if (sum == 0) {
                    ans.add(Arrays.asList(nums[i], nums[L], nums[R]));
                    while (L < R && nums[L] == nums[L + 1]) L++; // 去重
                    while (L < R && nums[R] == nums[R - 1]) R--; // 去重
                    L++;
                    R--;
                } else if (sum < 0) {
                    L++;
                } else if (sum > 0) {
                    R--;
                }
            }
        }
        return ans;
    }

    private static void quickSort(int a[], int low, int high) {
        if (low >= high) {
            return;
        }
        int tmp = a[low];
        int i = low;
        int j = high;

        while (i < j) {
            while (a[j] >= tmp && j > i) {
                j--;
            }

            while (a[i] <= tmp && j > i) {
                i++;
            }
            if (i < j) {
                int t = a[i];
                a[i] = a[j];
                a[j] = t;
            }
        }

        a[low] = a[i];
        a[i] = tmp;
        quickSort(a, low, i - 1);
        quickSort(a, i + 1, high);
    }

    /**
     * 有 1元、4元、 16元以及64元的硬币 和一张1024的纸币，现在购买商品的价格 [0 < N <= 1024],问最少收到多少硬币？
     */

    private static int minCoin(int value) {
        int[] coins = new int[]{64, 16, 4, 1};
        int remainVal = 1024 - value;
        int len = coins.length;
        int cnt = 0;
        for (int i = 0; i < len && remainVal > 0; i++) {
            cnt += remainVal / coins[i];
            remainVal = remainVal % coins[i];
        }
        return cnt;
    }

    /**
     * 有N根绳子，第i根绳子长度为LiLi，现在需要M根等长的绳子，你可以对N根绳子进行任意裁剪（不能拼接），请你帮忙计算出这M根绳子最长的长度是多少。
     * 输入 3 4
     * 3跟绳子长度分别为 ： 3 5 4
     * 输出：
     * 2.50
     * 解释： 第一根和第三根绳子分别裁剪出一根2.50长度的绳子，第二根绳子刚好可以裁剪出两根2.50的长度绳子，刚好4根。
     */

    private static double getMaxLength(int[] arr, int n, int m) {
        int max = arr[0];
        // 找到最长的绳子
        for (int i = 1, len = arr.length; i < len; i++) {
            if (arr[i] > max)
                max = arr[i];
        }
        double low = 0;
        double high = max;
        double mid = 0;
        int cnt;
        while (high - low > 1.0E-4) {
            mid = (high - low) / 2 + low;
            cnt = check(arr, mid);
            if (cnt >= m) { // 计算出绳子的根数大于等于m,表示长度小了,记住一定要 >=
                low = mid;
            } else {
                high = mid;
            }
        }
        return mid;
    }

    private static int check(int[] arr, double maxLen) {
        int cnt = 0;
        for (int i = 0; i < arr.length; i++) {
            cnt += arr[i] / maxLen;
        }
        return cnt;
    }

    /**
     * 老师想给孩子们分发糖果，有 N 个孩子站成了一条直线，老师会根据每个孩子的表现，预先给他们评分。
     * 每个孩子至少分配到 1 个糖果。
     * 相邻的孩子中，评分高的孩子必须获得更多的糖果。
     * 那么这样下来，老师至少需要准备多少颗糖果呢？
     * <p>
     * 示例 1:
     * 输入: [1,0,2]
     * 输出: 5
     * 解释: 你可以分别给这三个孩子分发 2、1、2 颗糖果。
     * <p>
     * 示例 2:
     * 输入: [1,2,2]
     * 输出: 4
     * 解释: 你可以分别给这三个孩子分发 1、2、1 颗糖果。第三个孩子只得到 1 颗糖果，这已满足上述两个条件。
     *
     * @param score
     */
    private static int candyCount(int[] score) {
        int[] candys = new int[score.length];
        candys[0] = 1;
        // 先正序遍历，如果后一位孩子的得分比前一位的高，则糖果数等于前一位孩子的糖果数加1，否则给1
        for (int i = 0, len = score.length; i < len - 1; i++) {
            if (score[i + 1] > score[i]) {
                candys[i + 1] = candys[i] + 1;
            } else {
                candys[i + 1] = 1;
            }
        }

        // 然后倒序遍历，如果前一个孩子的得分比后一位的得分高且糖果数小于等于后一位的糖果数，则前一个孩子的糖果数等于后一个孩子的糖果数加1
        for (int i = score.length - 1; i > 0; i--) {
            if (score[i - 1] > score[i] && candys[i - 1] <= candys[i]) {
                candys[i - 1] = candys[i] + 1;
            }
        }

        int cnt = 0;
        for (int i = 0, len = candys.length; i < len; i++) {
            cnt += candys[i];
        }

        return cnt;
    }


}
