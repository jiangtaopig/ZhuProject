package daily.yiyuan.com.test_java.data_structure;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2019/6/21
 */
public class TestMain {
    public static void main(String [] args){
        BinaryTree tree = new BinaryTree();
        BinaryTreeNode root = new BinaryTreeNode();
        root.setData(1);

        BinaryTreeNode leftChild = new BinaryTreeNode();
        leftChild.setData(2);

        BinaryTreeNode rightChild = new BinaryTreeNode();
        rightChild.setData(3);

        root.setLeftChirld(leftChild);
        root.setRightChirld(rightChild);

        tree.setRoot(root);
        tree.postOrder(root);

        int height = tree.getTreeHeight();
        int size = tree.size();
        System.out.println("height = "+height+", size = "+size);
    }
}
