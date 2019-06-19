package daily.yiyuan.com.test_java.leet_code;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import sun.misc.LRUCache;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2019/6/17
 */
public class TestMain {
    public static void main(String[] args) {
        int[] nums = {2, 7, 4, 9};
        int[] nums2 = {9, 7, 6};
//        int target = 26;
//        int [] res = twoSum(nums, target);
//        System.out.println("res = ["+res[0]+", "+res[1]+"]");
//
//        int [] nums2 = {-4,-2,1,-5,-4,-4,4,-2,0,4,0,-2,3,1,-5,0, 0};
//        List<List<Integer>> lists = threeSum(nums2);
//        for (List<Integer> list : lists){
//            System.out.println(list);
//        }

//        testAddTwoNumbers(nums, nums2);
        int length = lengthOfLongestSubstring("abcbdme");
        System.out.println("length = " + length);

        String isHuiwen = maxHuiwenString("abbabb");
        System.out.println("isHuiwen = " + isHuiwen);

    }


    /**
     * 给定一个整数数组 nums 和一个目标值 target，请你在该数组中找出和为目标值的那 两个 整数，并返回他们的数组下标。
     * 你可以假设每种输入只会对应一个答案。但是，你不能重复利用这个数组中同样的元素
     *
     * @param nums
     * @param target
     * @return
     */
    public static int[] twoSum(int[] nums, int target) {
        int[] res = new int[2];
        for (int i = 0; i < nums.length; i++) {
            int diff = target - nums[i];
            for (int j = i + 1; j < nums.length; j++) {
                if (diff == nums[j]) {
                    res[0] = i;
                    res[1] = j;
                    return res;
                }
            }
        }
        return res;
    }

    /**
     * 给定一个包含 n 个整数的数组 nums，判断 nums 中是否存在三个元素 a，b，c ，使得 a + b + c = 0 ？找出所有满足条件且不重复的三元组。
     *
     * @param nums
     * @return
     */
    public static List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        boolean isAdded = false;
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                for (int k = j + 1; k < nums.length; k++) {
                    if (nums[i] + nums[j] + nums[k] == 0) {
                        List<Integer> tmp = new ArrayList<>();
                        tmp.add(nums[i]);
                        tmp.add(nums[j]);
                        tmp.add(nums[k]);
                        if (nums[i] == 0 && nums[j] == 0) {
                            if (!isAdded) {
                                isAdded = true;
                                res.add(tmp);
                            }
                        } else {
                            if (res.size() > 0) {
                                for (int t = res.size() - 1; t >= 0; t--) {
                                    List<Integer> list = res.get(t);
                                    if (list.contains(tmp.get(0)) && list.contains(tmp.get(1)) && list.contains(tmp.get(2))) {
                                        break;
                                    } else if (t == 0) {
                                        res.add(tmp);
                                    }
                                }
                            } else {
                                res.add(tmp);
                            }
                        }
                    }
                }
            }
        }
        return res;
    }

    //.....................................2.测试两个数的和...................................
    private static void testAddTwoNumbers(int[] nums, int[] nums2) {
        ListNode head1 = initList(nums);
        ListNode head2 = initList(nums2);

        ListNode head = addTwoNumbers(head1, head2);
        ListNode tmp1 = head.next;
        while (tmp1 != null) {
            System.out.println("vv = " + tmp1.val);
            tmp1 = tmp1.next;
        }
    }

    /**
     * 给出两个 非空 的链表用来表示两个非负的整数。其中，它们各自的位数是按照 逆序 的方式存储的，并且它们的每个节点只能存储 一位 数字。
     * 如果，我们将这两个数相加起来，则会返回一个新的链表来表示它们的和。
     * 您可以假设除了数字 0 之外，这两个数都不会以 0 开头。
     * <p>
     * 示例：
     * 输入：(2 -> 4 -> 3) + (5 -> 6 -> 4)
     * 输出：7 -> 0 -> 8
     * 原因：342 + 465 = 807
     *
     * @param l1
     * @param l2
     * @return
     */
    public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode dummyHead = new ListNode(0);
        ListNode p = l1, q = l2, curr = dummyHead;
        int carry = 0;
        while (p != null || q != null) {
            int x = (p != null) ? p.val : 0;
            int y = (q != null) ? q.val : 0;
            int sum = carry + x + y;
            carry = sum / 10;
            curr.next = new ListNode(sum % 10);
            curr = curr.next;
            if (p != null) p = p.next;
            if (q != null) q = q.next;
        }
        if (carry > 0) {
            curr.next = new ListNode(carry);
        }
        return dummyHead.next;

    }

    public static ListNode initList(int[] nums) {
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

    public static class ListNode {
        public int val;
        public ListNode next;

        public ListNode(int val) {
            this.val = val;
        }
    }

    //.................................................................3、无重复字符的最长子串.................................

    public static int lengthOfLongestSubstring(String s) {
        int n = s.length(), ans = 0;
        Map<Character, Integer> map = new HashMap<>();
        for (int end = 0, start = 0; end < n; end++) {
            char alpha = s.charAt(end);
            if (map.containsKey(alpha)) {
                start = Math.max(map.get(alpha), start);//找到不重复数据的开始index
            }
            ans = Math.max(ans, end - start + 1);
            map.put(s.charAt(end), end + 1);//加1表示从后面一个数据开始不重复
        }
        return ans;

    }

    //......................................4、找最大的会问子串.........................................................
    public static String maxHuiwenString(String s) {
        int len = s.length();
        int diff = 0;
        int maxLen = 0;
        String maxString = "";
        for (int i = 0; i < len; i++) {
            for (int j = i; j < len; j++) {
                diff = j - i;
                if (isHuiwen(s.substring(i, j + 1))) {
                    if (maxLen <= diff) {
                        maxLen = diff;
                        maxString = s.substring(i, j + 1);
                    }
                }
            }
        }
        return maxString;
    }

    public static boolean isHuiwen(String s) {
        int len = s.length();
        for (int i = 0; i < len / 2; i++) {
            if (!(s.charAt(i) == s.charAt(len - 1 - i))) {
                return false;
            }
        }
        return true;
    }

    //回文子串方法2
    public static String longestPalindrome(String s) {
        if (s == null || s.length() < 1) return "";
        int start = 0, end = 0;
        for (int i = 0; i < s.length(); i++) {
            int len1 = expandAroundCenter(s, i, i);
            int len2 = expandAroundCenter(s, i, i + 1);
            int len = Math.max(len1, len2);
            if (len > end - start) {
                start = i - (len - 1) / 2;
                end = i + len / 2;
            }
        }
        return s.substring(start, end + 1);
    }

    private static int expandAroundCenter(String s, int left, int right) {
        int L = left, R = right;
        while (L >= 0 && R < s.length() && s.charAt(L) == s.charAt(R)) {
            L--;
            R++;
        }
        return R - L - 1;
    }

    //..........................................................5.Z字形变换...........................................
    public static String convert(String s, int numRows) {
        String res = "";
        int len = s.length();
        int coloum = len % numRows == 0 ? len / numRows : len / numRows + 1;
        char [][] chars = new char[numRows][coloum];
        for (int i = 0; i < len; i++){

        }
        return res;
    }


}
