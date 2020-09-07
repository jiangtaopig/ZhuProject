package daily.yiyuan.com.test_java.leet_code;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2020/2/24
 */
public class TestTree {

    private static int res;
    private static int leftHeight;
    private static int rightHeight;

    public static void main(String[] args) {
//        testSameTree();
        System.out.println(1 << 31 - 1);
        System.out.println("------------------------------------------------");

        TreeNode p = new TreeNode(1);
        TreeNode pR = new TreeNode(3);
        p.right = pR;
        p.left = new TreeNode(2);
        pR.left = new TreeNode(4);
        pR.right = new TreeNode(5);

        int height = getTreeHeightByRecursion(p);
        System.out.println("height = " + height);
        System.out.println("-----------------------------------------");

        levelTraverse(p);

        inorderTraversal(p);

        System.out.println("---------------levelOrder-------------");
        List<List<Integer>> res = levelOrder(p);
        System.out.println(res);

        System.out.println("---------------levelZOrder-------------");
        List<List<Integer>> res2 = levelZOrder(p);
        System.out.println(res2);

        numTrees(2);

        TreeNode root = new TreeNode(10);
        TreeNode l1 = new TreeNode(5);
        TreeNode r1 = new TreeNode(16);
        root.left = l1;
        root.right = r1;

        TreeNode l2 = new TreeNode(3);
        l1.left = l2;
        l1.right = new TreeNode(8);

        rangeSum(root, 1, 15);
        int sum = rangeSum2(root, 1, 15);
        System.out.println("sum = " + sum);


        getDiameter(root);

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
        List<List<Integer>> levels = new ArrayList<>();
        levelOrder(root, 0, levels);
        return levels;
    }

    public static void levelOrder(TreeNode root, int level, List<List<Integer>> levels) {
        if (root == null) {
            return;
        }
        if (levels.size() == level) {
            levels.add(new ArrayList<Integer>());
        }
        levels.get(level).add(root.val);
        levelOrder(root.left, level + 1, levels);
        levelOrder(root.right, level + 1, levels);
    }

    /**
     * 按 层 遍历输出
     *
     * @param root
     */
    private static void levelTraverse(TreeNode root) {
        if (root == null) {
            return;
        }
        LinkedList<TreeNode> queue = new LinkedList<>();
        queue.offer(root); // 根节点入队
        TreeNode current = null;

        while (!queue.isEmpty()) { // 利用队列的先进先出的特性
            current = queue.poll(); // 出队队列的头部元素
            System.out.print("-->" + current.val);
            if (current.left != null) {
                queue.offer(current.left);
            }
            if (current.right != null) {
                queue.offer(current.right);
            }
        }
        System.out.println();
    }

    /**
     * 翻转 二叉树 ：交换二叉树的左右两个孩子
     *
     * @param node
     */
    private static TreeNode invertTree(TreeNode node) {
        if (node == null) {
            return null;
        }
        LinkedList<TreeNode> queue = new LinkedList<>();
        queue.offer(node);
        while (!queue.isEmpty()) {
            TreeNode tmp = queue.poll();
            TreeNode left = tmp.left;
            tmp.left = tmp.right;
            tmp.right = left;
            if (tmp.left != null) {
                queue.offer(tmp.left);
            }
            if (tmp.right != null) {
                queue.offer(tmp.right);
            }
        }
        return node;
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
        if ((level & 1) == 0) {//偶数
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

    /**
     * 递归获取树的最大高度
     *
     * @param node
     * @return
     */
    public static int getTreeHeightByRecursion(TreeNode node) {
        if (node == null) {
            return 0;
        }
        int left = getTreeHeightByRecursion(node.left);
        int right = getTreeHeightByRecursion(node.right);
        if (left > right) {
            return left + 1;
        } else {
            return right + 1;
        }
    }

    /**
     * 通过迭代求出二叉树的高度
     *
     * @param node
     */
    public static void getHeightByIteration(TreeNode node) {
        //这个就是慢迭代的下标
        int front = -1;
        //这个是记录每一层结尾处的下标
        int last = 0;
        //树的高度
        int height = 0;
        //这个是快迭代的下标
        int rear = -1;
        List<TreeNode> linkList = new ArrayList<>();
        linkList.add(++rear, node);
        TreeNode tmp;
        while (front < rear) {
            tmp = linkList.get(++front);
            if (tmp.left != null) {
                linkList.add(++rear, tmp.left);
            }
            if (tmp.right != null) {
                linkList.add(++rear, tmp.right);
            }
            if (front == last) {
                height++;
                last = rear;
            }

        }
        System.out.println(height);
    }

    /**
     * 求叶子结点的个数
     *
     * @param node
     * @return
     */
    public static int biTreeNodeCount(TreeNode node) {
        if (node != null) {
            int L = biTreeNodeCount(node.left);
            int R = biTreeNodeCount(node.right);
            if (L == 0 && L == 0) {
                return 1;
            } else {
                return L + R;
            }
        } else {
            return 0;
        }
    }


    /**
     * 二叉搜索树的范围和
     * 给定二叉搜索树的根结点 root，返回 L 和 R（含）之间的所有结点的值的和。
     *
     * @param root
     * @param L
     * @param R
     */
    public static void rangeSum(TreeNode root, int L, int R) {
        inOrder(root, L, R);
        System.out.println("..............................................................");
        System.out.println("rangeSum = " + res);
    }

    /**
     * 二叉搜索树的部分和
     * 中序遍历然后比较，
     *
     * @param node
     * @param L
     * @param R
     */
    private static void inOrder(TreeNode node, int L, int R) {
        if (node == null)
            return;
        inOrder(node.left, L, R);
        System.out.println("val = " + node.val + ", res = " + res);
        if (node.val >= L && node.val <= R) {
            res += node.val;
        }
        inOrder(node.right, L, R);
    }

    /**
     * 对于二叉搜索树，左子树 小于根节点 ，右子树大于根节点
     * 我们只需要数组中L&R之间的数值，故如果当前节点小于L，则遍历右子树，若果当前结点大于R，则遍历左子树，其他情况遍历左右子树和当前节点。
     *
     * @param root
     * @param L
     * @param R
     * @return
     */
    private static int rangeSum2(TreeNode root, int L, int R) {
        if (root == null || L > R)
            return 0;

        if (root.val < L) {
            return rangeSum2(root.right, L, R);
        } else if (root.val > R) {
            return rangeSum2(root.left, L, R);
        } else {
            return root.val + rangeSum2(root.left, L, R) + rangeSum2(root.right, L, R);
        }
    }

    /**
     * 给定一棵二叉树，你需要计算它的直径长度。一棵二叉树的直径长度是任意两个结点路径长度中的最大值。这条路径可能穿过根结点。
     *
     *       1
     *     / \
     *    2   3
     *  / \
     * 4   5
     * 返回 3 , 它的长度是路径 [4,2,1,3] 或者 [5,2,1,3]。
     *
     * 注意： 两结点之间的路径长度是以它们之间边的数目表示。
     *
     * @param root
     * @return
     */
    private static int getDiameter(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int left = getHeight(root.left);
        int right = getHeight(root.right);
        System.out.println("diameter = " + (left + right));
        return left + right;
    }

    private static int getHeight(TreeNode node) {
        if (node == null)
            return 0;
        int left = getHeight(node.left);
        int right = getHeight(node.right);

        return left > right ? left + 1 : right + 1;
    }


//--------------------------------------------------------最后的花括号---------------------------------------------------------
}
