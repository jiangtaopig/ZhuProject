package daily.yiyuan.com.test_java.data_structure;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2020/1/17
 * 平衡二叉树：是一种特殊的搜索二叉树，是左右子树高度相差不超过1的搜索二叉树
 *
 * 特性：
 * 1.可以是空树
 * 2.假如不是空树，任何一个节点的左子树与右子树都是平衡二叉树，并且高度之差的绝对值不超过 1。
 */
public class AvlTree {

    public AvlNode root;

    public void insert(AvlNode root, int data){
        AvlNode node = new AvlNode(data);

        if (data < root.data){
            if (root.leftChild == null){
                root.leftChild = node;
                root.leftChild.parent = root;
            }else {
                insert(root.leftChild, data);
            }
        }


    }
}
