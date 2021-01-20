package daily.yiyuan.com.test_java.algoritham;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2021/1/14
 * Least Frequency Use // 最不频繁使用
 * 主要利用双向链表，链表的结点包含 freq 元素;
 */
public class LFUCache {
    private LFUNode head;
    private LFUNode tail;
    private int size;

    public void add(String val) {
        final LFUNode l = tail;
        final LFUNode e = new LFUNode(l, null, val, 1);
        tail = e;
        if (head == null)
            head = e;
        else
            l.next = e;
        size++;
    }


    protected boolean removeEldestEntry() {
        return false;
    }
}

class LFUNode {
    public LFUNode pre;
    public LFUNode next;
    public String value;
    public int freq;

    public LFUNode(LFUNode pre, LFUNode next, String val, int freq) {
        this.pre = pre;
        this.next = next;
        this.value = val;
        this.freq = freq;
    }
}