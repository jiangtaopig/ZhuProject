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


}
