package daily.yiyuan.com.test_java.leet_code;

import java.util.List;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2021/1/29
 */
class TestListNode210109 {

    public static void main(String[] args) {

        int [] arr = new int [] {1, 2, 3, 4, 5, 6, 7};

        ListNode root = InitListNodeUtils.initListNodeWithoutHead(arr);
//        root = reverseNodeList(root);
//        root = reverseBySpecifyNum(root, 2);



        ListNode L1 = InitListNodeUtils.initListNodeWithoutHead(new int[]{1, 3, 8, 9});
        ListNode L2 = InitListNodeUtils.initListNodeWithoutHead(new int[]{2, 3, 4});
        ListNode res = merge2SortedListNode(L1, L2);

        System.out.println("----");
    }

    public static ListNode reverseNodeList(ListNode head){
        ListNode L = new ListNode(-1);
        ListNode r = L;
        ListNode p = head;
        while (p != null){
            head = head.next;
            p.next = r.next;
            r.next = p;
            p = head;
        }
        return L.next;
    }

    /**
     * 链表每隔 num 翻转一次
     * @param root
     * @param num
     * @return
     */
    private static ListNode reverseBySpecifyNum(ListNode root, int num) {
        ListNode L = new ListNode(-1);
        ListNode r = L;
        ListNode p = root;
        ListNode q = root;
        ListNode tail = root;
        int cnt = 0;

        while (p != null && cnt < num) {
            p = p.next;
            cnt++;
            if (cnt == num) {
                while (q != p) {
                    cnt--;
                    q = q.next;
                    root.next = r.next;
                    r.next = root;
                    root = q;
                }
                r = tail;
                tail = p;
            }
        }
        if (tail != null)
            r.next = tail;
        return L.next;
    }


    /**
     * 没K个结点翻转一次，但是是从尾结点开始
     *  步骤1 ： 将链表总体翻转
     *  步骤2： 链表K个元素翻转
     *  步骤3： 再次将链表总体翻转
     * @param head
     * @param K
     * @return
     */
    public static ListNode revserseKGroupPlus(ListNode head, int K){
        head = reverseNodeList(head);
        head = reverseBySpecifyNum(head, K);
        head = reverseNodeList(head);
        return head;
    }


    /**
     * 合并2个有序链表
     * @param L1
     * @param L2
     * @return
     */
    public static ListNode merge2SortedListNode(ListNode L1, ListNode L2){
        if (L1 == null && L2 == null)
            return null;
        if (L1 == null)
            return L2;
        if (L2 == null)
            return L1;

        ListNode L = new ListNode(-1);
        ListNode pre = L;
        while (L1 != null && L2 != null){
            if (L1.val < L2.val){
                pre.next = L1;
                pre = L1;
                L1 = L1.next;
            }else {
                pre.next = L2;
                pre = L2;
                L2 = L2.next;
            }
        }
        if (L1 != null)
            pre.next = L1;
        if (L2 != null)
            pre.next = L2;
        return L.next;
    }









}
