package daily.yiyuan.com.test_java.data_structure;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2019/6/21
 * 二叉树的操作，创建、添加、清空、遍历
 *
 *
 * 搜索二叉树是一种特殊的二叉树：他的左子树的结点数据一定小于他的父结点数值，右子树的结点数值一定大于父节点的数值；
 * 中序遍历就是排好序的
 */
public class BinaryTree {
    private BinaryTreeNode root;

    public BinaryTree(){

    }

    public BinaryTree(BinaryTreeNode root){
        this.root = root;
    }

    public BinaryTreeNode getRoot() {
        return root;
    }

    public void setRoot(BinaryTreeNode root) {
        this.root = root;
    }

    //判断二叉树是否为空
    public boolean isEmpty(){
        return root == null;
    }

    /**
     * 二叉树的清空：
     * 首先提供一个清空以某个节点为根节点的子树的方法，既递归地删除每个节点；
     * 接着提供一个删除树的方法，直接通过第一种方法删除到根节点即可
     */
    public void clear(){
        clearTreeNode(root);
    }

    public void clearTreeNode(BinaryTreeNode node){
        if (node != null){
            clearTreeNode(node.getLeftChirld());
            clearTreeNode(node.getRightChirld());
            node = null;//删除结点
        }
    }

    /**
     * 求二叉树的高度：
     * 首先要一种获取以某个节点为子树的高度的方法，使用递归调用。
     * 如果一个节点为空，那么这个节点肯定是一颗空树，高度为0；
     * 如果不为空，那么我们要遍历地比较它的左子树高度和右子树高度，
     *     高的一个为这个子树的最大高度，然后加上自己本身的高度就是了
     * 获取二叉树的高度，只需要调用第一种方法，即传入根节点
     */
    public int getTreeHeight(){
        return height(root);
    }

    public int height(BinaryTreeNode node){
        if (node == null){
            return 0;
        }else {
            int left = height(node.getLeftChirld());
            int right = height(node.getRightChirld());
            //高度应该算更高的一边，（+1是因为要算上自身这一层）
            return left >= right ? (left + 1) : (right + 1);
        }
    }

    public int size(){
        return size(root);
    }

    public int size(BinaryTreeNode node){
        if (node == null){
            return 0;
        }
        //计算本节点 所以要+1
        return  1 + size(node.getLeftChirld()) + size(node.getRightChirld());
    }

    //node节点在subTree子树中的父节点
    public BinaryTreeNode getParent(BinaryTreeNode subTree,BinaryTreeNode node){
        if(subTree==null){
            return null;   //如果是空子树，则没有父节点
        }
        if(subTree.getLeftChirld()==node || subTree.getRightChirld() == node){
            return subTree;   //如果子树的根节点的左右孩子之一是待查节点，则返回子树的根节点
        }
        BinaryTreeNode parent = null;
        if(getParent(subTree.getLeftChirld(),node)!=null){
            parent = getParent(subTree.getLeftChirld(),node);
            return parent;
        }else{
            //递归左右子树
            return getParent(subTree.getRightChirld(),node);
        }
    }

    //查找node节点在二叉树中的父节点
    public BinaryTreeNode getParent(BinaryTreeNode node){
        return (root==null||root==node)? null:getParent(root,node);
    }

    //获取某个节点的左子树
    public BinaryTreeNode getLeftTree(BinaryTreeNode node){
        return node.getLeftChirld();
    }

    //获取某个节点的右子树
    public BinaryTreeNode getRightTree(BinaryTreeNode node){
        return node.getRightChirld();
    }
    //给某个节点插入左节点
    public void insertLeft(BinaryTreeNode parent,BinaryTreeNode newnode){
        parent.setLeftChirld(newnode);
    }
    //给某个节点插入右节点
    public void insertRitht(BinaryTreeNode parent,BinaryTreeNode newnode){
        parent.setRightChirld(newnode);
    }

    //先序遍历 :
    public void preOrder(BinaryTreeNode node){
        if(node!=null){
            System.out.println(node.getData()); //先访问根节点
            preOrder(node.getLeftChirld());  //先根遍历左子树
            preOrder(node.getRightChirld());  //先根遍历右子树
        }
    }

    //中序遍历
    public void centerOrder(BinaryTreeNode node){
        if (node != null){
            centerOrder(node.getLeftChirld());//中根遍历左子树
            System.out.println(node.getData());//访问根节点
            centerOrder(node.getRightChirld());//中根遍历右子树
        }
    }

    //后序遍历
    public void postOrder(BinaryTreeNode node){
        if (node != null){
            postOrder(node.getLeftChirld()); //后序遍历左子树
            postOrder(node.getRightChirld());//后序遍历右子树
            System.out.println(node.getData());//访问根结点
        }
    }

}
