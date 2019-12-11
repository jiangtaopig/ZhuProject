package daily.yiyuan.com.test_java.leet_code;

import java.util.ArrayList;
import java.util.List;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2019/12/5
 *
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
}
