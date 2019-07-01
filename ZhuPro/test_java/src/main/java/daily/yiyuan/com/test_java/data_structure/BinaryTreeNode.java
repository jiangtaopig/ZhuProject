package daily.yiyuan.com.test_java.data_structure;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2019/6/21
 * 二叉树的结点定义：包括数值，左子树和右子树
 */
public class BinaryTreeNode {
    private int data; //数值
    private BinaryTreeNode leftChild;  //左子树结点
    private BinaryTreeNode rightChild; //右子树结点

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }

    public BinaryTreeNode getLeftChirld() {
        return leftChild;
    }

    public void setLeftChirld(BinaryTreeNode leftChirld) {
        this.leftChild = leftChirld;
    }

    public BinaryTreeNode getRightChirld() {
        return rightChild;
    }

    public void setRightChirld(BinaryTreeNode rightChirld) {
        this.rightChild = rightChirld;
    }
}
