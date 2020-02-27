package daily.yiyuan.com.test_java.data_structure;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2020/1/16
 * 二叉搜索树 又叫 二叉查找数 二叉排序树
 *
 * 二叉搜索树：左子树的结点数值 小于等于 父节点的数值，右子树的结点数值大于等于 父节点的数值，
 * 所以中序遍历的结果是升序的
 *
 */
public class BinarySearchTreeNode {
    public BinarySearchTreeNode leftChild;
    public BinarySearchTreeNode rightChild;
    public int data;

    public BinarySearchTreeNode(BinarySearchTreeNode left, BinarySearchTreeNode right, int data){
        this.leftChild = left;
        this.rightChild = right;
        this.data = data;
    }
}
