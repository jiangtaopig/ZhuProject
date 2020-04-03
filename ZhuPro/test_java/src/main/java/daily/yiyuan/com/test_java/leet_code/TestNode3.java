package daily.yiyuan.com.test_java.leet_code;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2020/3/30
 */
public class TestNode3 {

    public static void main(String[] args) {

        ListNode node = InitListNodeUtils.initListNodeWithoutHead(new int[]{1, 2, 3, 4, 5, 6, 7, 8});
        node = reverseListNodeWithNum(node, 4);
//        node = reverseListNode(node);
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
     * 每 n 个结点
     * @param node
     * @param n
     * @return
     */
    public static ListNode reverseListNodeWithNum(ListNode node, int n){
        ListNode L = new ListNode(-1);
        ListNode r = L;
        ListNode p = node;
        ListNode q = p;
        ListNode tail = node;
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
                tail = p;
            }
        }
        if (p != null){
            r.next = p;
        }
       return L.next;
    }
}
