package daily.yiyuan.com.test_java.leet_code;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2020/6/28
 * 字符串匹配
 */
public class TestStringMatching {
    public static void main(String[] args) {

        String s = "goodgoogle";
        String t = "google";
        boolean isMatch = forceMatch(s, t);
        System.out.println("isMatch = " + isMatch);

        String maxString = getMaxString("1345267", "123456");
        System.out.println("maxString = "+maxString);
    }

    /**
     * 字符串匹配的暴力算法
     *
     * @param s 主串
     * @param t 子串
     * @return
     */
    private static boolean forceMatch(String s, String t) {
        boolean isMatching = false;
        int sLen = s.length();
        int tLen = t.length();

        for (int i = 0; i < sLen - tLen + 1; i++) {
            // 每次开始比较都是和 子串的开始字符进行比较
            if (s.charAt(i) == t.charAt(0)) {
                int matchCnt = 0; // 匹配上的字符串数量
                for (int j = 0; j < tLen; j++) {
                    if (s.charAt(i + j) != t.charAt(j)) {
                        break;
                    }
                    matchCnt++;
                }
                if (matchCnt == tLen) {
                    isMatching = true;
                }
            }
        }
        return isMatching;
    }

    /**
     * 最长子串
     * a = "1345723" b = "123456" , 那么最长公共子串就是 "345"，注意子串和子序列不一样，子串是连续的字符集
     *
     * @param a
     * @param b
     * @return
     */
    private static String getMaxString(String a, String b) {
        int maxLen = 0;
        String maxString = "";
        for (int i = 0, aLen = a.length(); i < aLen; i++) {
            for (int j = 0, bLen = b.length(); j < bLen; j++) {
                if (a.charAt(i) == b.charAt(j)) {
                    for (int m = i, n = j; m < aLen && n < bLen; m++, n++) {
                        if (a.charAt(m) != b.charAt(n)) {
                            break;
                        }

                        if (maxLen < m - i + 1) { // m - i + 1 表示已匹配的字符个数
                            maxLen = m - i + 1;
                            maxString = a.substring(i, m + 1);
                        }
                    }
                }
            }
        }
        return maxString;
    }

    /**
     * LeetCode面试算法-力扣 567. 字符串的排列
     * 给定两个字符串 s1 和 s2，写一个函数来判断 s2 是否包含 s1 的排列。即判断第一个字符串的排列之一是否是第二个字符串的子串。
     *     输入: s1 = "ab" s2 = "eidbaooo"
     *     输出: True
     *     解释: s2 包含 s1 的排列之一 ("ba").
     *
     *     输入: s1= "ab" s2 = "eidboaoo"
     *     输出: False
     *
     * @param s1
     * @param s2
     * @return
     */
    public static boolean checkInclusion(String s1, String s2) {
        if (s1.length() > s2.length())
            return false;
        //因为字符串都是a-z的字母，所以我们可以使用一个长度为26的int数组来存储每个字母出现的个数
        int[] s1map = new int[26];
        int[] s2map = new int[26];
        for (int i = 0; i < s1.length(); i++) {
            //这里char类型加减使用Ascii码，-'a'正好对应a-z在数组中的索引，值为该字母出现的次数
            s1map[s1.charAt(i) - 'a']++;
            s2map[s2.charAt(i) - 'a']++;
        }
        //滑动窗口
        //窗口大小为s1.length，索引从[0 —— s1.length-1]到[s2.length-s1.length —— s2.length-1]
        for (int i = 0; i < s2.length() - s1.length(); i++) {
            //如果匹配，直接返回
            if (isMatch(s1map, s2map)) {
                return true;
            }
            //如果不匹配，窗口向后移动一位
            //窗口加入下一个字母
            s2map[s2.charAt(i + s1.length()) - 'a']++;
            //窗口移除第一个字母
            s2map[s2.charAt(i) - 'a']--;
        }
        //最后比较i = s2.length-s1.length的情况
        return isMatch(s1map,s2map);

    }
    private static boolean isMatch(int[] s1map, int[] s2map) {
        for (int i = 0; i < 26; i++) {
            // 这里只要出现一个字母个数不一样，就直接返回false
            if (s1map[i] != s2map[i])
                return false;
        }
        return true;
    }


}
