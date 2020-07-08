package daily.yiyuan.com.test_java.leet_code;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2020/3/30
 */
public class TestNode3 {

    public static void main(String[] args) {

        ListNode node = InitListNodeUtils.initListNodeWithoutHead(new int[]{1, 2, 3, 4, 5, 6, 7});
//        node = reverseListNodeWithNum(node, 4);
//        node = reverseListNode(node);
        resolve(node, 3);
        int size = 0;
    }

    /**
     * 倒置单链表
     * @param node
     * @return
     */
    public static ListNode reverseListNode(ListNode node){
        if (node == null){
            return null;
        }
        ListNode L = new ListNode(-1);
        ListNode r = L;
        ListNode p = node;
        while (p != null){
            p = p.next;
            node.next = r.next;
            r.next = node;
            node = p;
        }
        return L.next;
    }

    /**
     * 每 n 个结点逆序一次
     * @param node
     * @param n
     * @return
     */
    public static ListNode reverseListNodeWithNum(ListNode node, int n){
        ListNode L = new ListNode(-1);
        ListNode r = L;
        ListNode p = node;
        ListNode q = p;
        ListNode tail = node; //tail 指向每轮的第一个结点
        int cnt = 0;
        while (q != null && cnt < n){
            q = q.next;
            cnt++;
            if (cnt == n){
                while (p != q){
                    p = p.next;
                    node.next = r.next;
                    r.next = node;
                    node = p;
                    cnt--;
                }
                r = tail;
                tail = p;//tail 指向每轮的第一个结点
            }
        }
        if (p != null){
            r.next = p;
        }
       return L.next;
    }

    /**
     * 给定一个单链表的头节点 head,实现一个调整单链表的函数，
     * 使得每K个节点之间为一组进行逆序，并且从链表的尾部开始，头部剩余节点数量不够一组的不需要逆序。（不能使用队列或者栈作为辅助）
     * @param node
     * @param K
     *
     * 1 2 3 4 5 6 7, K=3
     * 解法;
     *  先逆序： 7 6 5 4 3 2 1
     *  然后K个逆序： 5 6 7 2 3 4 1
     *  再逆序一次： 1 4 3 2 7 6 5
     */
    public static ListNode resolve(ListNode node, int K){
        node = reverseListNode(node);
        node = reverseListNodeWithNum(node, K);
        node = reverseListNode(node);
        return node;
    }

}
