package daily.yiyuan.com.test_java.leet_code;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import daily.yiyuan.com.test_java.data_structure.HeapSort;
import daily.yiyuan.com.test_java.data_structure.SearchUtils;

import static daily.yiyuan.com.test_java.data_structure.SearchUtils.binarySearch;

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
        findLongestSubString();
        findMaxHuiwenString();
        findMissingOneNum();
        findMissing2Num();
        findMissing2Number2();

        int[] arr = {1, 1, 2, 3, 2};
//        removeDuplicates(arr);
        int len = removeElement(arr, 2);
        System.out.println("len = " + len);
//        swapListNode();

        int[] a1 = {1, 2, 6, 8};
        int[] a2 = {3, 5, 9};
        ListNode l1 = initListNodeWithoutHead(a1);
        ListNode l2 = initListNodeWithoutHead(a2);
        ListNode L = mergeTwoLists(l1, l2);
        ListNode r = L;
        while (r != null) {
            System.out.println(r.val + "........");
            r = r.next;
        }

        int[] a3 = {1, 3, 4, 7, 9};
        int index = binarySearch(a3, 9);
        System.out.println("index = " + index);

        int[] a4 = {1, 3};
        int[] ss = searchRange(a4, 1);
        System.out.println("ss[0]=" + ss[0] + ", ss[1] = " + ss[1]);

        heapSort();

        findSubStr();

        findMinFullSubString();

        int[] arrs = {7, 2, 4, 7, 6, 8, 1};
        listNodeInsertSort(arrs);
        int[] arrs2 = {-1, -3, 1, 2, 3, 5};
        int sum = largestSumAfterKNegations(arrs2, 1);

        partitionLabels("tabcqbdcmnj");

        int [] arr3 = {3, 1, 3, 3};
        singleNumber(arr3);

    }

    private static void listNodeInsertSort(int[] arrs) {
        ListNode listNode = initListNodeWithoutHead(arrs);
        ListNode head = insertSort(listNode);
        ListNode s = head;
        while (s != null) {
            System.out.println("---" + s.val + "---");
            s = s.next;
        }
    }

    private static void findMinFullSubString() {
        String s = "a";
        String t = "aa";
        String minString = minWindow(s, t);
        System.out.println("minString = " + minString);
    }

    private static void findSubStr() {
        String s = "lingmindraboofooowingdingbarrwingmonkeypoundcake";
        String[] words = {"fooo", "barr", "wing", "ding", "wing"};
        List<Integer> integerList = findSubstring(s, words);
        System.out.println("substring index = " + integerList);
    }

    private static void heapSort() {
        int[] A = {3, 7, 2, 11, 3, 4, 9, 2, 18, 0};
        HeapSort heapSort = new HeapSort();
        heapSort.buildMaxHeap(A);
        heapSort.heapSort();
    }

    private static void swapListNode() {
        int[] arr2 = {1, 2, 3, 4, 2};
        ListNode head = initListNodeWithoutHead(arr2);
        ListNode L = swapPairs(head);
        ListNode r = L;
        while (r != null) {
            System.out.println(r.val + "........");
            r = r.next;
        }
    }

    private static void findMissing2Number2() {
        Integer array[] = {7, 9, 8, 1, 4, 2, 3, 5, 6, 10, 11, 12};
        Integer subArray[] = {1, 2, 4, 5, 6, 7, 8, 9, 11, 12};
        getMissNumber(array, subArray);
    }

    private static void findMissing2Num() {
        int[] arrMissingTwoNum = {5, 1, 3, 4, 7};
        int[] arr = getMissingTwoNumber(arrMissingTwoNum, 7);
        System.out.println("missingTwoNum = " + arr[0] + ", " + arr[1]);
    }

    private static void findLongestSubString() {
        int length = lengthOfLongestSubstring("abcbdme");
        System.out.println("length = " + length);
    }

    private static void findMaxHuiwenString() {
        String isHuiwen = maxHuiwenString("abbabb");
        System.out.println("isHuiwen = " + isHuiwen);
    }

    private static void findMissingOneNum() {
        int[] missingArr = {3, 5, 2, 1};
        int missingNum = getMissingNum2(missingArr, 5);
        int missingNum2 = getMissingNum(missingArr, 5);
        System.out.println("missingNum = " + missingNum + ", missingNum2 = " + missingNum2);
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
        Arrays.sort(nums);

        for (int k = 0; k< nums.length -2; k++){
            if (nums[k] > 0)
                break;
            if (k > 0 && nums[k] == nums[k-1])//说明这个数在上一次已经查找了，避免重复
                continue;
            int i = k+1;
            int j = nums.length - 1;
            while (i < j){
                int sum = nums[k] + nums[i] + nums[j];
                if (sum > 0){
                    while (i<j && nums[j] == nums[--j]){}
                }else if (sum < 0){
                    while (i<j && nums[i] == nums[++i]){}
                }else {
                    List<Integer> tmpList = new ArrayList<>();
                    tmpList.add(nums[k]);
                    tmpList.add(nums[i]);
                    tmpList.add(nums[j]);
                    res.add(tmpList);
                    while (i < j && nums[i] == nums[++i]){}
                    while (i<j && nums[j] == nums[--j]){}
                }
            }
        }
        return res;
    }

    /**
     *  最接近的3数之和 ---- 例如，给定数组 nums = [-1，2，1，-4], 和 target = 1； 与 target 最接近的三个数的和为 2. (-1 + 2 + 1 = 2).
     * @param nums
     * @param target
     * @return
     */
    public static int threeSumClosest(int[] nums, int target) {
        int sum = Integer.MAX_VALUE;
        int res = Integer.MIN_VALUE;
        Arrays.sort(nums);
        for (int k = 0; k < nums.length -2; k++){
            int i = k+1;
            int j = nums.length -1;
            while (i < j){
                int tmp = nums[k] + nums[i] + nums[j];
                int diff = Math.abs(tmp - target);
                if (diff <= sum){
                    sum = diff;
                    res = tmp;
                }
               if (tmp > target){
                   j--;
               }else if (tmp < target){
                   i++;
               }else {
                   return tmp;
               }
            }
        }
        return res;
    }

    //.....................................2.测试两个数的和...................................
    private static void testAddTwoNumbers(int[] nums, int[] nums2) {
        ListNode head1 = initListNodeWithHead(nums);
        ListNode head2 = initListNodeWithHead(nums2);

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

    /**
     * 初始化有头结点的链表
     *
     * @param nums
     * @return
     */
    public static ListNode initListNodeWithHead(int[] nums) {
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

    /**
     * 初始化无头结点的链表
     *
     * @param nums
     * @return
     */
    public static ListNode initListNodeWithoutHead(int[] nums) {
        ListNode head, r;
        head = new ListNode(nums[0]);
        r = head;
        for (int i = 1; i < nums.length; i++) {
            ListNode p = new ListNode(nums[i]);
            r.next = p;
            r = p;
        }
        return head;
    }

    //.................................................................3、无重复字符的最长子串.................................

    public static int lengthOfLongestSubstring(String s) {
        int n = s.length(), ans = 0, start = 0;
        Map<Character, Integer> map = new HashMap<>();
        for (int end = 0; end < n; end++) {
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
        char[][] chars = new char[numRows][coloum];
        for (int i = 0; i < len; i++) {

        }
        return res;
    }

    //...............................................6. 1-n个自然数中缺失1个数，找出这个数................................

    /**
     * 方法1：将n个自然数之和减去缺失一个自然数的数组之和
     *
     * @param arrs
     * @param n
     * @return
     */
    private static int getMissingNum(int[] arrs, int n) {
        int sum = (n + 1) * n / 2;
        int arrSum = 0;
        for (int v : arrs) {
            arrSum += v;
        }
        return sum - arrSum;
    }


    /**
     * 方法2：位运算异或操作来实现：任何数异或自己都等于0，任何数异或0都等于他自己，
     *
     * @param arrs
     * @param n
     * @return
     */
    private static int getMissingNum2(int[] arrs, int n) {
        int res = 0;
        for (int i = 1; i <= n; i++) {
            res ^= i;
        }

        for (int i = 0; i < arrs.length; i++) {
            res ^= arrs[i];
        }
        return res;
    }


    //.........................................7.1~n的自然数中缺少2个数，请找出这两个数................................................

    /**
     * 方法1：利用1~n个自然数之和减去缺失数组的和，得到2个数之和；1~n自然数的积除以缺失数组的积，得到2个数的积，再利用求根公式算出这两个数
     *
     * @param array
     * @param n
     * @return
     */
    private static int[] getMissingTwoNumber(int[] array, int n) {
        int[] arr = new int[2];
        int sum = 0; //两个数之和
        int p = 1;//两个数之积

        for (int i = 1; i <= n; i++) {
            p *= i;
        }

        int sumN = (n + 1) * n / 2;
        int sumArray = 0;
        for (int v : array) {
            sumArray += v;
            p /= v;
        }
        sum = sumN - sumArray;

        int missingOne = (int) ((sum - Math.sqrt(sum * sum - 4 * p)) / 2);
        int missingTwo = (int) ((sum + Math.sqrt(sum * sum - 4 * p)) / 2);
        arr[0] = missingOne;
        arr[1] = missingTwo;
        return arr;
    }

    /**
     * @param array    包含所有整数的数组
     * @param subArray 缺少整数的数组
     * @return 缺少的整数的异或值
     */
    public static int find_Miss_Number_By_XOR(Integer array[], Integer subArray[]) {
        int result = 0;

        for (Integer e : array) {
            result ^= e;
        }
        for (Integer e : subArray) {
            result ^= e;
        }
        return result;
    }

    // 获取最低位1的位置
    public static int get_mid_By_xor_result(int number) {
        int location = 0;
        while (number % 2 == 0) {
            location++;
            number /= 2;
        }
        return location;
    }

    //返回一个数组中第i位为1的所有的数构成的数组
    private static Integer[] divid_Array(Integer array[], int i) {

        List<Integer> list = new ArrayList<Integer>();

        for (Integer e : array) {
            int temp = e;

            for (int j = 0; j < i; j++) {
                temp = e / 2;
            }
            if (temp % 2 == 1) {
                list.add(e);
            }

        }
        Integer[] result = new Integer[list.size()];
        for (int j = 0; j < list.size(); j++) {
            result[j] = list.get(j);
        }
        return result;
    }

    /**
     * @param array    包含所有整数的数组
     * @param subArray 缺少整数的数组
     */
    public static void getMissNumber(Integer array[], Integer subArray[]) {
        int xor = find_Miss_Number_By_XOR(array, subArray);
        System.out.println("异或的结果为" + xor);
        int mid = get_mid_By_xor_result(xor);
        System.out.println("最低位1的位置" + mid);
        // 数组A的元素为：
        System.out.println("数组A的元素为：");
        Integer[] array1 = divid_Array(array, mid);
        for (Integer e : array1) {
            System.out.print(e + "、");
        }
        // 数组B的元素为：
        System.out.println();
        System.out.println("数组B的元素为：");
        Integer[] array2 = divid_Array(subArray, mid);
        for (Integer e : array2) {
            System.out.print(e + "、");
        }
        System.out.println();
        System.out.println("缺少的第一个数为：");
        int solution1 = find_Miss_Number_By_XOR(array1, array2);
        System.out.println(solution1);
        System.out.println("缺少的第二个数为：");
        int solution2 = solution1 ^ xor;
        System.out.println(solution2);

    }

    //.........................................8.链表只循环一次即可删除倒数第n个结点，给定n是保证有效的...................................

    //方法1： 这里的链表是包含头结点的，头结点的指针域指向链表的第一个结点
    //流程就是先设置2个start、head指向头结点head，然后start先向后移动n步,这样就保证start和end之间相差n步；然后保持start和end同步向后移动，知道start.next为null;
    public static ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode start = head, end = head;
        while (n > 0) {
            start = start.next;
            n--;
        }

        while (start.next != null) {
            start = start.next;
            end = end.next;
        }
        end.next = end.next.next;
        return head;
    }

    //方法2：这里的链表是不包含头结点的
    public static ListNode removeNthFromEnd2(ListNode head, int n) {
        ListNode pre = new ListNode(-1);
        pre.next = head;
        ListNode start = pre, end = pre;
        while (n > 0) {
            start = start.next;
            n--;
        }

        while (start.next != null) {
            start = start.next;
            end = end.next;
        }
        end.next = end.next.next;
        return pre.next;//不返回head是因为假设删除的就是第一个元素
    }

    //......................................9.删除排序数组中的重复项..simple...................................................

    /**
     * @param nums
     * @return 返回不重复数组中的长度
     */
    public static int removeDuplicates(int[] nums) {
        if (nums.length == 0) return 0;
        int i = 0;
        for (int j = 1; j < nums.length; j++) {
            if (nums[j] != nums[i]) {
                i++;
                nums[i] = nums[j];
            }
        }
        for (int j = 0; j < i + 1; j++) {
            System.out.println(nums[j] + ",");
        }
        return i + 1;
    }

    //........................................给定一个数组 nums 和一个值 val，你需要原地移除所有数值等于 val 的元素，返回移除后数组的新长度....simple....................
    // 给定 nums = [3,1,2,3], val = 3 返回值为2，数组只剩[1, 2]
    public static int removeElement(int[] nums, int val) {

        int len = nums.length;
        int i = 0;
        for (int j = 0; j < len; j++) {
            if (nums[j] != val) {
                nums[i] = nums[j];
                i++;
            }
        }

        for (int j = 0; j < i; j++) {
            System.out.println("nums[" + j + "] = " + nums[j]);
        }
        return i;
    }


    //.................................10.不用乘、除来是实现除法.....................................
    public static int divide(int dividend, int divisor) {
        int left = dividend < 0 ? -dividend : dividend;
        int y = divisor < 0 ? -divisor : divisor;
        int result = 0;
        while (left >= y) {
            int multi = 1;
            while (y * multi <= (left >> 1)) {
                multi = multi << 1;
            }
            result += multi;
            left -= y * multi;
        }
        if (dividend < 0 && divisor > 0 || dividend > 0 && divisor < 0)
            return -result;
        return result;
    }

    //....................................11.两两交换链表中的结点........medium...................
    // 效果：1-2-3-4 => 2-1-4-3;
    public static ListNode swapPairs(ListNode head) {
        ListNode L = new ListNode(-1);
        L.next = head;
        ListNode start = head;
        ListNode end = L, p = L;

        while (start != null && start.next != null) {
            start = start.next;
            end = end.next;

            end.next = start.next;
            start.next = end;
            p.next = start;

            p = end;
            start = p.next;

        }
        return L.next;
    }

    //......................................12\合并两个有序的链表...........................................
    //方法1 思想就是将l1的数据插入l2中
    public static ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        ListNode L2 = new ListNode(-1);
        L2.next = l2;
        ListNode p, q, r, t;
        p = l1;
        q = L2.next;
        t = L2;
        while (p != null && q != null) {
            if (p.val >= q.val) {
                t = q;
                q = q.next;
            } else {
                r = p;
                p = p.next;
                r.next = q;
                t.next = r;
                q = t.next;
            }
        }
        if (p != null && q == null) {
            t.next = p;
        }
        return L2.next;
    }

    //方法2 ----这个解法是leetcode上的解法，比我的好
    public static ListNode mergeTwoLists2(ListNode l1, ListNode l2) {
        ListNode prehead = new ListNode(-1);

        ListNode prev = prehead;
        while (l1 != null && l2 != null) {
            if (l1.val <= l2.val) {
                prev.next = l1;
                l1 = l1.next;
            } else {
                prev.next = l2;
                l2 = l2.next;
            }
            prev = prev.next;
        }
        prev.next = l1 == null ? l2 : l1;
        return prehead.next;
    }

    //...............................................13、在排序数组中查找相同元素的第一个和最后一个位置.....................................
    //[1, 2, 3, 3,3, 5] ，3 结果是[2, 4]

    public static int[] searchRange(int[] nums, int target) {
        int[] res = {-1, -1};
        if (nums.length < 1) {
            return res;
        }
        int index = binarySearch(nums, target);
        int tmp = index;
        int start = -1, end = -1;
        if (index == -1) {
            return res;
        }
        while (index < nums.length - 1) {
            if (nums[++index] != target) {
                break;
            } else {
                end = index;
            }
        }

        while (index > 0) {
            if (nums[--index] != target) {
                break;
            } else {
                start = index;
            }
        }
        if (start != -1 && end != -1) {
            res[0] = start;
            res[1] = end;
        } else if (start != -1) {
            res[0] = start;
            res[1] = tmp;
        } else if (end != -1) {
            res[0] = tmp;
            res[1] = end;
        } else {
            res[0] = tmp;
            res[1] = tmp;
        }
        return res;
    }

    //.................................................................14.字符串相乘................

    public static String multiply(String num1, String num2) {
        if (num1.equals("0") || num2.equals("0"))
            return String.valueOf(0);
        int n = num1.length() + num2.length();
        //使用一个数组保存结果的每一位，索引0确定m*n结果是m+n还是m+n-1位
        int[] result = new int[n];
        for (int i = 0; i < num1.length(); i++) {
            int a = num1.charAt(i) - 48;
            ;
            for (int j = 0; j < num2.length(); j++) {
                int m = a * (num2.charAt(j) - 48);
                if (m < 10)
                    result[i + j + 1] += m;
                else {
                    result[i + j + 1] += m % 10;
                    result[i + j] += m / 10;
                    int k = i + j;
                    //先对i+j索引确定是否依次进位
                    while (k > 0 && result[k] >= 10) {
                        result[k] -= 10;
                        result[k - 1]++;
                        k--;
                    }
                }
                //再看i+j+1索引是否产生进位
                if (result[i + j + 1] >= 10) {
                    int k = i + j + 1;
                    while (k > 0 && result[k] >= 10) {
                        result[k] -= 10;
                        result[k - 1]++;
                        k--;
                    }
                }
            }
        }
        String res = "";
        //判断m*n位是否是m+n位
        if (result[0] == 0) {
            for (int i = 1; i < result.length; i++) {
                res += result[i];
            }
        } else {
            for (int i = 0; i < result.length; i++) {
                res += result[i];
            }
        }
        return res;
    }

    //...........................................15.串联所有单词的子串...........................这一题的思想非常好.....................................................
    //给定一个字符串 s 和一些长度相同的单词 words。找出 s 中恰好可以由 words 中所有单词串联形成的子串的起始位置。
    //输入 s = "barfoothefoobarman",words = ["foo","bar"] 从索引 0 和 9 开始的子串分别是 "barfoor" 和 "foobar" 。
    //输出的顺序不重要, [9,0] 也是有效答案。

    /**
     * 怎么判断子串是否符合？这也是这个题的难点了，由于子串包含的单词顺序并不需要固定，
     * 如果是两个单词 A，B，我们只需要判断子串是否是 AB 或者 BA 即可。如果是三个单词 A，B，C 也还好，
     * 只需要判断子串是否是 ABC，或者 ACB，BAC，BCA，CAB，CBA 就可以了，但如果更多单词呢？那就崩溃了。
     * 用两个 HashMap 来解决。首先，我们把所有的单词存到 HashMap 里，key 直接存单词，value 存单词出现的个数（因为给出的单词可能会有重复的，所以可能是 1 或 2 或者其他）。
     * 然后扫描字符串的单词，如果当前扫描的单词在之前的 HashMap 中，就把该单词存到新的 HashMap 中，并判断新的 HashMap 中该单词的 value 是不是大于之前的 HashMap 该单词的 value ，
     * 如果大了，就代表该子串不是我们要找的，接着判断下一个子串就可以了。如果不大于，那么我们接着判断下一个单词的情况。字符串扫描结束，如果子串的全部单词都符合，那么该子串就是我们找的其中一个。
     *
     * @param s
     * @param words
     * @return
     */
    public static List<Integer> findSubstring(String s, String[] words) {
        List<Integer> integerList = new ArrayList<>();
        int wordsNum = words.length;
        if (words == null || words.length < 1) {
            return integerList;
        }
        int wordLen = words[0].length();

        Map<String, Integer> wordsMap = new HashMap<>();//将单词存到map中，key为单词，value为单词的次数
        for (String val : words) {
            wordsMap.put(val, wordsMap.getOrDefault(val, 0) + 1);
        }

        for (int i = 0; i < s.length() - wordLen * wordsNum + 1; i++) {
            int cnt = 0;
            Map<String, Integer> map = new HashMap<>();
            while (cnt < wordsNum) {
                String tmp = s.substring(i + cnt * wordLen, i + wordLen * (cnt + 1));
                if (wordsMap.containsKey(tmp)) {
                    map.put(tmp, map.getOrDefault(tmp, 0) + 1);
                    if (map.get(tmp) <= wordsMap.get(tmp)) {
                        cnt++;
                    } else {
                        break;
                    }
                } else {
                    break;
                }
            }
            if (cnt == wordsNum) {
                integerList.add(i);
            }
        }
        return integerList;
    }

    //.....................................................16.最小覆盖子串...........最小滑动窗口方法.......................................................................................................................
    //比如： s="ACESDBDCA" t = "ACB"; 最小覆盖子串就是 BDCA；

    public static String minWindow(String s, String t) {
        if (s.length() == 0 || t.length() == 0) {
            return "";
        }
        int left = 0, right = 0;
        int match = 0;
        int start = 0, end = 0;//记录覆盖最小子串的起始位置
        int minLen = -1;

        Map<Character, Integer> sMap = new HashMap<>();
        Map<Character, Integer> tMap = new HashMap<>();
        for (int i = 0; i < t.length(); i++) {
            tMap.put(t.charAt(i), tMap.getOrDefault(t.charAt(i), 0) + 1);
        }
        while (right < s.length()) {
            char c = s.charAt(right);
            if (tMap.containsKey(c)) {
                sMap.put(c, sMap.getOrDefault(c, 0) + 1);
                if (sMap.get(c).intValue() == tMap.get(c).intValue()) {
                    match++;
                }
            }

            //right - left + 1 表示覆盖子串的长度
            while (match == tMap.size()) {
                if (minLen == -1 || right - left + 1 < minLen) {
                    start = left;
                    end = right;
                    minLen = right - left + 1;
                }
                char c2 = s.charAt(left);
                if (sMap.containsKey(c2)) {
                    sMap.put(c2, sMap.get(c2) - 1);
                    if (sMap.get(c2).intValue() < tMap.get(c2).intValue()) {
                        match--;
                    }
                }
                left++;
            }
            right++;
        }
        return minLen == -1 ? "" : s.substring(start, end + 1);
    }

    public static String minWindow2(String s, String t) {
        if (s.length() == 0 || t.length() == 0) {
            return "";
        }

        Map<Character, Integer> dictT = new HashMap<Character, Integer>();
        for (int i = 0; i < t.length(); i++) {
            int count = dictT.getOrDefault(t.charAt(i), 0);
            dictT.put(t.charAt(i), count + 1);
        }

        int required = dictT.size();

        int l = 0, r = 0;
        int formed = 0;
        Map<Character, Integer> windowCounts = new HashMap<Character, Integer>();

        int[] ans = {-1, 0, 0};

        while (r < s.length()) {
            char c = s.charAt(r);
            int count = windowCounts.getOrDefault(c, 0);
            windowCounts.put(c, count + 1);

            if (dictT.containsKey(c) && windowCounts.get(c).intValue() == dictT.get(c).intValue()) {
                formed++;
            }

            while (l <= r && formed == required) {
                c = s.charAt(l);
                if (ans[0] == -1 || r - l + 1 < ans[0]) {
                    ans[0] = r - l + 1;
                    ans[1] = l;
                    ans[2] = r;
                }

                windowCounts.put(c, windowCounts.get(c) - 1);
                if (dictT.containsKey(c) && windowCounts.get(c).intValue() < dictT.get(c).intValue()) {
                    formed--;
                }
                l++;
            }
            r++;
        }
        return ans[0] == -1 ? "" : s.substring(ans[1], ans[2] + 1);
    }

    //...................................................................17. 给定一个排序链表，删除所有重复的元素，使得每个元素只出现一次--simple......................................
    public static ListNode deleteDuplicates(ListNode head) {
        if (head == null) {
            return null;
        }
        ListNode p = head.next;
        ListNode q = head;
        while (p != null) {
            if (p.val == q.val) {
                p = p.next;
                q.next = p;
            } else {
                p = p.next;
                q = q.next;
            }
        }
        return head;
    }

    //...................................................................18. 给定一个排序链表，删除所有重复的元素，使得每个元素只出现一次--simple......................................
    // [1, 2, 2 ,4, 4, 5] => 1, 5和上面不同
    public static ListNode deleteDuplicates2(ListNode head) {
        if (head == null) {
            return null;
        }
        ListNode L = new ListNode(-1);
        L.next = head;
        ListNode left = L;
        ListNode right = L.next;
        while (right != null) {
            while (right.next != null && right.val == right.next.val)
                right = right.next;
            if (left.next == right) {
                left = left.next;
            } else {
                left.next = right.next;
            }
            right = right.next;
        }
        return L.next;
    }


    //..............................................................19.链表的冒泡排序............................
    public static ListNode maopaoSort(ListNode head) {
        if (head == null) {
            return head;
        }
        ListNode p = head;
        ListNode q;
        int tmp;
        while (p != null) {
            q = p.next;
            while (q != null) {
                if (p.val > q.val) {
                    tmp = p.val;
                    p.val = q.val;
                    q.val = tmp;
                }
                q = q.next;
            }
            p = p.next;
        }
        return head;
    }

    //........................................................20.链表的插入排序.....................................................
    //通过构建有序序列，对于未排序数据，在已排序序列中从后向前扫描，找到相应的位置并插入
    public static ListNode insertSort(ListNode head) {
        if (head == null)
            return head;
        ListNode L = new ListNode(-1);
        ListNode pre = head;
        ListNode cur = head.next;//从第二个结点开始比较
        L.next = head;
        while (cur != null) {
            if (cur.val < pre.val) {
                ListNode r = L.next;
                ListNode p = L;
                while (r != null) {
                    if (r.val > cur.val) {//找到大于当前的值，，然后把当前的结点插入结点p的后面r的前面，
                        pre.next = cur.next;//先把当前结点前面的一个结点指向当前结点的后面结点
                        p.next = cur;//然后把当前的结点插入结点p的后面r的前面，
                        cur.next = r;
                        cur = pre.next;//让当前结点指向pre结点的后面结点
                        break;
                    } else {
                        p = p.next;
                        r = r.next;
                    }
                }
            } else {
                pre = pre.next;
                cur = cur.next;
            }
        }
        return L.next;
    }

    //..................................................21.将数组中的元素翻转(翻转的意思就是取反，2 翻转为-2)K次使得数组和最大.......................
    public static int largestSumAfterKNegations(int[] A, int K) {
        int res = 0;
        Arrays.sort(A);
        if (A[0] >= 0)
            A[0] = K % 2 == 0 ? A[0] : -A[0];
        else {
            for (int i = 0; i < A.length; i++) {
                if (A[i] >= 0) {
                    if ((K - i) % 2 == 0)
                        break;
                    else if (A[i] > A[i - 1])
                        A[i - 1] = -A[i - 1];
                    else
                        A[i] = -A[i];
                    break;
                } else {
                    A[i] = -A[i];
                }
            }
        }
        for (int i = 0; i < A.length; i++) {
            System.out.println("......A[" + i + "] = " + A[i]);
            res += A[i];
        }
        return res;
    }

    //........................................22.划分字母区间............................................
    //字符串 S 由小写字母组成。我们要把这个字符串划分为尽可能多的片段，同一个字母只会出现在其中的一个片段。返回一个表示每个字符串片段的长度的列表。
    //输入 S = "ababcbacadefegdehijhklij" 输出 [9,7,8] 解释：划分结果为 "ababcbaca", "defegde", "hijhklij"。
    //从第一个字符开始遍历，每获取一个字符就寻找该字符下一次出现的位置索引，并将其定为当前片段的最后位置，
    // 在达到该位置之前， 继续寻找更靠后的最后位置，若达到最后位置之前都没有发现更靠后的最后位置，则将当前最后位置作为一个片段的末尾， 前个片段的末尾后一位是该片段的开头。
    // 实际上是贪心算法思想的一次运用，在每一步中得到截止目前为止的局部最优解，后一个最优解总是比前一个最优解“更优”， 由此推进到结束时，便可得到全局最优解。
    //
    public static List<Integer> partitionLabels(String S) {
        List<Integer> list = new ArrayList<>();
        int end = 0;
        int start = 0;
        for (int i = 0; i< S.length(); i++){
            char c = S.charAt(i);
            int index = S.indexOf(c, i+1);
            if (index == -1 && i >= end){
                list.add(i - start +1);
                start = i+1;
            }else if (index > end){
                end = index;
            }
        }
        return list;
    }

    //.......................................23. 所有的数都出现3次，只有一个数出现1次，找出这个数......................
    //将所有的数按位操作加起来，然后对3取模，如[4, 4, 4,2] = 0100+0100+0100+0010 = 0310 每一位都对3取模 = 0010 = 2
    public static int singleNumber(int[] nums) {
        int ones = 0, twos = 0, threes = 0;
        for(int num : nums){
            twos |= ones & num;
            ones ^= num;
            threes = ones & twos;
            ones &= ~threes;
            twos &= ~threes;
        }
        return ones;
    }

    //.....................................24.删除结点......................simple............................
    //从链表里删除一个节点 node, 且只给这个待删除的node，且该结点不是最后一个结点
    public void deleteNode(ListNode node) {
        node.val = node.next.val;
        node.next = node.next.next;
    }

    //...............................25.判断一个链表是否为回文....................
    public static boolean isPalindrome(ListNode head) {
        if (head == null || head.next == null){
            return true;
        }

        ListNode p = head;
        ListNode L = new ListNode(-1);
        ListNode r = L;
        while (p != null){
            ListNode t = new ListNode(p.val);
            t.next = r.next;
            r.next = t;
            p = p.next;
        }
        p = head;
        r = L.next;
        while (p != null){
            if (p.val != r.val){
                return false;
            }else {
                p = p.next;
                r = r.next;
            }
        }
        return true;
    }

    //..........................26链表反转............................
    public static ListNode reverseList(ListNode head) {
        if (head == null || head.next == null){
            return head;
        }
        ListNode p = head;
        ListNode L = new ListNode(-1);
        ListNode r = L;
        while (p != null){
            ListNode t = new ListNode(p.val);
            t.next = r.next;
            r.next = t;
            p = p.next;
        }
        return L.next;
    }

    //...........................27.删除链表的特定元素.............................................
    public static ListNode removeElements(ListNode head, int val) {
        ListNode L = new ListNode(-1);//增加头结点，用来删除第一个元素就相等
        L.next = head;
        ListNode pre = L;
        while (pre.next != null){
            if (pre.next.val == val){
                pre.next = pre.next.next;
            }else {
                pre = pre.next;
            }
        }
        return L.next;
    }

}



