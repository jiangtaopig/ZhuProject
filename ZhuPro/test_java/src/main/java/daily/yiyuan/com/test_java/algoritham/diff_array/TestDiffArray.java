package daily.yiyuan.com.test_java.algoritham.diff_array;


/**
 * ----------- 差分数组-------------------
 * <p>
 * <p>
 * 怎么求差分数组？ 假设有个数组 nums，对 nums 数组求差分数组步骤如下：
 * int [] diff = new int[nums.length];
 * diff[0] = nums[0]
 * for (int i = 1; i < nums.length; i++) {
 * diff[i] = nums[i] - nums[i-1];
 * }
 * <p>
 * 如何根据差分数组求出原始数组呢？一样的操作
 * int[] res = new int[diff.length];
 * res[0] = diff[0];
 * for (int i = 1; i < diff.length; i++) {
 * res[i] = res[i - 1] + diff[i];
 * }
 * <p>
 * 这样就可以基于差分数组，进行区间的增减操作，如果你想对 nums[i,j]的元素全部加3，那么只需要让 diff[i] += 3;
 * 然后 diff[j+1] -= 3;
 * <p>
 * 为什么？
 * 因为 diff[i] += 3 就相当于 nums 数组从索引为 i 开始到数组结束的所有元素都加上 3；
 * diff[j+1] -= 3 就相当于 nums 数组从索引为 j+1 开始到数组结束的所有元素都减去 3；
 */
public class TestDiffArray {
    public static void main(String[] args) {

        int [] nums = {8, 2, 6, 3, 1};
        Difference difference = new Difference(nums);
        difference.increment(1, 3, 3);
        difference.increment(0, 2, -2);
        int[] res = difference.result();

        for (int i = 0; i < res.length; i++) {
            System.out.print(res[i]+" , ");
        }
        System.out.println("---------");

        int[][] trips = {{2, 1, 5}, {3, 3, 7}};

       boolean canGetInCar = carPooling(trips, 4);
        System.out.println("canGetInCar = " + canGetInCar);

    }


    static class Difference {
        private int[] nums;
        private  int[] diff;

        public Difference(int[] nums) {
            this.nums = nums;
            // 构造差分数组
            diff = new int[nums.length];
            diff[0] = nums[0];

            for (int i= 1, len = nums.length; i < len; i++) {
                diff [i] = nums[i] - nums[i-1];
            }
        }

        /**
         * 给 nums 数组的闭区间[i, j]增加 value（value 可以是负数）
         */
        public void increment(int i, int j, int value) {
            diff[i] += value; // 相当于 nums 数组从索引 i 开始至数组结束都加上 value
           if (j + 1 < nums.length) {
               diff[j+1] -= value; // 相当于 nums 数组从索引 j+1 开始至数组结束都减去 value
           }
        }

        public int[] result() {
            int[] res = new int[diff.length];
            // 根据差分数组 构造结果数组
            res[0] = diff[0];
            for (int i = 1, len = diff.length; i < len; i++) {
                res[i] = res[i-1] + diff[i];
            }
            return res;
        }

    }


    // --------------------------------------- 实战 ------------------------------------------

    /**
     * 你是一个开公交车的司机，公交车的最大载客量为capacity，
     * 沿途要经过若干车站，给你一份乘客行程表int[][] trips，
     * 其中trips[i] = [num, start, end]代表着有num个旅客要从站点start上车，到站点end下车，
     * 请你计算是否能够一次把所有旅客运送完毕（不能超过最大载客量capacity）
     *
     * 函数签名：boolean carPooling(int[][] trips, int capacity);
     * 其中 ：0 <= trips[i][1] < trips[i][2] <= 1000，表示车站的最大个数为1000个，即车站的最大个数为1000，
     * 那么差分数组的长度可以直接设置成 1001
     *
     * 分析：trips[i] 代表一组区间操作，上车和下车相当于数组区间的加减
     *
     */

    public static boolean carPooling(int[][] trips, int capacity) {
        int [] nums = new int[1001];
        Difference difference = new Difference(nums);
        for (int[] trip : trips) {
            int val = trip[0]; // 上车人数
            int start = trip[1]; // 在第 trip[1] 站上车
            // 第 trip[2] 站乘客已经下车，
            // 即乘客在车上的区间是 [trip[1], trip[2] - 1]
            int end = trip[2] -1;
            difference.increment(start, end, val);
        }
        int [] res = difference.result();
        // 最后遍历数组，如果大于 capacity 表示超载了
        for (int v : res) {
            if (v > capacity)
                return false;
        }
        return true;
    }


    /**
     * 航班预订统计：
     * 这里有 n 个航班，他们分别从 1 到 n 进行编号
     * 我们这儿有一份预订列表，表中第 i 条记录 bookings[i] = [i, j, k] 表示
     * 我们在从 i 到 j 的每个航班上预订了 k 个位置。
     *
     * 请你返回一个长度为 n 的 answer ,按航班编号顺序返回每个航班上的预订数
     * 示例：
     * 输入 bookings = [[1, 2, 5], [2, 3, 6]] , n = 3
     * 输出： [5, 11, 6]
     * 原因： n = 3，即 nums = [0, 0, 0]
     * [1, 2, 5] 表示 nums 数组的索引从 0 到 1 加上 5 => [5, 5, 0]
     * [2, 3, 6] 表示 nums 数组的索引从 1 到 2 加上 6 => [5, 11, 6]
     *
     *  函数签名如下：int[] corpFlightBookings(int[][] bookings, int n)
     */

    public static int[] corpFlightBookings(int[][] bookings, int n) {
        int [] nums = new int[n]; // 开始各个航班上的预定数都为0
        Difference difference = new Difference(nums);
        for (int[] booking : bookings) {
            // 由于航班编号是从 1 开始的，而数组的索引是从 0 开始的，所以转为数组索引的时候要减 1；
            int i = booking[0] - 1;
            int j = booking[1] - 1;
            int k = booking[2];
            difference.increment(i, j, k);
        }
        return difference.result();
    }
}
