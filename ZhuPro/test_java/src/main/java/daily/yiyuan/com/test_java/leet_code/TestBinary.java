package daily.yiyuan.com.test_java.leet_code;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2020/3/31
 */
public class TestBinary {
    public static void main(String[] args) {

        int size = maxNum(new String[]{"acb", "bcd", "uxymzt", "mnpaq"});
        System.out.println("size = " + size);

        singleNumber(new int[]{3, 5, 3, 3});
        int2Binary(-20);
        reverseBits(20);
        singleNumber3(new int[]{3, 3, 5, 5, 2, 4});
        isPowerOfTwo(5);
        rangeBitwiseAnd(5, 7);
        rangeBitwiseAnd2(Integer.MAX_VALUE - 1, Integer.MAX_VALUE);
        subsets(new int[]{1, 2});

        List<String> l1 = new ArrayList<>();
        l1.add("b");
        l1.add("c");

        List<String> l2 = new ArrayList<>();
        l2.add("b");
        l2.add("d");
        l2.add("e");

        l1.removeAll(l2);
        l1.size();

        findRepeatedDnaSequences("AAAAAAAAAAAA");

        int a = 1 << 0;

        maxProduct(new String[]{"acb", "bcd", "uxymzt", "mnpaq"});

        ListNode node = InitListNodeUtils.initListNodeWithoutHead(new int[]{1, 2, 3, 4, 5, 6, 7, 8});
        node = reverseKGroup(node, 3);
        int size2 = 0;
        getSum(5, 3);

         a = 2 ^25;
         int b = 5 ^ 25;
    }

    /**
     * 给定一个字符串数组 words，找到 length(word[i]) * length(word[j]) 的最大值，并且这两个单词不含有公共字母。
     * 你可以认为每个单词只包含小写字母。如果不存在这样的两个单词，返回 0。
     * <p>
     * 暴力法
     *
     * @param vals
     * @return
     */
    public static int maxNum(String[] vals) {
        int max = 0;
        int len = vals.length;
        for (int i = 0; i < len; i++) {
            for (int j = i + 1; j < len; j++) {

                if (!haveSameChar(vals[i], vals[j])) {
                    int size = vals[i].length() * vals[j].length();
                    if (size > max) {
                        max = size;
                    }
                }
            }
        }
        return max;
    }


    public static boolean haveSameChar(String s1, String s2) {
        if (s1 == null || s2 == null) {
            return false;
        }

        int l1 = s1.length();
        int l2 = s2.length();
        for (int i = 0; i < l1; i++) {
            char c1 = s1.charAt(i);
            for (int j = 0; j < l2; j++) {
                char c2 = s2.charAt(j);
                if (c1 == c2) {
                    return true;
                }
            }
        }
        return false;
    }


    /**
     * @param words
     * @return 解法 ： 用二进制的一位表示某一个字母是否出现过，0表示没出现，1表示出现。"abcd"二进制表示00000000 00000000 00000000 00001111、
     * "bc"二进制表示00000000 00000000 00000000 00000110。当两个字符串没有相同的字母时，二进制数与的结果为0。
     */

    public static int maxProduct(String[] words) {
        int res = 0;
        int len = words.length;
        int[] nums = new int[len];
        for (int i = 0; i < len; i++) {
            String tmp = words[i];
            for (int j = 0; j < tmp.length(); j++) {
                nums[i] |= 1 << (tmp.charAt(j) - 'a');
            }
        }

        for (int i = 0; i < len; i++) {
            for (int j = i + 1; j < len; j++) {
                if ((nums[i] & nums[j]) == 0) {//表示没有相同的字符
                    int size = words[i].length() * words[j].length();
                    if (size > res) {
                        res = size;
                    }
                }
            }
        }
        return res;
    }

    /**
     * 其他的数都出现3次，只有一个数出现1次，找出这个数
     *
     * @param nums
     * @return
     */
    public static int singleNumber(int[] nums) {
        if (nums == null || nums.length <= 0)
            throw new RuntimeException("数组不合法");

        int[] bits = new int[32];

        for (int j = 0; j < bits.length; j++) {
            for (int i = 0; i < nums.length; i++) { // 每一位数字对应的0或1加起来
                int num = nums[i] >> j;
                bits[j] += (num & 1);
            }
        }
        int result = 0;
        for (int i = bits.length - 1; i >= 0; i--) {
            result <<= 1;
            result += bits[i] % 3;
        }
        return result;
    }

    /**
     * 十进制 n 转 二进制
     *
     * @param n 这个是可以保证正负数转行正确的
     * @return
     */
    public static int[] int2Binary(int n) {
        int[] bits = new int[32];
        for (int i = 0; i < bits.length; i++) {
            int bit = n >> i;
            bits[i] = bit & 1;
        }

        int res = 0;
        for (int i = 0; i < bits.length; i++) {
            res += bits[i] << i;
        }

        return bits;
    }

    /**
     * 颠倒二进制位
     *
     * @param n
     * @return
     */
    public static int reverseBits(int n) {
        int[] bits = new int[32];
        for (int i = 0; i < bits.length; i++) {
            int bit = n >> i;
            bits[i] = bit & 1;
        }
        int res = 0;
        for (int i = bits.length - 1; i >= 0; i--) {
            res += (bits[i] << (bits.length - 1 - i));
        }
        return res;
    }

    /**
     * 数组中其余的数都出现2次，只有2个数出现一次，找出这2个数
     *
     * @param nums
     * @return
     */
    public static int[] singleNumber3(int[] nums) {
        int[] res = new int[2];
        int result = 0;
        //将所有的数据进行异或，那么结果就是 待找出的那2个数的异或结果
        for (int i = 0; i < nums.length; i++) {
            result ^= nums[i];
        }
        //找出上面异或结果第一次出现1的位置；出现1说明待找出这2个数相应位置上的数是不同的(一个是0，一个是1)
        int index = 0;
        while ((result & 1) != 1) {
            result = result >> 1;
            index++;
        }

        int n1 = 0;
        int n2 = 0;

        //所有该位置上是1的异或，是0的异或，找出这2个数
        for (int i = 0; i < nums.length; i++) {
            int tmp = nums[i] >> index;
            if ((tmp & 1) == 1) {
                n1 ^= nums[i];
            } else {
                n2 ^= nums[i];
            }
        }
        res[0] = n1;
        res[1] = n2;
        return res;
    }

    /**
     * 判断一个数是否是 2的幂次方
     *
     * @param n
     * @return
     */
    public static boolean isPowerOfTwo(int n) {
        if (n <= 0) {
            return false;
        }
        int cnt = 0;
        while (n != 0) {
            if ((n & 1) == 1) {
                cnt++;
                if (cnt > 1) {
                    return false;
                }
            }
            n = n >> 1;
        }
        return true;
    }

    /**
     * 判断一个数是否是 2的幂次方
     * 高逼格
     *
     * @param n
     * @return
     */
    public static boolean isPowerOfTwo2(int n) {
        return (n & (n - 1)) == 0;
    }

    public static int rangeBitwiseAnd(int m, int n) {
        if (m == Integer.MAX_VALUE) {
            return m;
        }
        int res = m;
        for (int i = m + 1; i <= n; i++) {
            res &= i;
            if (res == 0 || i == Integer.MAX_VALUE) {//因为当i == 2147483647时， i++ 就溢出了，会变成 - 2147483648，所以就死循环了
                break;
            }
        }
        return res;
    }

    public static int rangeBitwiseAnd2(int m, int n) {
        int zeroNum = 0;//0的个数
        //因为 m & (m+1) 最低位肯定是0
        while (m < n) {
            zeroNum++;
            m >>= 1;
            n >>= 1;
        }
        return m << zeroNum;
    }

    /**
     * 给定一个大小为 n 的数组，找到其中的多数元素。多数元素是指在数组中出现次数大于 [n/2] 的元素。
     */

    public int majorityElement(int[] nums) {
//        Arrays.sort(nums);
//        return nums[nums.length/2];
        int major = 0;
        int cnt = 0;
        for (int i = 0; i < nums.length; i++) {
            if (cnt == 0) {
                major = nums[i];
            }
            if (nums[i] == major) {
                cnt++;
            } else {
                cnt--;
            }
        }
        return major;
    }

    public static List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> output = new ArrayList();
        output.add(new ArrayList<Integer>());

        for (int num : nums) {
            List<List<Integer>> newSubsets = new ArrayList();
            for (List<Integer> curr : output) {
                List<Integer> tmp = new ArrayList<>(curr);
                tmp.add(num);
                newSubsets.add(tmp);
            }
            for (List<Integer> curr : newSubsets) {
                output.add(curr);
            }
        }
        return output;
    }

    /**
     * 重复的DNA序列 使用HashSet
     * 编写一个函数来查找 DNA 分子中所有出现超过一次的 10 个字母长的序列（子串）
     *
     * @param s
     * @return
     */
    public static List<String> findRepeatedDnaSequences(String s) {
        Set<String> set = new HashSet<>();
        Set<String> tmpSet = new HashSet<>();
        int index = 0;
        int gap = 10;
        int len = s.length();
        while (index < len - gap + 1) {
            String tmp = s.substring(index, index + gap);
            if (tmpSet.contains(tmp))
                set.add(tmp);
            tmpSet.add(tmp);
            index++;
        }
        return new ArrayList<>(set);
    }

    /**
     * 给定一个非负整数 num。对于 0 ≤ i ≤ num 范围中的每个数字 i ，计算其二进制数中的 1 的数目并将它们作为数组返回。
     * <p>
     * 输入: 5
     * 输出: [0,1,1,2,1,2]
     * <p>
     * 对于所有的数字，只有两类：
     * 奇数：二进制表示中，奇数一定比前面那个偶数多一个 1，因为多的就是最低位的 1。
     * 偶数：二进制表示中，偶数中 1 的个数一定和除以 2 之后的那个数一样多。
     * 因为最低位是 0，除以 2 就是右移一位，也就是把那个 0 抹掉而已，所以 1 的个数是不变的。
     */

    public static int[] countBits(int num) {
        int[] res = new int[num + 1];
        res[0] = 0;//0  1的个数为0
        for (int i = 1; i <= num; i++) {
            if ((i & 1) == 0) {//偶数
                res[i] = res[i / 2];
            } else {
                res[i] = res[i - 1] + 1;
            }
        }
        return res;
    }

    /**
     * 此方法效率不高
     *
     * @param num
     * @return
     */
    public static int[] countBits2(int num) {
        int[] res = new int[num + 1];
        res[0] = 0;//0  1的个数为0
        for (int i = 1; i <= num; i++) {
            res[i] = getOneCnt(i);
        }
        return res;
    }

    /**
     * 获取 num 中二进制 1 的个数
     *
     * @param num
     * @return
     */
    private static int getOneCnt(int num) {
        int cnt = 0;
        while (num != 0) {
            if ((num & 1) != 0) {
                cnt++;
            }
            num = num >> 1;
        }
        return cnt;
    }

    /**
     * 判断 n 是不是 4 的幂次方 4， 16， 64 ...
     *
     * @param n
     * @return
     */
    public boolean isPowerOfFour(int n) {
        while (n != 0 && n % 4 == 0) {
            n /= 4;
        }
        return n == 1;
    }

    /**
     * K 个一组翻转链表
     * <p>
     * 给你这个链表：1->2->3->4->5
     * 当 k = 2 时，应当返回: 2->1->4->3->5
     * 当 k = 3 时，应当返回: 3->2->1->4->5
     *
     * @param head
     * @param k
     * @return
     */
    public static ListNode reverseKGroup(ListNode head, int k) {
        ListNode L = new ListNode(-1);
        ListNode r = L;
        ListNode p = head;
        ListNode tail = head;
        ListNode tmp = head;
        int cnt = 0;

        while (p != null && cnt < k + 1) {
            p = p.next;
            cnt++;
            if (cnt == k) {
                while (head != p) {
                    tmp = tmp.next;
                    head.next = r.next;
                    r.next = head;
                    head = tmp;
                }
                r = tail;
                tail = p;
                cnt = 0;
            }
        }
        if (tail != null) {
            r.next = tail;
        }
        return L.next;
    }

    /**
     * 不使用运算符 + 和 - ​​​​​​​，计算两整数 ​​​​​​​a 、b ​​​​​​​之和。
     *
     * @param a
     * @param b
     * @return
     */
    public static int getSum(int a, int b) {

        while (b != 0) {
            int sum = a ^ b; //异或 表示 二进制下不进位的加法
            int carry = a & b;//表示进位值
            carry = carry << 1; //有进位 则 左移 表示向前进一位
            a = sum;
            b = carry;
        }
        return a;
    }


    /**
     * 给定两个字符串 s 和 t，它们只包含小写字母。
     * 字符串 t 由字符串 s 随机重排，然后在随机位置添加一个字母。
     * 请找出在 t 中被添加的字母。
     * <p>
     * 输入：
     * s = "abcd"
     * t = "abcde"
     * <p>
     * 输出：
     * e
     *
     * @param s
     * @param t
     * @return
     */
    public char findTheDifference(String s, String t) {
        int res = 0;
        for (int i = 0, len = s.length(); i < len; i++) {
            res ^= s.charAt(i);
        }

        for (int i = 0, len = t.length(); i < len; i++) {
            res ^= t.charAt(i);
        }
        return (char) res;
    }


    public char findTheDifference2(String s, String t) {
        int res = 0;
        int res2 = 0;
        for (int i = 0, len = s.length(); i < len; i++) {
            res += s.charAt(i);
        }

        for (int i = 0, len = t.length(); i < len; i++) {
            res2 += t.charAt(i);
        }
        return (char) (res2 - res);
    }

    /**
     * 数字转16进制
     *
     * @param num
     * @return
     */
    public String toHex(int num) {
        if (num == 0)
            return "0";
        String hexString = "0123456789abcdef";
        String res = "";
        while (num != 0 && res.length() < 8) {//ffffffff 就是 二进制 32位都是1
            res = hexString.charAt(num & 0xf) + res;
            num = num >> 4;
        }
        return res;
    }

    /**
     * 汉明距离 : 两个整数之间的汉明距离指的是这两个数字对应二进制位不同的位置的数目。
     * @param x
     * @param y
     * @return
     *
     *  思路：2个数异或，对应的二进制不同 结果为1 ，所以 只需要计算 两个数的异或 ，然后计算异或后结果中 1 的个数
     */
    public int hammingDistance(int x, int y) {
        int res = x ^ y;
        int cnt = 0;
        while (res != 0){//计算0的个数的很妙的方法
            cnt++;
            res &= res -1;
        }
        return cnt;
    }

    /**
     * 汉明距离总和:计算一个数组中，任意两个数之间汉明距离的总和。
     * @param nums
     * @return
     *   7  :   0 1 1 1
     *   11 :   1 0 1 1
     *   13 :   1 1 0 1
     *
     *  从 每行的最后一位看，7， 11 ，13最后一位都是1， 所以汉明距离的个数为0，
     *  倒数 第二位，汉明结果为 2， 数字7的 1 和 数字 13 的 0 是一个，数字 11 的 1 和13的0 是另一个，所以为2；
     *  以此类推，可以看出 总和 就是 每个二进制位 0 的 个数乘以 1 的个数
     */
    public int totalHammingDistance(int[] nums) {
        int [] bitArray = new int[32];
        for (int i = 0; i < 32; i++){
            for (int j = 0, len = nums.length; j < len; j++){
                bitArray[i] += (nums[j] >> i) & 1;
            }
        }
        int res = 0;
        for (int i = 0, len = nums.length; i < 32; i++){
            res += (len - bitArray[i])*bitArray[i];
        }
        return res;
    }

}
