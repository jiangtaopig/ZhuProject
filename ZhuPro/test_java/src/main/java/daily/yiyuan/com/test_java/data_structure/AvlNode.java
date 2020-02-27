package daily.yiyuan.com.test_java.data_structure;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2020/1/17
 */
public class AvlNode {
    public int data;
    public int depth; //结点深度
    public int balance;//是否平衡
    public AvlNode parent;
    public AvlNode leftChild;
    public AvlNode rightChild;

    public AvlNode(int data){
        this.data = data;
        depth = 1;
        balance = 0;
        leftChild = null;
        rightChild = null;
    }

}
