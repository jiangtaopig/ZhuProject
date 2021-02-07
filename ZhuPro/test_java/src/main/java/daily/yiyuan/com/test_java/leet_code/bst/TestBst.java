package daily.yiyuan.com.test_java.leet_code.bst;

import daily.yiyuan.com.test_java.leet_code.TreeNode;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2021/1/15
 * BST Binary Search Tree 二叉搜索树：
 * 1、对于 BST 的每一个节点node，左子树节点的值都比node的值要小，右子树节点的值都比node的值大。
 * 2、对于 BST 的每一个节点node，它的左侧子树和右侧子树都是 BST。
 */
public class TestBst {
    public static void main(String[] args) {
        TreeNode root = getBst();
//        inOrderTraversal(root);
//        inOrderDecent(root);
//        TreeNode sumTree = convertBST(root);
//        preOrder(sumTree);
//        TreeNode node = insert(root, 6);



//        inOrderTraversal(node);

//        System.out.println(" is in bst " + isInBst(root, 8));

        TreeNode node = createBST(new int[]{4, 2, 7, 6, 10});
        TreeNode deleteRoot = deleteNode(node, 7);
        inOrderTraversal(deleteRoot);
    }

    private static TreeNode getBst() {
        TreeNode root = new TreeNode(4);
        TreeNode left = new TreeNode(2);
        TreeNode right = new TreeNode(7);
        root.left = left;
        root.right = right;
        right.left = new TreeNode(6);
        return root;
    }

    /**
     * 先序遍历
     *
     * @param root
     */
    private static void preOrder(TreeNode root) {
        if (root == null)
            return;
        System.out.println("preOrder = " + root.val);
        preOrder(root.left);
        preOrder(root.right);
    }

    /**
     * 二叉搜索树的中序遍历是有序的
     * 升序
     *
     * @param root
     */
    private static void inOrderTraversal(TreeNode root) {
        if (root == null)
            return;
        inOrderTraversal(root.left);
        System.out.println("val = " + root.val);
        inOrderTraversal(root.right);
    }

    /**
     * 二叉搜索树中序遍历
     * 降序
     *
     * @param root
     */
    private static void inOrderDecent(TreeNode root) {
        if (root == null)
            return;
        inOrderDecent(root.right);
        System.out.println("desent val = " + root.val);
        inOrderDecent(root.left);
    }


    /**
     * 把二叉搜索树 转为 二叉累加树
     *      7                    16
     *    /  \       转换后     /   \
     *  4     9              20     9
     * <p>
     * 比 结点 4 大的节点有 7， 9加上本身 4，所以累加树上这个结点的值 等于 7+9+4 = 20
     *
     * @param root
     * @return 仍然利用 中序遍历；但是 得降序
     */
    static int sum = 0;
    private static TreeNode convertBST(TreeNode root) {
        if (root == null)
            return root;
        convertBST(root.right);
        sum += root.val;
        root.val = sum;
        convertBST(root.left);
        return root;
    }

    /**
     * 插入元素
     * @param root
     * @param val
     * @return
     */
    private static TreeNode insert(TreeNode root, int val) {
        if (root == null)
            return new TreeNode(val);
        // 基本不会这么干的
//        if (root.val == val)
        if (root.val < val)
            root.right = insert(root.right, val);
        if (root.val > val)
            root.left = insert(root.left, val);
        return root;
    }

    /**
     * 查找元素
     * @param root
     * @param target
     * @return
     */
    private static boolean isInBst(TreeNode root, int target){
        if (root == null)
            return false;
        if (target == root.val)
            return true;
        if (target > root.val)
            return isInBst(root.right, target);
        else
            return isInBst(root.left, target);
    }

    /**
     * e二叉树的创建
     * @param arr
     * @return
     */
    private static TreeNode createBST(int [] arr){
        if (arr.length == 0)
            return null;
        TreeNode root = new TreeNode(arr[0]);
        for (int i = 1, len = arr.length; i < len; i++){
            insert(root, arr[i]);
        }
        return root;
    }

    /**
     * 是否是合法的 BST
     * @param root
     * @return
     */
    private static boolean isValidBst(TreeNode root){
        return isValidBst(root, null, null);
    }

    private static boolean isValidBst(TreeNode root, TreeNode min, TreeNode max){
        if (root == null)
            return true;
        if (min != null && min.val > root.val)
            return false;
        if (max != null && max.val < root.val)
            return false;
        // 限定左子树的最大值 是 root.val ，右子树的最小值是 root.val
        return isValidBst(root.left, min, root) && isValidBst(root.right, root, max);
    }


    /**
     * 删除结点
     * @param root
     * @param target
     * @return
     */
    private static TreeNode deleteNode(TreeNode root, int target){
        if (root == null)
            return null;
        if (root.val == target){ // 找到目标，删除它
            // 情况1 ： 删除的是叶子结点，即它没有左右子树
            if (root.left == null && root.right == null)
                return null;
            // 情况2：删除的结点有一个子节点
            if (root.left == null) return root.right;
            if (root.right == null) return root.left;

            // 情况3： 待删除的结点有2个子节点，那么必须找到待删除结点 左子树的最大值 或者 右子树 的最小值来 替代自己
            TreeNode rightMin = getMinNode(root.right); // 获取右子树的最小结点
            root.val = rightMin.val;
            // 删除右子树中那个最小的结点
            root.right = deleteNode(root.right, rightMin.val);
        }
        if (target > root.val)
           root.right = deleteNode(root.right, target);
        if (target < root.val)
            root.left = deleteNode(root.left, target);
        return root;
    }

    private static TreeNode getMinNode(TreeNode root){
        while (root.left != null)
            root = root.left;
        return root;
    }



}
