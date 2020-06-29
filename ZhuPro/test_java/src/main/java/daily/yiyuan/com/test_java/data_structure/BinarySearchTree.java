package daily.yiyuan.com.test_java.data_structure;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2020/1/16
 */
public class BinarySearchTree {
    public BinarySearchTreeNode root;

    public void insert(int data){
        BinarySearchTreeNode node = new BinarySearchTreeNode(null, null, data);
        if (root == null){
            root = node;
        }else {
            BinarySearchTreeNode p = root;
            while (p != null){
                if (data < p.data){
                    if (p.leftChild == null){
                        p.leftChild = node;
                        return;
                    }
                    p = p.leftChild;
                }else {
                    if (p.rightChild == null){
                        p.rightChild = node;
                        return;
                    }
                    p = p.rightChild;
                }
            }
        }
    }

    /**
     * 中序遍历，遍历的结果是升序的
     * @param root
     */
    public void inOrder(BinarySearchTreeNode root){
        if (root != null){
            inOrder(root.leftChild);
            System.out.println(root.data);
            inOrder(root.rightChild);
        }
    }

    /**
     * 先序遍历
     * @param root
     */
    public void preOrder(BinarySearchTreeNode root){
        if (root != null){
            System.out.println(root.data);
            preOrder(root.leftChild);
            preOrder(root.rightChild);
        }
    }

    /**
     * 后续遍历
     * @param root
     */
    public void postOrder(BinarySearchTreeNode root){
        if (root != null){
            postOrder(root.leftChild);
            postOrder(root.rightChild);
            System.out.println(root.data);
        }
    }

    /**
     * 获取最大值
     * 最大值肯定是在右子树上
     * @return
     */
    public int getMaxValue(){
        if (root == null){
            return -1;
        }
        BinarySearchTreeNode currentNode = root;
        while (currentNode.rightChild != null){
            currentNode = currentNode.rightChild;
        }
        return currentNode.data;
    }

    public int getMinValue(){
        if (root == null){
            return -1;
        }
        BinarySearchTreeNode currentNode = root;
        while (currentNode.leftChild != null){
            currentNode = currentNode.leftChild;
        }
        return currentNode.data;
    }

    /**
     * 查找特定的值 找到返回 该结点 ，否则返回null
     * @param target
     * @return
     */
    public BinarySearchTreeNode find(int target){
        BinarySearchTreeNode currentNode = root;
        while (currentNode != null){
            if (target > currentNode.data){
                currentNode = currentNode.rightChild;
            }else if (target < currentNode.data){
                currentNode = currentNode.leftChild;
            }else {
                return currentNode;
            }
        }
        return null;
    }

    public void delete(int target){
        BinarySearchTreeNode p = root;// p指向删除的节点，初始化为根节点
        BinarySearchTreeNode pp = null;// pp 记录 p 的父节点

        while (p != null && p.data != target){
            pp = p;
            if (target > p.data){
                p = p.rightChild;
            }else {
                p = p.leftChild;
            }
        }

        if (p == null){ //表示未找到
            return;
        }

        if (p.leftChild != null && p.rightChild != null){//要删除的结点有2个子节点，那么就去找到右子树中的最小节点, 用这个结点的值替换待删除结点的值，然后删除这个最小结点
            BinarySearchTreeNode minP = p.rightChild;
            BinarySearchTreeNode minPP = p; // minPP 表示 minP 的父节点

            while (minP.leftChild != null){
                minPP = minP;
                minP = minP.leftChild;
            }

            p.data = minP.data;// 将右子树的最小节点的值 替换到待删除的结点的值，因为要满足 父节点的值大于等于左子树以及小于等于右子树的所有结点值
            p = minP;
            pp = minPP;
        }

        //删除结点是 叶子结点 或者 只有一个子节点
        BinarySearchTreeNode node = null;
        if (p.leftChild != null){
            node = p.leftChild;
        }else if (p.rightChild != null){
            node = p.rightChild;
        }

        if (pp.leftChild == p){
            pp.leftChild = node;
        }else if (pp.rightChild == p){
            pp.rightChild = node;
        }else if (pp == null){// 删除的是根节点 且 根节点最多只有一个结点的情况
         p = node;
        }
    }


}
