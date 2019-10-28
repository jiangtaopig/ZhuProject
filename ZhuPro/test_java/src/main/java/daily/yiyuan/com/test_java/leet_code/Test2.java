package daily.yiyuan.com.test_java.leet_code;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2019/10/24
 */
public class Test2 {
    public static void main(String[] args) {
        int[][] nums = {{1, 2, 3}, {4, 5, 6}};
        matrixReshape(nums, 3, 2);
    }

    public static void matrixReshape(int[][] nums, int r, int c) {
        int [] a = nums[0];
        if (r * c == nums.length){
            int row = nums[0].length;
            int coloum = nums.length / row;
        }
    }
}
