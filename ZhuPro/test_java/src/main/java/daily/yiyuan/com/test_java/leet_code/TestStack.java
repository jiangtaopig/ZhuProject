package daily.yiyuan.com.test_java.leet_code;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2019/12/24
 * <p>
 * 栈 先进后出 Stack 继承自 Vector 是同步的
 */
public class TestStack {
    public static void main(String[] args) {
        Stack<String> stack = new Stack<>();
        stack.push("1");
        stack.push("2");

        while (stack.size() > 0) {
            String val = stack.pop();
            System.out.println("stack : " + val);
        }

        Queue<String> queue = new ArrayDeque<>();
        queue.add("a");
        queue.add("b");

        while (queue.size() > 0) {
            System.out.println("queue : " + queue.poll());
        }

        isValid("([}]");

        evalRPN(new String[]{"2", "1", "+", "3", "*"});

        trap6(new int[]{1, 0, 2, 0, 1, 2});

    }

    /**
     *  有效的括号 只有这3种， (), []. {}
     *  s = "()[]" ， "([])", "(){[]}" 返回true
     * @param s
     * @return
     *
     * 解题思路就是利用栈来解决的， 栈 是先进后出；
     * 从字符串的第2位开始比较，如果匹配就出栈，否则入栈，最后看栈的大小是否为0， 0 返回true
     */
    public static boolean isValid(String s) {
        if (s == null || s.length() % 2 != 0){
            return false;
        }

        if (s.length() == 0){
            return true;
        }

        Map<Character, Character> map = new HashMap<>();
        map.put('(', ')');
        map.put('[', ']');
        map.put('{', '}');

        int size = s.length();
        Stack<Character> stack = new Stack<>();
        stack.add(s.charAt(0));

        for (int i = 1; i < size; i++){
            Character character = s.charAt(i);
            if (stack.size() > 0 && map.get(stack.peek()) != null && map.get(stack.peek()).equals(character)){
                stack.pop();
            }else {
                stack.push(character);
            }
        }
     return stack.isEmpty();
    }


    public static int evalRPN(String[] tokens) {
        if (tokens == null){
            return -1;
        }
        int size = tokens.length;
        List<String> list = new ArrayList<>();
        list.add("+");
        list.add("-");
        list.add("*");
        list.add("/");

        Stack<String> stack = new Stack<>();
        String tmp = "";
        int v1 ;
        int v2;
        int res = 0;
        for (int i = 0; i < size; i++){
            tmp = tokens[i];
            if (list.contains(tmp)){//如果是运算符，那么 将栈中的2个操作数出栈，
                if (stack.size() > 1){
                    v1 = Integer.valueOf(stack.pop());
                    v2 = Integer.valueOf(stack.pop());
                    switch (tmp){
                        case "+":
                            res = v2 + v1;
                            break;
                        case "-":
                            res = v2 - v1;
                            break;
                        case "*":
                            res = v2 * v1;
                            break;
                        case "/":
                            res = v2 / v1;
                            break;
                    }
                    stack.add(String.valueOf(res));
                }
            }else {
                stack.add(tmp);
            }
        }
        return Integer.valueOf(stack.peek());
    }

    public static int trap6(int[] height) {
        int sum = 0;
        Stack<Integer> stack = new Stack<>();
        int current = 0;
        while (current < height.length) {
            //如果栈不空并且当前指向的高度大于栈顶高度就一直循环
            while (!stack.empty() && height[current] > height[stack.peek()]) {
                int h = height[stack.peek()]; //取出要出栈的元素
                stack.pop(); //出栈
                if (stack.empty()) { // 栈空就出去
                    break;
                }
                int distance = current - stack.peek() - 1; //两堵墙之前的距离。
                int min = Math.min(height[stack.peek()], height[current]);
                sum = sum + distance * (min - h);
            }
            stack.push(current); //当前指向的墙入栈
            current++; //指针后移
        }
        return sum;
    }


}
