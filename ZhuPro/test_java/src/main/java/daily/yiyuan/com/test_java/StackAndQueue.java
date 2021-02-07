package daily.yiyuan.com.test_java;

import java.util.Stack;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2020/3/27
 * <p>
 * Stack : 栈 先进后出
 * Queue : 队列 现进先出
 */
public class StackAndQueue {
    public static void main(String[] args) {

        TwoStack2Queue stack2Queue = new TwoStack2Queue();
        stack2Queue.push(1);
        stack2Queue.push(2);
        stack2Queue.push(3);

        System.out.println(stack2Queue.pop());
        System.out.println(stack2Queue.pop());
        System.out.println(stack2Queue.pop());
    }


}

/**
 * 2个栈 变成一个队列
 */
class TwoStack2Queue {
    private Stack<Integer> s1 = new Stack<>();
    private Stack<Integer> s2 = new Stack<>();

    public void push(int val) {
        s1.push(val);
    }

    public int pop() {
        int val ;
        while (s2.isEmpty()) {
            while (!s1.isEmpty()) {
                s2.push(s1.pop());
            }
        }

        val = s2.pop();
        while (!s2.isEmpty()) {
            s1.push(s2.pop());
        }
        return val;
    }
}
