package daily.yiyuan.com.test_java.leet_code;

import java.util.ArrayList;
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

        int [] a3 = {1, 3, 4, 7,9};
        int index = binarySearch(a3,9);
        System.out.println("index = "+index);

        int [] a4 = {1,3};
        int [] ss = searchRange(a4, 1);
        System.out.println("ss[0]="+ss[0]+", ss[1] = "+ss[1]);


        int [] A = {3, 7, 2, 11, 3, 4, 9, 2, 18, 0};
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
        boolean isAdded = false;
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                for (int k = j + 1; k < nums.length; k++) {
                    if (nums[i] + nums[j] + nums[k] == 0) {
                        List<Integer> tmp = new ArrayList<>();
                        tmp.add(nums[i]);
                        tmp.add(nums[j]);
                        tmp.add(nums[k]);
                        if (nums[i] == 0 && nums[j] == 0) {
                            if (!isAdded) {
                                isAdded = true;
                                res.add(tmp);
                            }
                        } else {
                            if (res.size() > 0) {
                                for (int t = res.size() - 1; t >= 0; t--) {
                                    List<Integer> list = res.get(t);
                                    if (list.contains(tmp.get(0)) && list.contains(tmp.get(1)) && list.contains(tmp.get(2))) {
                                        break;
                                    } else if (t == 0) {
                                        res.add(tmp);
                                    }
                                }
                            } else {
                                res.add(tmp);
                            }
                        }
                    }
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
        int n = s.length(), ans = 0;
        Map<Character, Integer> map = new HashMap<>();
        for (int end = 0, start = 0; end < n; end++) {
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

    //...............................................13、在排序数组中查找元素的第一个和最后一个位置.....................................

    public static int[] searchRange(int[] nums, int target){
        int [] res = {-1, -1};
        if (nums.length < 1){
            return res;
        }
        int index = binarySearch(nums, target);
        int tmp = index;
        int start = -1, end = -1;
        if (index == -1){
            return res;
        }
        while (index < nums.length-1){
            if (nums[++index] != target){
                break;
            }else {
                end = index;
            }
        }

        while (index > 0){
            if (nums[--index] != target){
                break;
            }else {
                start = index;
            }
        }
      if (start != -1 && end != -1){
          res[0] = start;
          res[1] = end;
      }else if (start != -1){
          res[0] = start;
          res[1] = tmp;
      }else if (end != -1){
          res[0] = tmp;
          res[1] = end;
      }else {
          res[0] = tmp;
          res[1] = tmp;
      }
      return res;
    }



}
