package daily.yiyuan.com.test_java.leet_code;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2019/12/30
 * <p>
 * 不是链表、多线程之类的
 */
public class TestNormal {
    public static void main(String[] args) {

        String digits = "234";
//        letterCombinations(digits);

        fourSum(new int[]{1, 1, 2, 3, 5}, 7);

        convert("abcde", 3);
        intToRoman(856);
        combinationSum(new int[]{1, 2}, 3);
        reverse(-1020300);


        int val = myAtoi("2247483647");
        System.out.println("val = " + val);

        int[] num1 = {1, 2, 3};
        int[] num2 = {2, 4};

        getKth(num1, 0, num1.length - 1, num2, 0, num2.length - 1, (num1.length + num2.length) / 2);

        romanToInt("MCMXCIV");

        String d = longestCommonPrefix(new String[]{"c", "acc", "ccc"});
        System.out.println("longestCommonPrefix = " + d);

        divide(-2147483648, 2);
        int index = search(new int[]{4, 5, 6, 7, 0, 1, 2}, 0);
        System.out.println("search = " + index);

        searchInsert(new int[]{1, 3, 5, 6}, 5);

        String a = "99";
        String b = "656";
        multiply(a, b);
    }

    /**
     * 判断一个整数是否是回文数，
     *
     * @param x
     * @return
     */
    private static boolean isPalindrome(int x) {
        if (x < 0) {//负数肯定不是回文数
            return false;
        } else {
            String str = String.valueOf(x);
            int len = str.length();
            for (int i = 0; i < len / 2; i++) {
                if (str.charAt(i) != str.charAt(len - i - 1)) {
                    return false;
                }
            }
        }
        return true;


    }

    /**
     * 给定一个仅包含数字 2-9 的字符串，返回所有它能表示的字母组合。
     *
     * @param digits
     * @return
     */
    public static List<String> letterCombinations(String digits) {
        if (digits == null || digits.length() == 0) {
            return new ArrayList<>();
        }

        Map<Character, List<String>> map = new HashMap<>();
        List<String> two = new ArrayList<>();
        two.add("a");
        two.add("b");
        two.add("c");

        List<String> three = new ArrayList<>();
        three.add("d");
        three.add("e");
        three.add("f");

        List<String> four = new ArrayList<>();
        four.add("g");
        four.add("h");
        four.add("i");

        List<String> five = new ArrayList<>();
        five.add("j");
        five.add("k");
        five.add("l");

        List<String> six = new ArrayList<>();
        six.add("m");
        six.add("n");
        six.add("o");

        List<String> seven = new ArrayList<>();
        seven.add("p");
        seven.add("q");
        seven.add("r");
        seven.add("s");

        List<String> eight = new ArrayList<>();
        eight.add("t");
        eight.add("u");
        eight.add("v");

        List<String> nigh = new ArrayList<>();
        nigh.add("w");
        nigh.add("x");
        nigh.add("y");
        nigh.add("z");

        map.put('2', two);
        map.put('3', three);
        map.put('4', four);
        map.put('5', five);
        map.put('6', six);
        map.put('7', seven);
        map.put('8', eight);
        map.put('9', nigh);

        int size = digits.length();
        if (size == 1) {
            return map.get(digits.charAt(0));
        }
        List<String>[] lists = new List[size];
        for (int i = 0; i < size; i++) {
            lists[i] = map.get(digits.charAt(i));
        }
        List<String> tmpRes = lists[0];
        for (int i = 0; i < size - 1; i++) {
            tmpRes = merge2List(tmpRes, lists[i + 1]);
        }
        return tmpRes;
    }


    public static List<String> merge2List(List<String> left, List<String> right) {
        List<String> list = new ArrayList<>();
        for (String s : left) {
            for (String v : right) {
                String val = s + v;
                list.add(val);
            }
        }
        return list;
    }


    /**
     * 四数之和 等于目标值
     *
     * @param nums
     * @param target
     * @return
     */
    public static List<List<Integer>> fourSum(int[] nums, int target) {
        List<List<Integer>> res = new ArrayList<>();
        if (nums == null || nums.length < 4) {
            return res;
        }

        Arrays.sort(nums);//从小到大排序
        int len = nums.length;

        for (int i = 0; i < len - 3; i++) {
            if (nums[i] * 4 > target) {//最小的4个数都大于 target 就找不到了
                break;
            }

            if (i > 0 && nums[i] == nums[i - 1]) {//过滤起点相同的元素，避免重复结果
                continue;
            }

            for (int j = len - 1; j - i > 2; j--) {
                //排序后最大sum值
                if (nums[j] * 4 < target) {
                    break;
                }
                //过滤终点相同的元素，避免重复结果
                if (j < len - 1 && nums[j] == nums[j + 1]) {
                    continue;
                }

                int left = i + 1;
                int right = j - 1;
                while (left < right) {
                    int sum = nums[i] + nums[left] + nums[right] + nums[j];
                    if (sum == target) {
                        res.add(Arrays.asList(nums[i], nums[left], nums[right], nums[j]));
                        while (left < len - 3 && nums[left] == nums[left + 1]) {////过滤起点相同的元素，避免重复结果
                            left++;
                        }

                        while (right > 3 && nums[right] == nums[right - 1]) {//过滤终点相同的元素，避免重复结果
                            right--;
                        }
                        left++;
                        right--;
                    } else if (sum > target) {
                        while (right > 3 && nums[right] == nums[right - 1]) {//过滤终点相同的元素，避免重复结果
                            right--;
                        }
                        right--;
                    } else {
                        while (left < len - 3 && nums[left] == nums[left + 1]) {//过滤起点相同的元素，避免重复结果
                            left++;
                        }
                        left++;
                    }
                }
            }
        }
        return res;

    }

    /**
     * 无重复最长子串的个数
     *
     * @param str
     * @return
     */
    public static int maxSubStr(String str) {
        if (str == null) {
            return 0;
        }
        int res = 0;
        int start = 0;
        int len = str.length();
        Map<Character, Integer> map = new HashMap<>();

        for (int end = 0; end < len; end++) {
            Character ch = str.charAt(end);
            if (map.containsKey(ch)) {
                start = Math.max(start, map.get(ch));
            }
            res = Math.max(res, end - start + 1);
            map.put(ch, end + 1);
        }
        return res;
    }


    /**
     * Z 字型变换
     *
     * @param s
     * @param numRows
     * @return 比如输入字符串为 "LEETCODEISHIRING" 行数为 3 时，排列如下：
     * <p>
     * L   C   I   R
     * E T O E S I I G
     * E   D   H   N
     * <p>
     * 输出： "LCIRETOESIIGEDHN"
     */
    public static String convert(String s, int numRows) {
        if (numRows < 2) return s;
        List<StringBuilder> rows = new ArrayList<>();
        for (int i = 0; i < numRows; i++) {
            rows.add(new StringBuilder());
        }
        int i = 0, flag = -1;
        for (char c : s.toCharArray()) {
            rows.get(i).append(c);
            if (i == 0 || i == numRows - 1) {
                flag = -flag;
            }
            i += flag;
        }
        StringBuilder res = new StringBuilder();
        for (StringBuilder row : rows) res.append(row);
        return res.toString();
    }

    /**
     * 整数转罗马数字
     * <p>
     * 字符          数值
     * I             1
     * V             5
     * X             10
     * L             50
     * C             100
     * D             500
     * M             1000
     * 例如， 罗马数字 2 写做 II ，即为两个并列的 1。12 写做 XII ，即为 X + II 。 27 写做  XXVII, 即为 XX + V + II 。
     * <p>
     * 通常情况下，罗马数字中小的数字在大的数字的右边。但也存在特例，例如 4 不写做 IIII，而是 IV。数字 1 在数字 5 的左边，所表示的数等于大数 5 减小数 1 得到的数值 4 。同样地，数字 9 表示为 IX。这个特殊的规则只适用于以下六种情况：
     * <p>
     * I 可以放在 V (5) 和 X (10) 的左边，来表示 4 和 9。
     * X 可以放在 L (50) 和 C (100) 的左边，来表示 40 和 90。 
     * C 可以放在 D (500) 和 M (1000) 的左边，来表示 400 和 900。
     * 给定一个整数，将其转为罗马数字。输入确保在 1 到 3999 的范围内。
     *
     * @param num
     * @return
     */
    public static String intToRoman(int num) {
        Map<Integer, String> map = new HashMap<>();
        map.put(1, "I");
        map.put(4, "IV");
        map.put(5, "V");
        map.put(9, "IX");
        map.put(10, "X");
        map.put(40, "XL");
        map.put(50, "L");
        map.put(90, "XC");
        map.put(100, "C");
        map.put(400, "CD");
        map.put(500, "D");
        map.put(900, "CM");
        map.put(1000, "M");

        int[] val = {1, 4, 5, 9, 10, 40, 50, 90, 100, 400, 500, 900, 1000};
        int len = val.length;
        StringBuilder sb = new StringBuilder();
        int sum = num;
        for (int i = len - 1; i >= 0; i--) {
            if (val[i] == sum) {
                sum -= val[i];
                sb.append(map.get(val[i]));
                if (sum == 0) {
                    break;
                }
            } else if (val[i] < sum) {
                sum -= val[i];
                sb.append(map.get(val[i]));
                i++;
                if (sum == 0) {
                    break;
                }
            }
        }

        return sb.toString();
    }

    public static List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> res = new ArrayList<>();
        Arrays.sort(candidates);
        //System.out.println(candidates);
        backtrack(candidates, target, res, 0, new ArrayList<Integer>());
        return res;
    }

    private static void backtrack(int[] candidates, int target, List<List<Integer>> res, int i, ArrayList<Integer> tmp_list) {
        System.out.println("target = " + target + ", i = " + i);
        if (target < 0) return;
        if (target == 0) {
            res.add(new ArrayList<>(tmp_list));
            return;
        }
        for (int start = i; start < candidates.length; start++) {
            System.out.println("start = " + start + ", target = " + target);
            if (target < candidates[start])
                break;
            tmp_list.add(candidates[start]);
            System.out.println(tmp_list);
            backtrack(candidates, target - candidates[start], res, start, tmp_list);
            tmp_list.remove(tmp_list.size() - 1);
            System.out.println("after delete tmp_list = " + tmp_list);
        }
    }


    /**
     * 整数反转
     * 输入: 123
     * 输出: 321
     * <p>
     * 输入: -123
     * 输出: -321
     * <p>
     * 输入: 120
     * 输出: 21
     * <p>
     * 假设我们的环境只能存储得下 32 位的有符号整数，则其数值范围为 [−231,  231 − 1]。请根据这个假设，如果反转后整数溢出那么就返回 0。
     *
     * @param x
     * @return
     */
    public static int reverse(int x) {
        // 遇到这种问题不要只想着转成String来做，因为会溢出；为什么会溢出呢，如果一个数反转后大于Integer.MAX_VALUE，因为要 return int, 所以调用 Integer.valueOf 会报错；我之前就是那样报错的
        int ans = 0;
        while (x != 0) {
            int pop = x % 10;
            if (ans > Integer.MAX_VALUE / 10 || (ans == Integer.MAX_VALUE / 10 && pop > 7)) // 7 是 MAX_VALUE 的个位数
                return 0;
            if (ans < Integer.MIN_VALUE / 10 || (ans == Integer.MIN_VALUE / 10 && pop < -8)) // -8 是 MIN_VALUE 的个位数
                return 0;
            ans = ans * 10 + pop;
            x /= 10;
        }
        return ans;
    }

    public static String deleteFirstZero(String val) {
        if (val.charAt(0) == '0') {
            return deleteFirstZero(val.substring(1, val.length()));
        } else {
            return val;
        }
    }

    /**
     * 字符串转数字， 如果第一个非空字符不是加号或者减号或者数字 直接返回0，
     *
     * @param str
     * @return
     */

    public static int myAtoi(String str) {
        if (str == null) {
            return 0;
        }
        str = str.trim();
        if (str.length() < 1) {
            return 0;
        }

        boolean isNegative = false;
        if (str.charAt(0) == '-') {
            if (str.length() > 1) {
                isNegative = true;
                str = str.substring(1, str.length());
            } else {
                return 0;
            }
        } else if (str.charAt(0) == '+') {
            str = str.substring(1, str.length());
            if (str.length() < 1) {
                return 0;
            }
        }

        if (str.charAt(0) > '9' || str.charAt(0) < '0') {
            return 0;
        }

        int len = str.length();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < len; i++) {
            char ch = str.charAt(i);
            if (ch >= '0' && ch <= '9') {
                sb.append(ch);
            } else {
                break;
            }
        }

        String res = sb.toString();
        int len2 = res.length();
        int ans = 0, tmp;

        for (int i = 0; i < len2; i++) {
            tmp = res.charAt(i) - '0';
            if (ans > Integer.MAX_VALUE / 10 || (ans == Integer.MAX_VALUE / 10 && tmp > (isNegative ? 8 : 7))) {
                if (isNegative) {
                    return Integer.MIN_VALUE;
                } else {
                    return Integer.MAX_VALUE;
                }
            }
            ans = ans * 10 + tmp;
        }
        return isNegative ? -ans : ans;
    }


    /**
     * 求2个有序数组的中位数
     */
    public static double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int n = nums1.length;
        int m = nums2.length;
        int left = (n + m + 1) / 2;
        int right = (n + m + 2) / 2;
        //将偶数和奇数的情况合并，如果是奇数，会求两次同样的 k 。
        return (getKth(nums1, 0, n - 1, nums2, 0, m - 1, left) + getKth(nums1, 0, n - 1, nums2, 0, m - 1, right)) * 0.5;
    }

    /**
     * 寻找2个有序数组的第K小的数
     *
     * @param nums1
     * @param start1
     * @param end1
     * @param nums2
     * @param start2
     * @param end2
     * @param k
     * @return
     */
    public static int getKth(int[] nums1, int start1, int end1, int[] nums2, int start2, int end2, int k) {
        int len1 = end1 - start1 + 1;//num1 的长度
        int len2 = end2 - start2 + 1;//num2 的长度
        //让 len1 的长度小于 len2，这样就能保证如果有数组空了，一定是 len1
        if (len1 > len2) return getKth(nums2, start2, end2, nums1, start1, end1, k);
        if (len1 == 0) return nums2[start2 + k - 1];

        if (k == 1) return Math.min(nums1[start1], nums2[start2]);

        int i = start1 + Math.min(len1, k / 2) - 1; //每次都比较K/2个数，如果数组的长度小于K/2 那么就比较数组长度的个数
        int j = start2 + Math.min(len2, k / 2) - 1;

        if (nums1[i] > nums2[j]) {
            return getKth(nums1, start1, end1, nums2, j + 1, end2, k - (j - start2 + 1));//最后一个参数表示的是 K 减去已经比较过的数了。
        } else {
            return getKth(nums1, i + 1, end1, nums2, start2, end2, k - (i - start1 + 1));
        }
    }


    /**
     * 罗马数字转 整数
     *
     * @param s
     * @return 前值小于后值，减去前值
     * 前值大于或等于后值，加上前值
     * 最后一个值必然是加上的
     */
    public static int romanToInt(String s) {
        if (s == null || s.trim().length() < 1) {
            return 0;
        }

        int sum = 0;
        int preNum = getValue(s.charAt(0));
        for (int i = 1; i < s.length(); i++) {
            int num = getValue(s.charAt(i));
            if (preNum < num) {
                sum -= preNum;
            } else {
                sum += preNum;
            }
            preNum = num;
        }
        sum += preNum;
        return sum;
    }

    private static int getValue(char val) {
        switch (val) {
            case 'I':
                return 1;
            case 'V':
                return 5;
            case 'X':
                return 10;
            case 'L':
                return 50;
            case 'C':
                return 100;
            case 'D':
                return 500;
            case 'M':
                return 1000;
            default:
                return 0;
        }
    }

    /**
     * 最长公共前缀
     *
     * @param strs
     * @return 二分查找法
     */
    public static String longestCommonPrefix(String[] strs) {
        if (strs == null || strs.length < 1) {
            return "";
        }

        int minLen = Integer.MAX_VALUE;
        for (String str : strs) {
            minLen = Math.min(minLen, str.length());
        }
        int low = 1;
        int high = minLen;
        while (low <= high) {
            int mid = ((high - low) >>> 1) + low; //防止 low  和 high 数值很大时 发生溢出
            if (isCommonPrefix(strs, mid)) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return strs[0].substring(0, (low + high) / 2);
    }

    private static boolean isCommonPrefix(String[] strs, int index) {
        String first = strs[0].substring(0, index);
        for (int i = 1; i < strs.length; i++) {
            if (!strs[i].startsWith(first))
                return false;
        }
        return true;
    }

    /**
     * 两整数相除，不可以用乘除 注意溢出
     */
    public static int divide(int dividend, int divisor) {
        if (dividend == 0) {
            return 0;
        }

        if (dividend == Integer.MIN_VALUE && divisor == -1) { //溢出
            return Integer.MAX_VALUE;
        }

        boolean isNegative = (dividend ^ divisor) < 0 ? true : false; //利用异或来判断结果是否为负数
        long t = Math.abs((long) dividend); //这里面必须强转为 long 类型， 不然 MIN_VALUE 的绝对值会溢出
        long d = Math.abs((long) divisor);

        int ans = 0;
        for (int i = 31; i >= 0; i--) {
            if ((t >> i) >= d) {
                ans += 1 << i;
                t -= d << i;
            }
        }
        return isNegative ? -ans : ans;
    }


    /**
     * 搜索旋转排序数组
     * 假设按照升序排序的数组在预先未知的某个点上进行了旋转。
     * <p>
     * ( 例如，数组 [0,1,2,4,5,6,7] 可能变为 [4,5,6,7,0,1,2] )。
     * 搜索一个给定的目标值，如果数组中存在这个目标值，则返回它的索引，否则返回 -1 。
     * 比如说 target = 6, 返回2 ，target = 8 返回-1
     *
     * @param nums
     * @param target
     * @return
     */
    public static int search(int[] nums, int target) {
        return binarySearch(nums, 0, nums.length - 1, target);
    }

    public static int binarySearch(int[] nums, int low, int high, int target) {
        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (target == nums[mid]) {
                return mid;
            } else {
                if (nums[low] <= nums[mid]) {//表示前半部分有序
                    if (target >= nums[low] && target < nums[mid]) {
                        return binarySearch(nums, low, mid - 1, target);
                    } else {
                        low = mid + 1;
                    }
                } else {//表示后半部分有序
                    if (target > nums[mid] && target <= nums[high]) {
                        return binarySearch(nums, mid + 1, high, target);
                    } else {
                        high = mid - 1;
                    }
                }
            }
        }
        return -1;
    }

    //---------------------------------------------------搜索插入位置--------------------------------------------------

    /**
     * 给定一个排序数组和一个目标值，在数组中找到目标值，并返回其索引。如果目标值不存在于数组中，返回它将会被按顺序插入的位置。
     * <p>
     * 你可以假设数组中无重复元素。
     *
     * @param nums
     * @param target
     * @return
     */
    public static int searchInsert(int[] nums, int target) {

        if (nums == null || nums.length < 1) {
            return 0;
        }

//        for (int i = 0; i<nums.length; i++){
//            if (target <= nums[i]){
//                return i;
//            }
//        }

        int low = 0;
        int high = nums.length - 1;

        while (low <= high) {
            int mid = low + ((high - low) >> 1);
            if (target == nums[mid]) {
                return mid;
            } else if (target > nums[mid]) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return low;
    }

    //----------------------------------------盛最多水的容器-------------------------------------------

    public static int maxArea(int[] height) {
        if (height == null || height.length == 0) {
            return 0;
        }

        int i = 0;
        int j = height.length - 1;
        int res = 0;
        int minHeight = 0;
        while (i < j) {
            minHeight = Math.min(height[i], height[j]);//高度的最小值
            res = Math.max(res, minHeight * (j - i));// minHeight*(j - i) 表示 盛水面积
            //高度小的那边往里面移动1位
            if (height[i] < height[j]) {
                i++;
            } else {
                j--;
            }
        }
        return res;
    }


    //---------------------------------------------------下一个排列---------------------------------------------------------------------

    /**
     * 解题思路：
     * 1、从数组的右边开始往左边找，直到找到 nums[i] < nums[i+1]；说明 i 的右边是 倒序的，
     * 2、把 i 右边的数据进行升序排列
     * 3、把 i 右边排序后的数据和 nums[i]比较，第一个大于nums[i]的交换
     * 4、如果 i<0 说明 数组本来就是 降序排列的， 根据题目要求 返回最小的数组，即对数组做升序排列即可
     *
     * @param nums
     */
    public void nextPermutation(int[] nums) {
        if (nums == null || nums.length == 0) {
            return;
        }
        int len = nums.length;
        int i = len - 2;
        while (i >= 0 && nums[i] >= nums[i + 1]) {
            i--;
        }
        if (i < 0) {
            reverse(nums, 0);
        } else {
            reverse(nums, i + 1);
            for (int j = i + 1; j < len; j++) {
                if (nums[j] > nums[i]) {
                    swap(nums, i, j);
                    break;
                }
            }
        }
    }

    public static void reverse(int[] nums, int start) {
        int end = nums.length - 1;
        while (start < end) {
            swap(nums, start, end);
            start++;
            end--;
        }
    }

    public static void swap(int[] nums, int i, int j) {
        int tmp = nums[i];
        nums[i] = nums[j];
        nums[j] = tmp;
    }


    //----------------------------------------------------2个字符串相乘--------------------------------------------------------------------
    public static String multiply(String num1, String num2) {
        if (num1.equals("0") || num2.equals("0")) {
            return "0";
        }
        int len1 = num1.length();
        int len2 = num2.length();

        if (len1 < len2) {
            String tmp = num2;
            num2 = num1;
            num1 = tmp;
            len1 = num1.length();
            len2 = num2.length();
        }
        List<String> list = new ArrayList<>();
        int [] result = new int[len1 + len2];
        int mul1 = 0;
        int mul2 = 0;
        String sum = "";
       int val; //向高位进位的数
        for (int i = len2 - 1; i > -1; i--) {
            val = 0;
            mul1 = num2.charAt(i) - '0';
            for (int j = len1 - 1; j > -1; j--) {
                mul2 = num1.charAt(j) - '0';
                int v = mul1 * mul2;
                v += val;
                if (v >= 10) {
                    val = v / 10;
                } else {
                    val = 0;
                }
                if (j != 0) {
                    v = v % 10;
                }
                result[i+j+1] = v;
                sum = v +sum;
            }


        }


        return String.valueOf("");
    }


}
