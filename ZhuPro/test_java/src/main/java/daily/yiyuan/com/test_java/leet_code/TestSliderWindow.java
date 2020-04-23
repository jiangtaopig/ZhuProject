package daily.yiyuan.com.test_java.leet_code;

import java.util.HashMap;
import java.util.Map;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2020/4/22
 * 滑动窗口
 */
public class TestSliderWindow {
    public static void main(String[] args) {

        String s = minWindow("ABABC", "AAC");
        System.out.println("s = " + s);
        boolean include = checkInclusion("ab", "mnba");
        System.out.println("include = " + include);

        lengthOfLongestSubstring("bacbcd");
    }


    /**
     * 给你一个字符串s 和 一个字符串 t, 请在字符串 s中找出 包含 t 的所有字母的最小子串
     *
     * @param s
     * @param t
     * @return
     */
    public static String minWindow(String s, String t) {
        //存储t 中各个字符出现的次数
        Map<Character, Integer> need = new HashMap<>();
        //存储滑动到窗口中字符出现的次数
        Map<Character, Integer> window = new HashMap<>();
        int left = 0, right = 0; //注意是左闭右开 即 [left, right)
        int valid = 0; //表示窗口中满足need条件的字符个数
        //记录最小覆盖子串的起始索引以及长度
        int start = 0, len = Integer.MAX_VALUE;

        for (int i = 0, tLen = t.length(); i < tLen; i++) {
            Character tmp = t.charAt(i);
            need.put(tmp, need.getOrDefault(tmp, 0) + 1);
        }

        int sLen = s.length();
        while (right < sLen) {
            char c = s.charAt(right);
            right++;
            if (need.containsKey(c)) {
                window.put(c, window.getOrDefault(c, 0) + 1);
                if (need.get(c) == window.get(c)) {//字符出现的次数完全匹配
                    valid++;
                }
            }
            //说明t中所有的字符已被覆盖，下面要判断左侧窗口是否要滑动
            while (valid == need.size()) {
                if (right - left < len) {
                    start = left;
                    len = right - left;
                }
                //d 是即将移除窗口的字符
                char d = s.charAt(left);
                left++;
                //进行窗口内数据的更新
                if (need.containsKey(d)) {
                    if (window.get(d) == need.get(d)) {
                        valid--;
                    }
                    window.put(d, window.getOrDefault(d, 0) - 1);
                }
            }
        }
        return len == Integer.MAX_VALUE ? "" : s.substring(start, start + len);
    }

    /**
     * 给定两个字符串s和t，判断t的排列是否在s中
     * <p>
     * 方法1：最简单最暴力的方法其实就是找到s1的所有全排列，然后在s2中查找是否这些全排列字符串在s2中。但是这种方法耗时太大，会导致超时。
     * 方法2：滑动窗口
     * 其实不需要找到s1的全排列，因为我们只需要考虑s2中是否包含s1中同样个数的字符，并且这些字符是连在一起的就行了。
     *
     * @param s
     * @param t
     * @return
     */
    public static boolean checkInclusion(String t, String s) {
        //存储t 中各个字符出现的次数
        Map<Character, Integer> need = new HashMap<>();
        //存储滑动到窗口中字符出现的次数
        Map<Character, Integer> window = new HashMap<>();
        int left = 0, right = 0; //注意是左闭右开 即 [left, right)
        int valid = 0; //表示窗口中满足need条件的字符个数

        for (int i = 0, tLen = t.length(); i < tLen; i++) {
            Character character = t.charAt(i);
            need.put(character, need.getOrDefault(character, 0) + 1);
        }

        while (right < s.length()) {
            char c = s.charAt(right);
            right++;
            if (need.containsKey(c)) {
                window.put(c, window.getOrDefault(c, 0) + 1);
                if (window.get(c) == need.get(c)) {
                    valid++;
                }
            }

            while (right - left >= t.length()) {//判断字符是否连在一起
                if (valid == need.size()) {
                    return true;
                }
                char d = s.charAt(left);
                left++;
                //进行窗口内数据的更新
                if (need.containsKey(d)) {
                    if (need.get(d) == window.get(d)) {
                        valid--;
                    }
                    window.put(d, window.getOrDefault(d, 0) - 1);
                }
            }
        }
        return false;
    }

    /**
     * 给定一个字符串，请你找出其中不含有重复字符的 最长子串 的长度 。
     * 子串 是 连续的 子序列不需要
     * @param s
     * @return
     */
    public static int lengthOfLongestSubstring(String s) {
        Map<Character, Integer> window = new HashMap<>();
        int left = 0;
        int right = 0;
        int res = 0;
        while (right < s.length()) {
            char c = s.charAt(right);
            right++;
            window.put(c, window.getOrDefault(c, 0) + 1);
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
