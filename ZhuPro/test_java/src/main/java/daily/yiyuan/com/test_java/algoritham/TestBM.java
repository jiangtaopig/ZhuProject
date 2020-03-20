package daily.yiyuan.com.test_java.algoritham;

import java.util.HashMap;
import java.util.Map;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2020/3/4
 * BM算法：是找 模式串 在 主串 中的位置，即 "坏字符" 和 "好后缀"规则
 * 是从与模式串对齐的主串的位置开始比较，是从右往左的
 * 子串  ABGABCD
 * 模式串 BCD
 * 是从与模式串最右边字符D对齐的G开始比较，D和G不相等就是坏字符，移动的位数等于 ： 坏字符在模式串中的位置 - 坏字符在模式串中最右出现的位置。
 * 此外，如果"坏字符"不包含在模式串之中，则最右出现位置为-1。例如 此处的G对应模式串的位置2，而G在模式串中不存在，所以移动的位数等于：2 - （-1）=3
 * <p>
 * 再来看看什么是 好后缀规则
 * ABRGMPDTMPTCMP
 * MPTCMP
 * 好后缀移动规则：  好后缀在模式串中的位置 - 好后缀在模式串中上一次出现的位置，且如果好后缀在模式串中没有再次出现，则为-1。
 * MP 、P 都是好后缀，先判断 好后缀 MP 在模式串 中有没有其他与 MP 相等的片段，这里面有，所以：  好后缀在模式串中的位置 = 4， 好后缀在模式串中上一次出现的位置 = 0，移动 4 - 0 = 0；
 * 如果不相等，则还要判断 下一个好后缀P在 模式串 中是否存在
 */
public class TestBM {
    public static void main(String[] args) {
        String text = "here is a simple example";
        String pattern = "example";
        boyerMoore(pattern, text);
    }

    public static void boyerMoore(String pattern, String text) {
        int m = pattern.length();
        int n = text.length();
        Map<String, Integer> bmBc = new HashMap<String, Integer>();
        int[] bmGs = new int[m];
        // proprocessing
        preBmBc(pattern, m, bmBc);
        preBmGs(pattern, m, bmGs);
        // searching
        int j = 0;
        int i = 0;
        int count = 0;
        while (j <= n - m) {
            for (i = m - 1; i >= 0 && pattern.charAt(i) == text.charAt(i + j); i--) { // 用于计数
                count++;
            }
            if (i < 0) {
                System.out.println("one position is:" + j);
                j += bmGs[0];
            } else {
                j += Math.max(bmGs[i], getBmBc(String.valueOf(text.charAt(i + j)), bmBc, m) - m + 1 + i);
            }
        }
        System.out.println("count:" + count);
    }

    private static void preBmBc(String pattern, int patLength, Map<String, Integer> bmBc) {
        System.out.println("bmbc start process...");
        {
            for (int i = patLength - 2; i >= 0; i--)
                if (!bmBc.containsKey(String.valueOf(pattern.charAt(i)))) {
                    bmBc.put(String.valueOf(pattern.charAt(i)), (Integer) (patLength - i - 1));
                }
        }
    }

    private static void preBmGs(String pattern, int patLength, int[] bmGs) {
        int i, j;
        int[] suffix = new int[patLength];
        suffix(pattern, patLength, suffix);
        // 模式串中没有子串匹配上好后缀，也找不到一个最大前缀
        for (i = 0; i < patLength; i++) {
            bmGs[i] = patLength;
        }
        // 模式串中没有子串匹配上好后缀，但找到一个最大前缀
        j = 0;
        for (i = patLength - 1; i >= 0; i--) {
            if (suffix[i] == i + 1) {
                for (; j < patLength - 1 - i; j++) {
                    if (bmGs[j] == patLength) {
                        bmGs[j] = patLength - 1 - i;
                    }
                }
            }
        }
        // 模式串中有子串匹配上好后缀
        for (i = 0; i < patLength - 1; i++) {
            bmGs[patLength - 1 - suffix[i]] = patLength - 1 - i;
        }
        System.out.print("bmGs:");
        for (i = 0; i < patLength; i++) {
            System.out.print(bmGs[i] + ",");
        }
        System.out.println();
    }

    private static void suffix(String pattern, int patLength, int[] suffix) {
        suffix[patLength - 1] = patLength;
        int q = 0;
        for (int i = patLength - 2; i >= 0; i--) {
            q = i;
            while (q >= 0 && pattern.charAt(q) == pattern.charAt(patLength - 1 - i + q)) {
                q--;
            }
            suffix[i] = i - q;
        }
    }

    private static int getBmBc(String c, Map<String, Integer> bmBc, int m) {
        // 如果在规则中则返回相应的值，否则返回pattern的长度
        if (bmBc.containsKey(c)) {
            return bmBc.get(c);
        } else {
            return m;
        }
    }

}
