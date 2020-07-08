package daily.yiyuan.com.test_java.leet_code;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2020/7/3
 */
public class MyTest {
    public static void main(String[] args) {
        ListNode node = InitListNodeUtils.initListNodeWithoutHead(new int[]{1, 2, 3, 4, 5, 6, 7});
        reverseNodeWithNum(node, 3);
    }

    /**
     * 每n个结点作一次翻转
     * 1-2-3-4-5-6-7 当n=3 ： 3-2-1-6-5-4-7
     */
    private static ListNode reverseNodeWithNum(ListNode head, int n) {
        ListNode L = new ListNode(-1);
        ListNode r = L;
        ListNode p = head;
        ListNode q = head;
        ListNode tail = head; // 指向每轮开始的第一个结点
        int cnt = 0;

        while (q != null && cnt < n) {
            q = q.next;
            cnt++;
            if (cnt == n) {
                while (p != q) {
                    p = p.next;
                    head.next = r.next;
                    r.next = head;
                    head = p;
                    cnt--;
                }
                r = tail; // 指向一轮逆序后的最后一个结点
                tail = p;
            }
        }
        if (p != null){
            r.next = p;
        }
        return L.next;
    }
}
