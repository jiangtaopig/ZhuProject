package daily.yiyuan.com.test_java.leet_code;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2019/12/3
 * 每次都选择最有利的选项
 * 贪心算法---背包问题
 * 题目：有N件物品和一个容量为V的背包。第i件物品的重量是w[i]，价值是v[i]。求解将哪些物品装入背包可使这些物品的重量总和不超过背包容量，且价值总和最大
 */
public class TestGreedyAlgorithm {

    /**
     * 背包最大容量
     */
    private static int KNAPSACK_MAX_CAPACITY = 150;
    /**
     * 保存各个物品的重量
     */
    private static int[] goods_weights = new int[]{5, 30, 60, 50, 40, 8, 25};
    /**
     * 保存各个物品的价值
     */
    private static int[] goods_values = new int[]{10, 40, 30, 50, 35, 40, 30};

    /**
     * 解题思路： 首先计算出每种物品的性价比，然后按照性价比的顺序进行排序；然后依次循环从性价比高的开始循环
     */


    public static void main(String[] args) {
        getMaxValue(KNAPSACK_MAX_CAPACITY, goods_weights, goods_values);
    }

    public static int getMaxValue(int maxCapacity, int[] weights, int values[]) {
        int res = 0;
        int size = weights.length;
        double[] priceRatio = new double[size]; //记录性价比的值，性价比 = 价值/重量
        int[] index = new int[size]; //记录性价比的下标

        for (int i = 0; i < size; i++) {
            priceRatio[i] = values[i] / (weights[i] * 1.0);
            index[i] = i;
        }

        //对性价比进行排序
        for (int i = 0; i< size - 1; i++){
            for (int j = i+1; j < size; j++){
                if (priceRatio[i] > priceRatio[j]){
                    double tmp = priceRatio[i];
                    priceRatio[i] = priceRatio[j];
                    priceRatio[j] = tmp;

                    int k = index[i];
                    index[i] = index[j];
                    index[j] = k;
                }
            }
        }
        //将排序好的重量和价值分别保存到数组
        int [] w1=new int[size];
        int [] v1=new int[size];

        //性价比递增
        for (int i = 0; i<size; i++){
            w1[i] = weights[index[i]];
            v1[i] = values[index[i]];
        }

        for (int i = size - 1; i >= 0; i--){//优先选择性价比最高的商品
            if (w1[i] < maxCapacity){
                int cnt = maxCapacity/w1[i]; //性价比最高的商品最多可以选几个
                maxCapacity -= cnt * w1[i];
                res += cnt * v1[i];
            }
        }
        return res;
    }
}
