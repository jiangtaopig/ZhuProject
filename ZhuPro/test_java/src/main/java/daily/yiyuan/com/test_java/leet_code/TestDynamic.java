package daily.yiyuan.com.test_java.leet_code;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2019/10/30
 * 动态规划算法
 */
public class TestDynamic {
    public static void main(String[] args) {

        int[] arr = {-2, 11, -4, 13, -5, -2};
        int maxSubSum = findMaxSubSum(arr);
        System.out.println("maxSubSum = " + maxSubSum);

        String str1 = "aiba";
        String str2 = "abdka";
        int maxSubListLen = findLCS(str1, str1.length(), str2, str2.length());
        System.out.println("maxSubListLen = " + maxSubListLen);
    }

    /**
     * 1. 求素组的连续子段和的最大值
     * 例如：-2,11,-4,13,-5,-2，和最大的子段为：11,-4,13。和为20。
     */
    public static int findMaxSubSum(int[] arr) {
        int nowSum = 0;
        int maxSum = 0;
        for (int i = 0; i < arr.length; i++) {
            nowSum += arr[i];
            if (nowSum > maxSum) {
                maxSum = nowSum;
            }
            if (nowSum < 0) {
                nowSum = 0;
            }
        }
        return maxSum;
    }

    /**
     * 最长公共子序列Lcs
     * 比如两个串为：
     * abcicba
     * abdkscab
     * ab是两个串的子序列，abc也是，abca也是，其中abca是这两个字符串最长的子序列
     */

    public static int findLCS(String A, int n, String B, int m) {
        int[][] dp = new int[n + 1][m + 1];
        for (int i = 0; i <= n; i++) {
            for (int j = 0; j <= m; j++) {
                dp[i][j] = 0;
            }
        }
        // String str1 = "aiba";
        //        String str2 = "abdka";
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                if (A.charAt(i - 1) == B.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);//
                }
            }
        }
        return dp[n][m];
    }


}
