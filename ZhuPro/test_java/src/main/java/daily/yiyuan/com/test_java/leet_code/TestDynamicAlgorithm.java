package daily.yiyuan.com.test_java.leet_code;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2019/12/5
 * 参考 ：https://labuladong.gitbook.io/algo/
 * 动态规划算法
 */
public class TestDynamicAlgorithm {
    public static void main(String[] args) {

        List<List<Integer>> lists = new ArrayList<>();
        List<Integer> row1 = new ArrayList<>();
        row1.add(2);

        List<Integer> row2 = new ArrayList<>();
        row2.add(3);
        row2.add(4);

        List<Integer> row3 = new ArrayList<>();
        row3.add(6);
        row3.add(5);
        row3.add(7);

        List<Integer> row4 = new ArrayList<>();
        row4.add(4);
        row4.add(1);
        row4.add(8);
        row4.add(3);
        lists.add(row1);
        lists.add(row2);
        lists.add(row3);
        lists.add(row4);

        int minVal = triangle(lists);
        System.out.println("最小路径值 minVal = "+minVal);


        int [] arr = {1, 3, -7, 4, 2, -3, 3, 4};
        int val = maxSubArray(arr);
        System.out.println("val = "+val);

        int maxSubSequence = maxSubSequence(arr);
        System.out.println("最长上升子序列的长度 = "+maxSubSequence);
    }


    /**
     * 三角形问题----找出自顶向下的最小路径和。每一步只能移动到下一行中相邻的结点上。
     * @param triangle
     * @return 最小路劲和
     *
     * 解法： “最后一行元素到当前元素的最小路径和”，对于 [0][0] 这个元素来说，最后状态表示的就是我们的最终答案
     * dp[i][j] = Math.min(dp[i + 1][j], dp[i + 1][j + 1]) + triangle[i][j]
     */
    public static int triangle(List<List<Integer>> triangle){
        int n = triangle.size();
        int [][] dp = new int[n][n];
        List<Integer> lastRow = triangle.get(n - 1);
        for (int i = 0; i< lastRow.size(); i++){
            dp[n-1][i] = lastRow.get(i);
        }

        //然后从倒数第二行开始计算
        for (int i = n - 2; i>=0; i--){
            List<Integer> row = triangle.get(i);
            for (int j = 0; j < i + 1; j++){
                dp[i][j] = Math.min(dp[i+1][j], dp[i+1][j+1]) + row.get(j);
            }
        }
        return dp[0][0];
    }

    /**
     * 给定一个整数数组 nums ，找到一个具有最大和的连续子数组（子数组最少包含一个元素），返回其最大和。
     * @param arr
     * @return
     *
     * 这个时候我们需要思考的问题是 “以 i 结尾的所有子数组中，和最大的是多少？”，然后我们去试着拆解，这里其实只有两种情况：
     * 1.  i 这个位置的元素自成一个子数组;
     * 2.  i 位置的元素的值 + 以 i – 1 结尾的所有子数组中的子数组和最大的值
     */
    public static int maxSubArray(int [] arr){
        if (arr == null || arr.length == 0){
            return 0;
        }

        int [] d = new int[arr.length];
        d[0] = arr[0];
        int result = d[0];

        for (int i = 1; i < arr.length; i++){
            d[i] = Math.max(d[i-1] + arr[i], arr[i]);
            result = Math.max(result, d[i]);
        }
        return result;
    }

    /**
     * 给定一个无序的整数数组，找到其中最长上升子序列的长度。
     *
     *  输入: [10,9,2,5,3,7,101,18]
     *  输出: 4
     *  解释: 最长的上升子序列是 [2,3,7,101]，它的长度是 4。
     * @param arr
     * @return
     */
    public static int maxSubSequence(int [] arr){
        int max = 0;
        int n = arr.length;
        // dp[i] 表示 以arr[i]结尾的最长上升子序列的个数
        int [] dp = new int[n];
        Arrays.fill(dp, 1);//因为最少也有1个
        for (int i = 0; i< n; i++){
            for (int j = 0; j < i; j++){
                if (arr[i] > arr[j]){
                    dp[i] = Math.max(dp[j]+1, dp[i]);
                }
            }
            max = Math.max(max, dp[i]);
        }
        return max;
    }
}
