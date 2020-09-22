package daily.yiyuan.com.test_java.leet_code;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2020/4/8
 *  yuan深度优先搜索 DFS deep first search
 */
public class TestDfs {
    public static int M, N, K;

    public static void main(String[] args) {

    }

    /**
     * 机器人的运动范围
     * 地上有一个m行n列的方格，从坐标 [0,0] 到坐标 [m-1,n-1] 。一个机器人从坐标 [0, 0] 的格子开始移动，
     * 它每次可以向左、右、上、下移动一格（不能移动到方格外），也不能进入行坐标和列坐标的数位之和大于k的格子。例如，当k为18时，机器人能够进入方格 [35, 37] ，
     * 因为3+5+3+7=18。但它不能进入方格 [35, 38]，因为3+5+3+8=19。请问该机器人能够到达多少个格子？
     * <p>
     * 链接：https://leetcode-cn.com/problems/ji-qi-ren-de-yun-dong-fan-wei-lcof
     */

    public static int movingCount(int m, int n, int k) {
        M = m;
        N = N;
        K = k;
        //标记访问过的位置
        boolean[][] visited = new boolean[m][n];
        return dfs(0, 0, 0, visited);
    }

    /**
     * 深搜
     *
     * @param i       横坐标
     * @param j       纵坐标
     * @param sum     坐标数位和
     * @param visited 标记数组
     * @return
     */
    private static int dfs(int i, int j, int sum, boolean[][] visited) {
        //如果 坐标越界 或者 数位和大于k 或者 已经访问过，则停止当前方向的深搜
        if (i == M || j == N || sum > K || visited[i][j]) return 0;
        //标记为已访问
        visited[i][j] = true;
        //向下或者向右深搜
        return 1 + dfs(i + 1, j, sum(i + 1, j), visited) + dfs(i, j + 1, sum(i, j + 1), visited);
    }


    /**
     * 计算数位之和， 如 x =15, y = 23, sum = 1+5+2+3= 11
     *
     * @param x
     * @param y
     * @return
     */
    public static int sum(int x, int y) {
        int sum = 0;
        while (x != 0) {
            sum += x % 10;
            x /= 10;
        }
        while (y != 0) {
            sum += y % 10;
            y /= 10;
        }
        return sum;
    }

}
