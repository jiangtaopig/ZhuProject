package daily.yiyuan.com.test_java.leet_code;

import org.omg.CORBA.PUBLIC_MEMBER;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2019/7/15
 */
public class MyLinkedList {
    public int size;
    public ListNode r;
    public ListNode headL;

    public MyLinkedList() {
        headL = new ListNode(-1);
        r = headL;
    }

    public int get(int index) {
        if (index < 0 || index > size){
            return -1;
        }else {
            ListNode p = headL.next;
            int cnt = 0;
            while (cnt < index && p != null){
                cnt++;
                p = p.next;
            }
            if (p != null){
                return p.val;
            }else {
                return -1;
            }
        }
    }

    public void addAtHead(int val) {
        addAtIndex(0, val);
    }

    public void addAtTail(int val) {
        addAtIndex(size, val);
    }

    public void addAtIndex(int index, int val) {
        ListNode tmp = new ListNode(val);
        if (index <= 0){
            tmp.next = headL.next;
            headL.next = tmp;
            size++;
        }else {
            ListNode pre = getPreNode(index);
            if (pre != null){
                tmp.next = pre.next;
                pre.next = tmp;
                size++;
            }
        }
    }

    private ListNode getPreNode(int index){
        if (index < 0 || index > size){
            return null;
        }else {
            ListNode p = headL.next;
            int cnt = 0;
            while (cnt < index-1 && p != null){
                cnt++;
                p = p.next;
            }
            return p;
        }
    }

    public void deleteAtIndex(int index) {
        if (index < 0 || index > size - 1){
            return;
        }
        ListNode p = getPreNode(index);
        if (p != null){
            p.next = p.next.next;
        }
    }

    public void traverse(){
        ListNode p = headL.next;
        while (p != null){
            System.out.println("traverse : "+p.val);
            p = p.next;
        }
        System.out.println(".................................................");
    }

    public int size(){
        return size;
    }

}
