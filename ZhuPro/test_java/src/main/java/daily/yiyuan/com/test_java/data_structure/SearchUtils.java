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
            int index = (low + high)>>>1;////1.防止直接相加导致溢出 2.移位操作效率更高
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


    public static int binarySearch2(int[] arr, int target) {
        int low = 0;
        int high = arr.length;
        while (low < high) {
            int index = low + ((high - low) >> 1); //1.防止直接相加导致溢出 2.移位操作效率更高
            if (target > arr[index]) {
                low = index + 1;
            } else if (target < arr[index]) {
                high = index; //这里不能写成 index - 1
            } else if (target == arr[index]) {//有的人可能一开始就比较值相等，但根据经验值，一开始就相等的可能性很小
                return index;
            }
        }
        return -1;
    }

    public static int binarySearchByRecursive(int[] nums, int low, int high, int target) {
        if (low <= high) {
            int index = (low + high) >> 1;
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

    //查找有序数组中第一次出现指定元素的位置

    public static int binarySearchFirst(int [] arr, int target){
        int left = 0;
        int right = arr.length - 1;
        while (left <= right){
            int mid = left + ((right - left) >> 1);
            if (arr[mid] > target){
                right = mid - 1;
            }else if (arr[mid] < target){
                left = mid +1;
            }else {
                if (mid == 0 || arr[mid] != arr[mid -1]){
                    return mid;
                }else {
                    right = mid - 1;
                }
            }
        }
        return -1;
    }
}
