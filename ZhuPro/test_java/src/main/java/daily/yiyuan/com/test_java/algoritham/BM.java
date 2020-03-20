package daily.yiyuan.com.test_java.algoritham;

import java.util.Random;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2020/3/5
 */
public class BM {
    /**
     * 利用坏字符规则计算移动位数：坏字符 对应在模式串中的位置 - 从右往左搜索坏字符在模式串中的位置，
     * 如果 坏字符在模式串中未找到，那么 就 减去 （-1）
     */
    public static int badCharacter(String moduleString, char badChar, int badCharSuffix) {
        return badCharSuffix - moduleString.lastIndexOf(badChar, badCharSuffix);//lastIndexOf 找不到就返回 -1， badCharSuffix 表示从badCharSuffix这个位置开始，往左边查找
    }

    /**
     * 利用好后缀规则计算移动位数
     */
    public static int goodPostSuffix(String moduleString, int goodCharSuffix) {
        int result = -1;
        // 模式串长度
        int moduleLength = moduleString.length();
        // 好字符数
        int goodCharNum = moduleLength - 1 - goodCharSuffix;

        for (; goodCharNum > 0; goodCharNum--) {
            String endSection = moduleString.substring(moduleLength - goodCharNum, moduleLength);
            String startSection = moduleString.substring(0, goodCharNum);
            if (startSection.equals(endSection)) {
                result = moduleLength - goodCharNum;
            }
        }
        return result;
    }

    /**
     * BM匹配字符串
     *
     * @param originString 主串
     * @param moduleString 模式串
     * @return 若匹配成功，返回下标，否则返回-1
     */
    public static int match(String originString, String moduleString) {
        // 主串
        if (originString == null || originString.length() <= 0) {
            return -1;
        }
        // 模式串
        if (moduleString == null || moduleString.length() <= 0) {
            return -1;
        }
        // 如果模式串的长度大于主串的长度，那么一定不匹配
        if (originString.length() < moduleString.length()) {
            return -1;
        }

        int moduleSuffix = moduleString.length() - 1;
        int moduleIndex = moduleSuffix;
        int originIndex = moduleSuffix;
        int originLength = originString.length();

        for (int i = originIndex; originIndex < originLength && moduleIndex >= 0;) {
            char oc = originString.charAt(originIndex);
            char mc = moduleString.charAt(moduleIndex);
            if (oc == mc) {
                originIndex--;
                moduleIndex--;
            } else {
                // 坏字符规则
                int badMove = badCharacter(moduleString, oc, moduleIndex);
                // 好字符规则
                int goodMove = goodPostSuffix(moduleString, moduleIndex);
                // 下面两句代码可以这样理解，主串位置不动，模式串向右移动
                originIndex = i + Math.max(badMove, goodMove);
                moduleIndex = moduleSuffix;
                // ot就是中间变量
                i = originIndex;
            }
        }
        if (moduleIndex < 0) {
            // 多减了一次
            return originIndex + 1;
        }
        return -1;
    }

    public static void main(String[] args) {
        // 主串
        String originString = "HERE IS A SIMPLE EXAMPLE";
        // 模式串
        String moduleString = "EXAMPLE";
        int index = match(originString, moduleString);
        System.out.println("匹配的下标：" + index);
    }
}
