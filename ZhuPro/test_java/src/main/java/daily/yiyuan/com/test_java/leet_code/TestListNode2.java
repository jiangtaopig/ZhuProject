package daily.yiyuan.com.test_java.leet_code;

import static daily.yiyuan.com.test_java.leet_code.TestMain.initListNodeWithoutHead;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2019/10/24
 */
public class TestListNode2 {
    static int flow  = 0;
    public static void main(String[] args) {
//        int[][] nums = {{1, 2, 3}, {4, 5, 6}};
//        matrixReshape(nums, 3, 2);


        int[] a1 = {1, 2, 6, 8};
        int[] a2 = {3, 5, 9};
        ListNode l1 = initListNodeWithoutHead(a1);
        ListNode l2 = initListNodeWithoutHead(a2);
        ListNode l3 = merge2ListNode(l1, l2);

        ListNode l4 = initListNodeWithoutHead(new int[]{1, 2, 3, 4, 5, 6});
//        ListNode l5 = reverseKGroup(l4, 2);

//        mergeKListNodes();

//        oddEvenList(initListNodeWithoutHead(new int[]{1, 2, 3, 4, 5, 6}));

        splitListToParts(initListNodeWithoutHead(new int[]{1, 2, 3, 4}), 5);

        reverseListNode(initListNodeWithoutHead(new int[]{1, 2, 3, 4}));


        addTwoNumbers(initListNodeWithoutHead(new int[]{2, 4, 3}), initListNodeWithoutHead(new int[]{9, 7, 6, 4}));



        ListNode l6 = initListNodeWithoutHead(new int[]{3, 4, 1});
        insertionSortList(l6);
    }

    private static void mergeKListNodes() {
        ListNode l6 = initListNodeWithoutHead(new int[]{1, 4, 9});
        ListNode l7 = initListNodeWithoutHead(new int[]{3, 5});
        ListNode l8 = initListNodeWithoutHead(new int[]{6});
        ListNode[] listNodes = new ListNode[]{l6, l7, l8};
        mergeKLists(listNodes);
    }

    public static void matrixReshape(int[][] nums, int r, int c) {
        int[] a = nums[0];
        if (r * c == nums.length) {
            int row = nums[0].length;
            int coloum = nums.length / row;
        }
    }

    /**
     * 合并2个有序链表的简单方法
     *
     * @param l1
     * @param l2
     * @return
     */
    public static ListNode merge2ListNode(ListNode l1, ListNode l2) {
        ListNode l3 = new ListNode(-1);
        ListNode pre = l3;
        if (l1 == null && l2 == null) {
            return null;
        } else if (l1 == null) {
            return l2;
        } else if (l2 == null) {
            return l1;
        }

        while (l1 != null && l2 != null) {
            if (l1.val <= l2.val) {
                pre.next = l1;
                l1 = l1.next;
            } else {
                pre.next = l2;
                l2 = l2.next;
            }
            pre = pre.next;
        }

        pre.next = l1 == null ? l2 : l1;
        return l3.next;
    }

    /**
     * 给你一个链表，每 k 个节点一组进行翻转，请你返回翻转后的链表。
     * k 是一个正整数，它的值小于或等于链表的长度。
     * 如果节点总数不是 k 的整数倍，那么请将最后剩余的节点保持原有顺序。
     * <p>
     * 示例 :
     * 给定这个链表：1->2->3->4->5
     * 当 k = 2 时，应当返回: 2->1->4->3->5
     * 当 k = 3 时，应当返回: 3->2->1->4->5
     */

    public static ListNode reverseKGroup(ListNode head, int k) {
        ListNode L = new ListNode(-1);
        ListNode start, end; //分别指向要翻转的开始和结束位置
        ListNode r, p = L, tmp;
        start = head;
        end = start;
        r = start;
        tmp = start;
        ListNode tail = L;//执行完一次翻转后指向翻转后的最后一个位置
        int cnt = 1;

        if (k == 1) {
            return head;
        } else {
            while (head != null) {
                if (cnt % k == 0) {
                    for (int i = 0; i < k; i++) {
                        tmp = tmp.next;
                        start.next = p.next;
                        p.next = start;
                        start = tmp;
                        if (i == 0) {
                            tail = p.next;
                        } else if (i == k - 1) {
                            p = tail;
                        }
                    }
                    head = r;
                    start = r;
                    end = r;
                } else {
                    end = end.next;
                    if (end != null) {
                        r = end.next;
                    }
                    head = head.next;
                }
                cnt++;
            }
            if (start != null) {
                tail.next = start;
            }
        }
        return L.next;
    }

    /**
     * 合并K个有序数组
     *
     * @param lists
     * @return
     */
    public static ListNode mergeKLists(ListNode[] lists) {
        int size = lists.length;
        if (size == 0) {
            return null;
        } else if (size == 1) {
            return lists[0];
        }
        int index = size / 2;
        ListNode[] left = new ListNode[index];
        ListNode[] right = new ListNode[index];
        for (int i = 0; i < index; i++) {
            left[i] = lists[i];
            right[i] = lists[index + i];
        }
        ListNode[] res = new ListNode[index];
        if (size % 2 != 0) {//如果数组的大小为奇数，则先把数组的最后一个和left的第一个先合并
            left[0] = merge2ListNode(left[0], lists[size - 1]);
        }
        for (int i = 0; i < index; i++) {//合并left right
            res[i] = merge2ListNode(left[i], right[i]);
        }
        return mergeKLists(res);//递归调用合并后的
    }


    /**
     * 将奇数位的结点和偶数位的结点分别排在一起并保持相对顺序
     * 输入: 2->1->3->5->6->4->7->NULL
     * 输出: 2->3->6->7->1->5->4->NULL
     *
     * @param head
     * @return 解题思路： 就是定义2个 奇、偶 链表分别将奇数和偶数结点 链接上去，最后合并这2个链表
     */
    public static ListNode oddEvenList(ListNode head) {
        ListNode odd = new ListNode(-1);
        ListNode p = odd;
        ListNode even = new ListNode(-1);
        ListNode r = even;
        int cnt = 1;
        while (head != null) {
            if (cnt % 2 == 0) {
                r.next = head;
                r = head;
            } else {
                p.next = head;
                p = head;
            }
            cnt++;
            head = head.next;
            r.next = null;
            p.next = null;
        }
        p.next = even.next;
        return odd.next;
    }


    public static ListNode[] splitListToParts(ListNode root, int k) {
        if (root == null) {
            return null;
        }
        ListNode p = root;
        ListNode r = root;

        ListNode[] res = new ListNode[k];
        int size = 0;
        while (p != null) {
            size++;
            p = p.next;
        }

        int count = size / k;//每组都有的固定个数
        int reminder = size % k; //每组固定个数后还有遗留的，按照遗留的个数分配给 res 数组；
        if (count == 0 || count == 1) {
            for (int i = 0; i < size; i++) {
                res[i] = root;
                if (root != null) {
                    r = root.next;
                }
                root.next = null;
                root = r;
            }

            if (size < k) {
                for (int i = size; i < k; i++) {
                    res[i] = null;
                }
            }

        } else {
            for (int i = 0; i < k; i++) {
                res[i] = r;
                for (int j = 0; j < count + (reminder > 0 ? 1 : 0) - 1; j++) {
                    root = root.next;
                    if (root != null) {
                        r = root.next;
                    }
                }
                root.next = null;
                root = r;
                reminder--;
            }
        }

        return res;
    }


    /**
     * 给定两个非空链表来代表两个非负整数。数字最高位位于链表开始位置。它们的每个节点只存储单个数字。将这两数相加会返回一个新的链表。
     * 输入: (7 -> 2 -> 4 -> 3) + (5 -> 6 -> 4)
     * 输出: 7 -> 8 -> 0 -> 7
     */


    public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        if (l1 == null) return l2;
        if (l2 == null) return l1;
        ListNode res1 = l1, res2 = l2;
        int len1 = 0, len2 = 0;
        while (l1 != null) {
            len1++;
            l1 = l1.next;
        }
        while (l2 != null) {
            len2++;
            l2 = l2.next;
        }
        ListNode res = len1 >= len2 ? add(res1, res2, len1, len2) : add(res2, res1, len2, len1);//保证add函数的参数1的链表长度大于等于参数2的长度
        if (flow == 1) {
            res1 = new ListNode(1);
            res1.next = res;
            return res1;
        }

        return res;

    }


    public static ListNode add(ListNode l1, ListNode l2, int len1, int len2) {
        int temp;
        if ((len1 == 1) && (len2 == 1)) {
            temp = l1.val;
            l1.val = (l1.val + l2.val) % 10;
            flow = (temp + l2.val) / 10;
            return l1;
        }
        if (len1 > len2) {
            temp = l1.val;
            l1.next = add(l1.next, l2, len1 - 1, len2);
            l1.val = (temp + flow) % 10;
            flow = (temp + flow) / 10;
            return l1;
        }
        l1.next = add(l1.next, l2.next, len1 - 1, len2 - 1);
        temp = l1.val;
        l1.val = (temp + flow + l2.val) % 10;
        flow = (temp + flow + l2.val) / 10;
        return l1;

    }


    /**
     * 翻转链表
     *
     * @param head
     * @return
     */
    public static ListNode reverseListNode(ListNode head) {
        if (head == null) {
            return null;
        }

        ListNode L = new ListNode(-1);
        ListNode r = L;
        ListNode pre = head;

        while (head != null) {
            head = head.next;
            pre.next = r.next;
            r.next = pre;
            pre = head;
        }

        return L.next;
    }

    public static ListNode insertionSortList(ListNode head) {
        ListNode L, p, t, r, preT = null;
        if (head == null){
            return head;
        }

        L = head;
        head = head.next;
        p = head;
        t = L;
        L.next = null;

        while (p != null){
            r = p.next;
            while (t != null){
                if (p.val > t.val){
                    preT = t;
                    t = t.next;
                }else {
                    p.next = t;
                    if (preT != null){
                        preT.next = p;
                    }else {
                        L = p;
                    }
                    break;
                }
            }
            if (t == null && preT != null){
                preT.next = p;
                p.next = null;
            }
            preT = null;
            t = L;
            p = r;
        }
        return L;
    }

}
