package daily.yiyuan.com.test_java.algoritham.prefix_sum;

/**
 * 前缀和数组
 */
public class PrefixSum {
    public static void main(String[] args) {

        int[] nums = {-2, 0, 3, -5, 2, -1};
        NumArray numArray = new NumArray(nums);
        int res = numArray.sumRange(0, 2);
        int res2 = numArray.sumRange(2, 5);
        int res3 = numArray.sumRange(0, 5);
        System.out.println("PrefixSum:: res = " + res + " , res2 = " + res2 + " , res3 = " + res3);

        int[][] matrix = {{1, 2, 3, 4}, {5, 6, 7, 8}};
        testMatrix(matrix);

    }

    private static void testMatrix(int[][] matrix) {
        int row = matrix.length;
        int column = matrix[0].length;

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                System.out.println("matrix["+i+"]["+j+"] = " + matrix[i][j]);
            }
        }
        System.out.println("PrefixSum::testMatrix row " + row + ", column = " + column);
    }



    /**
     * 计算索引 left 和 right（包含 left 和 right）之间的 nums 元素的和 ，其中 left <= right
     * <p>
     * 实现 NumArray 类：
     * NumArray(int[] nums) 使用数组 nums 初始化对象
     * int sumRange(int i, int j) 返回数组 nums 中索引 left 和 right 之间的元素的总和包含 left 和 right两点
     * （也就是 nums[left] + nums[left + 1] + ... + nums[right])
     * <p>
     * 示例：
     * 输入：
     * ["NumArray", "sumRange", "sumRange", "sumRange"]
     * [[[-2, 0, 3, -5, 2, -1]], [0, 2], [2, 5], [0, 5]]
     * 输出：
     * [null, 1, -1, -3]
     * <p>
     * 解释：
     * NumArray numArray = new NumArray([-2, 0, 3, -5, 2, -1]);
     * numArray.sumRange(0, 2); // return 1 ((-2) + 0 + 3)
     * numArray.sumRange(2, 5); // return -1 (3 + (-5) + 2 + (-1))
     * numArray.sumRange(0, 5); // return -3 ((-2) + 0 + 3 + (-5) + 2 + (-1))
     */

    static class NumArray {

        private int[] nums;
        private int[] preSum; // 定义的前缀和数组

        public NumArray(int[] nums) {
            this.nums = nums;
            // preSum[0] 索引为0 的位置存放0，
            // preSum[i] 存放 num[0] + num[1]+...+num[i-1]
            preSum = new int[nums.length + 1];
            // 注意这里面一定要是 i <= len ; 因为 preSum 的长度比 nums 长度多一个，
            // 如果是 i < len 的话，那么 preSum[len] 存放的就是0了，而不是 nums 数组所有元素之和
            for (int i = 1, len = nums.length; i <= len; i++) {
                preSum[i] = nums[i - 1] + preSum[i - 1];
            }
        }

        public int sumRange(int left, int right) {
            // 因为是包含索引为 left 和 right的元素之和，所以减去 preSum[left]而不是preSum[left+1]
            return preSum[right + 1] - preSum[left];
        }
    }


}
