package daily.yiyuan.com.test_java.algoritham;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2020/2/28
 * 对于字符串中查找子串是否存在的算法中，Sunday 比 BM 和 KMP算法效率都要高，而暴力算法 BF 算法效率最差
 *
 *   HERE IS A SIMPLE EXAMPLE
 *   EXAMPLE
 *
 *   从头开始比较： H 和 E 不匹配， 那么选择主串中 对应模式串长度的后一位字符 就是 空格 来在模式串中从右往左找，找不到就把模式串移向后移动模式串的长度+1
 *   找到了就 移动： 模式串的长度 - 该字符在模式串中的位置
 *
 *   因为 模式中最后一位E的后一位对应的主串字符是 空格，且空格在模式中中不存在，所以 移动位数 = 模式串的长度 +1 = 7+1=8
 *   HERE IS A SIMPLE EXAMPLE
 *           EXAMPLE
 *
 *    再次比较， A和E不等，模式串后一位对应的主串字符是 E，在模式串中从右往左找，找到E就是最后一位， 移动位数 = 模式串的长度 - 该字符在模式串中的位置 = 7-6=1
 *    HERE IS A SIMPLE EXAMPLE
 *             EXAMPLE
 *
 *             再比较 空格 和 E ，不匹配，则模式串后一位对应主串的空格，空格在模式串中不存在，所以，移动 7 + 1 = 8
 *             HERE IS A SIMPLE EXAMPLE
 *                              EXAMPLE
 *
 *             发现匹配了
 */
public class TestSunday {
    public static void main(String[] args) {
        Sunday("here every where", "where");
    }

    public static int Sunday(String haystack, String needle) {
        int hayLen = haystack.length();
        int nLen = needle.length();

        int i = 0;//haystack串的游标索引
        int j = 0;// needle串的游标索引

        // haystack剩余字符少于needle串时跳过比较
        while (i <= hayLen - nLen) {
            // 将needle串与haystack串中参与比较的子串进行逐个字符比对
            while (j < nLen && haystack.charAt(i + j) == needle.charAt(j)) {
                j++;
            }
            // 如果j等于needle串的长度说明此时匹配成功，可以直接返回此时主串的游标索引
            if (j == nLen) {
                return i;
            }
            // 不匹配时计算需要跳过的字符数，移动主串游标i
            if (i < hayLen - nLen) {
                // 对照字符在needle串存在，则需要跳过的字符数为从对照字符在needle串中最后出现的位置起剩余的字符个数
                // 不存在, 则跳过的字符数为needle串长度+1，也就是代码nLen-(-1)的情况
                int index = lastIndex(needle, haystack.charAt(i + nLen));
                i += nLen - index;
            } else {
                return -1;
            }
            // 每次比较之后将needle游标置为0
            j=0;
        }
        return -1;
    }

    public static int lastIndex(String str, char ch) {
        // 从后往前检索
        for (int j = str.length() - 1; j >= 0; j--) {
            if (str.charAt(j) == ch) {
                return j;
            }
        }
        return -1;
    }
}
