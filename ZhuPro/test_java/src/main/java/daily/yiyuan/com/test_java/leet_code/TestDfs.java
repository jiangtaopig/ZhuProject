package daily.yiyuan.com.test_java.leet_code;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2020/4/8
 *  yuan����������� DFS deep first search
 */
public class TestDfs {
    public static int M, N, K;

    public static void main(String[] args) {

    }

    /**
     * �����˵��˶���Χ
     * ������һ��m��n�еķ��񣬴����� [0,0] ������ [m-1,n-1] ��һ�������˴����� [0, 0] �ĸ��ӿ�ʼ�ƶ���
     * ��ÿ�ο��������ҡ��ϡ����ƶ�һ�񣨲����ƶ��������⣩��Ҳ���ܽ�������������������λ֮�ʹ���k�ĸ��ӡ����磬��kΪ18ʱ���������ܹ����뷽�� [35, 37] ��
     * ��Ϊ3+5+3+7=18���������ܽ��뷽�� [35, 38]����Ϊ3+5+3+8=19�����ʸû������ܹ�������ٸ����ӣ�
     * <p>
     * ���ӣ�https://leetcode-cn.com/problems/ji-qi-ren-de-yun-dong-fan-wei-lcof
     */

    public static int movingCount(int m, int n, int k) {
        M = m;
        N = N;
        K = k;
        //��Ƿ��ʹ���λ��
        boolean[][] visited = new boolean[m][n];
        return dfs(0, 0, 0, visited);
    }

    /**
     * ����
     *
     * @param i       ������
     * @param j       ������
     * @param sum     ������λ��
     * @param visited �������
     * @return
     */
    private static int dfs(int i, int j, int sum, boolean[][] visited) {
        //��� ����Խ�� ���� ��λ�ʹ���k ���� �Ѿ����ʹ�����ֹͣ��ǰ���������
        if (i == M || j == N || sum > K || visited[i][j]) return 0;
        //���Ϊ�ѷ���
        visited[i][j] = true;
        //���»�����������
        return 1 + dfs(i + 1, j, sum(i + 1, j), visited) + dfs(i, j + 1, sum(i, j + 1), visited);
    }


    /**
     * ������λ֮�ͣ� �� x =15, y = 23, sum = 1+5+2+3= 11
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
