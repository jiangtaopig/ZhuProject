package daily.yiyuan.com.test_java.leet_code;

import java.util.ArrayList;
import java.util.List;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2020/2/24
 */
public class TestTree {
    static List<List<Integer>> levels = new ArrayList<>();

    public static void main(String[] args) {

//        testSameTree();

        TreeNode p = new TreeNode(1);
        TreeNode pR = new TreeNode(2);
        p.right = pR;
        p.left = new TreeNode(5);
        pR.left = new TreeNode(3);
        pR.right = new TreeNode(4);

        inorderTraversal(p);

        levelOrder(p);
        levels.size();

        levelZOrder(p);

        numTrees(2);
    }

    private static void testSameTree() {
        TreeNode p = new TreeNode(1);
        TreeNode pL = new TreeNode(2);
        TreeNode pR = new TreeNode(3);
        p.left = pL;
        p.right = pR;

        TreeNode q = new TreeNode(1);
        TreeNode qL = new TreeNode(2);
        TreeNode qR = new TreeNode(4);

        q.left = qL;
        q.right = qR;

        System.out.println("isSameTree >> " + isSameTree(p, q));
    }


    public static boolean isSameTree(TreeNode p, TreeNode q) {
        if (p == null && q == null) {//2棵树都为空
            return true;
        }
        //其中一棵树为空
        if (p == null && q != null || p != null && q == null) {
            return false;
        }
        //2棵树都不为空，但是 值不同
        if (p.val != q.val) {
            return false;
        }
        return isSameTree(p.left, q.left) && isSameTree(p.right, q.right);
    }


    /**
     * 是否是对称的二叉树
     *
     * @param root
     * @return
     */
    public static boolean isSymmetric(TreeNode root) {
        return isMirror(root, root);

    }

    public static boolean isMirror(TreeNode p, TreeNode q) {
        if (p == null && q == null) {
            return true;
        }
        if (p == null || q == null) {
            return false;
        }
        if (p.val != q.val) {
            return false;
        }
        //
        return isMirror(p.left, q.right) && isMirror(p.right, q.left);
    }

    /**
     * 树的深度
     *
     * @param root
     * @return
     */
    public static int maxDepth(TreeNode root) {
        return height(root);
    }

    public static int height(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int left = height(root.left);
        int right = height(root.right);
        return left >= right ? (left + 1) : (right + 1);
    }

    /**
     * 二叉树的中序遍历
     *
     * @param root
     * @return
     */
    public static List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        if (root == null) {
            return res;
        }
        inorder(root, res);
        System.out.println("res = " + res);
        return res;
    }

    public static void inorder(TreeNode root, List<Integer> res) {
        if (root == null) {
            return;
        }
        inorder(root.left, res);
        System.out.println("val = " + root.val);
        res.add(root.val);
        inorder(root.right, res);
    }

    /**
     * 判断一棵树是否是搜索二叉树
     *
     * @param root
     * @return
     */
    public static boolean isValidBST(TreeNode root) {
        return isValidBST(root, null, null);
    }

    public static boolean isValidBST(TreeNode root, TreeNode min, TreeNode max) {
        if (root == null)
            return true;

        if (min != null && min.val >= root.val)
            return false;

        if (max != null && max.val <= root.val)
            return false;
        return isValidBST(root.left, min, root) && isValidBST(root.right, root, max);
    }


    /**
     * 按层遍历二叉树
     * 1 2 3 null null 4, 遍历的结果就是 [[1], [2, 3], [4]];
     *
     * @param root
     * @return
     */
    public static List<List<Integer>> levelOrder(TreeNode root) {
        levelOrder(root, 0);
        return levels;
    }

    public static void levelOrder(TreeNode root, int level) {
        if (root == null) {
            return;
        }
        if (levels.size() == level) {
            levels.add(new ArrayList<Integer>());
        }
        levels.get(level).add(root.val);
        levelOrder(root.left, level + 1);
        levelOrder(root.right, level + 1);
    }

    /**
     * Z 字形 打印 二叉树
     *
     * @param root
     * @return
     */
    public static List<List<Integer>> levelZOrder(TreeNode root) {
        List<List<Integer>> res = new ArrayList<>();
        levelZOrder(root, 0, res);
        return res;
    }

    public static void levelZOrder(TreeNode root, int level, List<List<Integer>> res) {
        if (root == null) {
            return;
        }
        int size = res.size();
        if (size == level) {
            res.add(new ArrayList<Integer>());
        }
        res.get(level).add(root.val);
        if ((size & 1) == 0) {//偶数
            levelZOrder(root.left, level + 1, res);
            levelZOrder(root.right, level + 1, res);
        } else {
            levelZOrder(root.right, level + 1, res);
            levelZOrder(root.left, level + 1, res);
        }

    }

    /**
     * 给定 n ,那么 1~ n 可以构成多少种 搜索二叉树
     *
     * @param n 标签：动态规划
     *          假设n个节点存在二叉排序树的个数是G(n)，令f(i)为以i为根的二叉搜索树的个数，则
     *          G(n) = f(1) + f(2) + f(3) + f(4) + ... + f(n)G(n)=f(1)+f(2)+f(3)+f(4)+...+f(n)
     *          <p>
     *          当i为根节点时，其左子树节点个数为i-1个，右子树节点为n-i，则
     *          f(i) = G(i-1)*G(n-i)f(i)=G(i−1)∗G(n−i)
     *          <p>
     *          综合两个公式可以得到 卡特兰数 公式
     *          G(n) = G(0)∗G(n−1)+G(1)∗(n−2)+...+G(n−1)∗G(0)
     */
    public static int numTrees(int n) {
        int[] G = new int[n + 1];
        G[0] = 1;
        G[1] = 1;

        for (int i = 2; i <= n; ++i) {
            for (int j = 1; j <= i; ++j) {
                G[i] += G[j - 1] * G[i - j];
            }
        }
        return G[n];
    }

}