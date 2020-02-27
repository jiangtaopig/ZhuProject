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


        //-----------------------------------------二叉搜索树-----------------------------------------------------------
        System.out.println("-------------------------------------------------------------------");

        BinarySearchTree binarySearchTree = new BinarySearchTree();
        binarySearchTree.insert(18);
        binarySearchTree.insert(16);
        binarySearchTree.insert(23);
        binarySearchTree.insert(20);
        binarySearchTree.insert(26);
        binarySearchTree.insert(24);
        binarySearchTree.insert(28);

        System.out.println("中序遍历：");
        binarySearchTree.inOrder(binarySearchTree.root);
        System.out.println("先序遍历：");
        binarySearchTree.preOrder(binarySearchTree.root);
        System.out.println("后续遍历:");
        binarySearchTree.postOrder(binarySearchTree.root);

        System.out.println("最大值:"+binarySearchTree.getMaxValue()+", 最小值: "+binarySearchTree.getMinValue());

        BinarySearchTreeNode node = binarySearchTree.find(10);
        if (node != null){
            System.out.println("找到该值为: "+node.data);
        }else {
            System.out.println("找不到该结点");
        }

        System.out.println("删除结点23");
        binarySearchTree.delete(23);

        System.out.println("中序遍历：");
        binarySearchTree.inOrder(binarySearchTree.root);

        System.out.println("删除根结点: ");
        binarySearchTree.delete(18);

        System.out.println("删除后中序遍历");
        binarySearchTree.inOrder(binarySearchTree.root);
    }
}
