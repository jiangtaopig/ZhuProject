package daily.yiyuan.com.test_java.data_structure;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2019/6/28
 */
public class SearchUtils {
    public static int binarySearch(int[] nums, int target) {
        int low = 0;
        int high = nums.length-1;
        while (low <= high) {
            int index = (low + high)>>>1;
            if (nums[index] == target) {
                return index;
            } else {
                if (nums[index] > target) {
                    high = index - 1;
                } else {
                    low = index + 1;
                }
            }
        }

        return -1;
    }

    public static int binarySearchByRecursive(int[] nums, int low, int high, int target) {
        if (low <= high) {
            int index = (low + high) >>> 1;
            if (nums[index] == target) {
                return index;
            } else {
                if (nums[index] > target) {
                    return binarySearchByRecursive(nums, low, index - 1, target);
                } else {
                    return binarySearchByRecursive(nums, index + 1, high, target);
                }
            }
        }
        return -1;
    }
}
