package daily.yiyuan.com.test_java.leet_code;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2019/10/29
 */
public class TestListNode {
    public static void main(String[] args) {
        int [] arr = {1, 2, 3, 4, 5};
        ListNode head = createCircleNode(arr, 3);
        int index = findCircleNodeBegain(head);
        System.out.println("index = "+index);
    }

    private static ListNode createCircleNode(int [] nums, int circleStartIndex){
        ListNode head = new ListNode(nums[0]);
        ListNode r = head;
        ListNode circleHead = null;
        for (int i = 1; i<nums.length; i++){
            ListNode listNode = new ListNode(nums[i]);
            r.next = listNode;
            r = listNode;
            if (i == circleStartIndex -1){
                circleHead = listNode;
            }
            if (i == nums.length -1){
                r.next = circleHead;
            }
        }
        return head;
    }

    /**
     * ...............................................34.找到链表中环形链表的起点............................................................
     * a->b->c->d->e 然后e指向的是c，那么环形链表的起始位置就是2 即结点c
     *
     */
    public static int findCircleNodeBegain(ListNode head){
        int index = 0;
        if (head == null || head.next == null){
            return index;
        }
        //步骤一，设置快慢指针都指向链表开头，快指针每次走2步，慢指针每次走一步，直到快慢指针相遇，
        ListNode slow = head;
        ListNode fast = head;
        int cnt = 0;
        while (head.next.next != null){
            fast = fast.next.next;
            slow = slow.next;
            if (fast == slow){//直到快慢指针相遇
                //步骤二：快指针不动，慢指针每次走一步，直到和快指针再次相遇，记录下走了多少步(也就是环形链表的长度)
                cnt++;
                slow = slow.next;
                while (slow != fast){
                    slow = slow.next;
                    cnt++;
                }
                //步骤三：让快慢指针再次指向链表开头，快指针先走环形链表长度cnt步，然后快慢指针每次都走一步，相遇的结点就是环形链表的起始点
                slow = fast = head;
                while (cnt > 0){
                    fast = fast.next;
                    cnt--;
                }
                while (slow != fast){
                    slow = slow.next;
                    fast = fast.next;
                    index++;
                }
                break;
            }
        }
        return index;
    }
}
