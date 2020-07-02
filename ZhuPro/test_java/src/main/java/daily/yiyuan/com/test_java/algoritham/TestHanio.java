package daily.yiyuan.com.test_java.algoritham;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2020/6/29
 * 汉诺塔 递归解决
 */
public class TestHanio {
    public static void main(String[] args) {
        hanio(3, "x", "y", "z");
        System.out.println(fabalaqi(3));
    }

    /**
     * 题目描述：有 n 个盘子从小到大的放在x柱子上，现在要求将 x 柱子上的盘子 都移到z柱子上，且一次只能移动一个盘子，大的盘子不可以放在小的盘子上面；
     * 解题思想：
     * 1. 先将n-1个盘子移动y柱子上
     * 2. 将第n个盘子移动z柱子上
     * 3. 将n-1个盘子移动到z柱子上
     * 其中 第1、3步也是汉诺塔问题； hanio(n, x, y, z) 表示将n个盘子从柱子x移动到柱子z
     * 1. 把从小到大的 n-1个盘子从x移到y柱子上，代码就是 hanio(n-1, x, z, y)
     * 2. 把最大的盘子从x移到z, 操作就是直接完成一次移动就行了
     * 3. 再把从小到大的n-1个盘子从y柱子上移动到z柱子 : hanio(n-1, y, x, z)
     *
     * @param n
     * @param x
     * @param y
     * @param z
     */
    private static void hanio(int n, String x, String y, String z) {
        if (n < 1) {
            System.out.println("汉诺塔的层数不能小于1");
        } else if (n == 1) {
            System.out.println("移动: " + x + "-->" + z);
        } else {
            hanio(n - 1, x, z, y);
            System.out.println("移动：" + x + "-->" + z);
            hanio(n - 1, y, x, z);
        }
    }

    private static int fabalaqi(int n){
        if (n < 0){
            return -1;
        } else if (n == 0 || n == 1){
            return 1;
        }else {
            return fabalaqi(n-1) + fabalaqi(n-2);
        }
    }
}
