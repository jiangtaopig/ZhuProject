package daily.yiyuan.com.test_java.leet_code.stack;

import java.util.Stack;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2021/1/18
 *
 * 单调栈
 */
public class TestStack {

    public static void main(String[] args) {

        int [] res = nextGeneratorElement(new int[]{2, 1, 5, 4, 3});
        for (int a : res){
            System.out.println("a = " + a);
        }
    }


    /**
     * 给你一个数组，返回一个等长的数组，对应索引存储着下一个更大元素，如果没有更大的元素，就存 -1。
     * <p>
     * 输入一个数组nums = [2,1,2,4,3]，你返回数组[4,2,4,-1,-1]。
     * <p>
     * 解释：第一个 2 后面比 2 大的数是 4; 1 后面比 1 大的数是 2；第二个 2 后面比 2 大的数是 4; 4 后面没有比 4 大的数，填 -1；3 后面没有比 3 大的数，填 -1。
     * https://mp.weixin.qq.com/s?__biz=MzAxODQxMDM0Mw==&mid=2247487704&idx=1&sn=eb9ac24c644aa0950638c9b20384e982&chksm=9bd7eed0aca067c6b4424c40b7f234c815f83edfbb5efc9f51581335f110e9577114a528f3ec&scene=178&cur_album_id=1318896187793260544#rd
     * @param nums
     * @return
     */
    private static int[] nextGeneratorElement(int[] nums) {
        int [] res = new int[nums.length];
        Stack<Integer> stack = new Stack<>();

        // 倒着往栈里放
        for (int len = nums.length, i = len - 1; i >= 0; i--) {
            while (!stack.empty() && stack.peek() <= nums[i]) {
                // 值小的出栈
                stack.pop();
            }
            res[i] = stack.empty() ? -1 : stack.peek();
            stack.push(nums[i]);
        }
        return res;
    }


    /**
     * 给你一个数组 nums ，这个数组存放的是近几天的天气气温，你返回一个等长的数组，
     * 计算：对于每一天，你还要至少等多少天才能等到一个更暖和的气温；如果等不到那一天，填 0。
     *
     * 比如说给你输入T = [73,74,75,71,69,76]，你返回[1,1,3,2,1,0]。
     *
     * 解释：第一天 73 华氏度，第二天 74 华氏度，比 73 大，所以对于第一天，只要等一天就能等到一个更暖和的气温，后面的同理。
     * @param nums
     * @return
     */
    private static int [] dailyTemperatures(int [] nums){
        // 存放索引
        Stack<Integer> s = new Stack<>();
        int len = nums.length;
        int [] res = new int[len];

        for (int i = len - 1; i >= 0; i--){
            while (!s.empty() && s.peek() <= nums[i]){
                s.pop();
            }
            res[i] = s.empty() ? 0 : s.peek();
            // 索引入栈，而不是元素
            s.push(nums[i]);
        }
        return res;
    }

}
