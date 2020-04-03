package daily.yiyuan.com.test_java.leet_code;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2020/3/30
 */
public class InitListNodeUtils {
    private InitListNodeUtils(){

    }

    /**
     * 初始化有头结点的链表
     *
     * @param nums
     * @return
     */
    public static ListNode initListNodeWithHead(int[] nums) {
        ListNode head = new ListNode(0);
        head.next = null;

        ListNode r = head;
        for (int i = 0; i < nums.length; i++) {
            ListNode p = new ListNode(nums[i]);

            p.next = r.next;
            r.next = p;
            r = p;
        }
        return head;
    }

    /**
     * 初始化无头结点的链表
     *
     * @param nums
     * @return
     */
    public static ListNode initListNodeWithoutHead(int[] nums) {
        ListNode head, r;
        head = new ListNode(nums[0]);
        r = head;
        for (int i = 1; i < nums.length; i++) {
            ListNode p = new ListNode(nums[i]);
            r.next = p;
            r = p;
        }
        return head;
    }
}
