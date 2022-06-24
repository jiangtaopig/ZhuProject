package daily.yiyuan.com.test_java.algoritham.slide_window;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

/**
 * 滑动窗口的思想：
 * 1. 我们在字符串 S 中使用双指针中的左右指针，初始化 left = right = 0，
 * 把索引左闭右开区间 [left, right) 称为一个【窗口】。
 * 2. 不断的增加 right 指针，扩大窗口 [left, right)，直到窗口中的字符串符合要求了（包含 T 中所有字符串）。
 * 3. 此时，停止增加 right，转而不断增加 left 指针缩小窗口 [left, right)，
 * 直到窗口中的字符串不在符合要求（不包含 T 中所有字符串）。同时，每次增加 left 都要更新一轮结果。
 * 4. 重复 步骤2 和 步骤3 ，直到 right 到达字符串 S 的尽头。
 */
public class TestSlideWindow {
    public static void main(String[] args) {

        String S = "AADBCAD";
        String T = "ABC";
        String res = minSubString(S, T);
        System.out.println("TestSlideWindow::minSubString res = " + res);
        boolean include = checkInclusion("bcdbabcb", "bba");
        System.out.println("TestSlideWindow::checkInclusion include = " + include);

        List<Integer> res2 = findAnagrams("cbadfcab", "abc");
        System.out.println("TestSlideWindow::findAnagrams res2 = " + res2);

        lengthOfLongestSubstring("abadecda");

        Thread thread = new Thread("ddd"){
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName()+" >>> start ");
                try {
                    TimeUnit.SECONDS.sleep(1);
                    new Thread("eee") {
                        @Override
                        public void run() {
                            try {
                                System.out.println(Thread.currentThread().getName()+" >>> start ");
                                TimeUnit.SECONDS.sleep(2);
                                System.out.println(Thread.currentThread().getName()+" >>> end ");
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }.start();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName()+" >>> end ");
            }
        };
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName()+"------- >>> end ");
    }


    /**
     * 给你一个字符串 S ，一个字符串 T ，请在字符串 S 中找出：包含T所有字母的最小子串。
     * 示例：
     * 输入： S = "ADODEBANC", T = "ABC"
     * 输出： "BANC"
     * 如果S中不存在这样的子串就返回 ""，如果存在，我们保证它的答案是唯一的。
     */

    private static String minSubString(String S, String T) {
        Map<Character, Integer> need = new HashMap<>();
        Map<Character, Integer> window = new HashMap<>();

        for (char c : T.toCharArray()) {
            need.put(c, need.getOrDefault(c, 0) + 1); // 存储字符的次数
        }
        int left = 0, right = 0; // 区间是左开右闭的 [left, right)
        int valid = 0; // 表示窗口中满足 need 条件的字符个数
        int start = 0;
        int len = Integer.MAX_VALUE;

        while (right < S.length()) {
            // c 是要移进窗口的
            char c = S.charAt(right);
            right++;
            if (need.containsKey(c)) { // T 中包含此字符就加入窗口
                window.put(c, window.getOrDefault(c, 0) + 1);
                if (window.get(c).equals(need.get(c))) {
                    valid++;
                }
            }
            // valid == need.size() 表示窗口中已经包含了 T 的所有字符
            while (valid == need.size()) {
                // 这里更新最小覆盖子串
                if (right - left < len) {
                    start = left;
                    len = right - left;
                }
                // d 是要移出窗口的
                char d = S.charAt(left);
                left++;
                // 进行窗口内的数据更新
                if (need.containsKey(d)) {
                    // 如果window中和need中对应字符的个数相等，那么 valid--，因为下面要把window中该字符的个数减一
                    if (window.get(d).equals(need.get(d))) {
                        valid--;
                    }
                    window.put(d, window.getOrDefault(d, 0) - 1);
                }
            }
        }
        return len == Integer.MAX_VALUE ? "" : S.substring(start, start + len);
    }

    /**
     * 给定2个字符串 s1 和 s2，写一个函数来判断 s2 是否包含 s1 的排列。
     * <p>
     * 示例1 ： s1 = "ab" , s2 = "eidbaoo"
     * 返回 true，s2 包含 s1 的排列之一 "ba"
     * <p>
     * 示例2 s1 = "ab"，s2 = "adbca"
     * 返回 false
     */
    private static boolean checkInclusion(String S, String T) {
        Map<Character, Integer> need = new HashMap<>();
        Map<Character, Integer> window = new HashMap<>();

        for (char c : T.toCharArray()) {
            need.put(c, need.getOrDefault(c, 0) + 1); // 存储字符的次数
        }

        int right = 0;
        int valid = 0;
        int sLen = S.length();

        while (right < sLen) {
            char c = S.charAt(right);
            right++;
            if (need.containsKey(c)) {
                window.put(c, window.getOrDefault(c, 0) + 1);
                if (window.get(c).equals(need.get(c))) {
                    valid++;
                }
            } else { // 如果字符 c 不在T中，那么清除 window 且重置 valid
                if (window.size() > 0) {
                    window.clear();
                }
                valid = 0;
            }
            if (valid == need.size()) {
                return true;
            }
        }
        return false;
    }

    private static boolean checkInclusion2(String S, String T) {
        Map<Character, Integer> need = new HashMap<>();
        Map<Character, Integer> window = new HashMap<>();

        for (char c : T.toCharArray()) {
            need.put(c, need.getOrDefault(c, 0) + 1); // 存储字符的次数
        }

        int left = 0, right = 0;
        int valid = 0;

        while (right < S.length()) {
            char c = S.charAt(right);
            right++;

            if (need.containsKey(c)) {
                window.put(c, window.getOrDefault(c, 0) + 1);
                if (window.get(c).equals(need.get(c))) {
                    valid++;
                }
            }

            // 移动left缩小窗口的时机是窗口大小大于t.size()时，因为排列嘛，显然长度应该是一样的
            while (right - left >= need.size()) {
                // 当发现valid == need.size()时，就说明窗口中就是一个合法的排列
                if (valid == need.size()) {
                    return true;
                }
                char d = S.charAt(left);
                if (need.containsKey(d)) {
                    if (window.get(d).equals(need.get(d))) {
                        valid--;
                    }
                    window.put(d, window.getOrDefault(d, 0) - 1);
                }
            }
        }
        return false;
    }

    /**
     * 给定一个字符串 S 和 一个非空字符串 T ，找到 S 中所有是 P 的字母异位词的子串，返回这些子串的起始索引。
     *
     * 示例 S = "cbadfcab" T = "abc"
     *     返回[0, 5]
     *     解释： 起始索引 0 的子串是 "cba"，他是"abc" 的异位词
     *           起始索引 5 的子串是 "cab"，他是"abc" 的异位词
     *
     */
    private static List<Integer> findAnagrams(String S, String T) {
        List<Integer> res = new ArrayList<>();
        Map<Character, Integer> need = new HashMap<>();
        Map<Character, Integer> window = new HashMap<>();

        for (char c : T.toCharArray()) {
            need.put(c, need.getOrDefault(c, 0) + 1); // 存储字符的次数
        }

        int left = 0, right = 0;
        int valid = 0;
        int sLen = S.length();
        while (right < sLen) {
            char c = S.charAt(right);
            right++;

            if (need.containsKey(c)) {
                window.put(c, window.getOrDefault(c, 0) + 1);
                if (window.get(c).equals(need.get(c))) {
                    valid++;
                }
            }

            while (right - left >= need.size()) {
                if (valid == need.size()) {
                    res.add(left);
                }
                char d = S.charAt(left);
                left++;
                if (need.containsKey(d)) {
                    if (window.get(d).equals(need.get(d))) {
                        valid--;
                    }
                    window.put(d, window.getOrDefault(d, 0) - 1);
                }
            }
        }
        return res;
    }

    /**
     * 给定一个字符串，请你找出其中不含有重复字符的最长子串的长度
     * 示例1："abcabcbb" 返回 3 因为是 abc
     *       "bbbb"  返回1 因为是 b
     *
     */
    private static int lengthOfLongestSubstring(String s) {
        Map<Character, Integer> window = new HashMap<>();
        int left = 0, right = 0;
        int res = 0;
        while (right < s.length()) {
            char c = s.charAt(right);
            right++;
            window.put(c, window.getOrDefault(c, 0) + 1);

            // 当 window 中有重复字符时，就要收缩滑动窗口
            while (window.get(c) > 1) {
                char d = s.charAt(left);
                left++;
                window.put(d, window.getOrDefault(d, 0) - 1);
            }
            res = Math.max(res, right - left);
        }
        return res;
    }

}
