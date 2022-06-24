package daily.yiyuan.com.test_java.algoritham.binary_search;

public class TestBinarySearch {
    public static void main(String[] args) {

        int [] arr = {2, 2, 2, 3, 6};
        int index = binarySearchLeft(arr, 1);
        System.out.println("TestBinarySearch :: index = " + index);

        int [] arr2 = {1, 2, 2, 2};
        int res = binarySearchRight(arr2, 2);
        System.out.println("TestBinarySearch::binarySearchRight res = " + res);
        Math.sqrt(4.0);
    }

    /**
     * 找出升序数组 arr 中 target 所在的位置
     * 比如 [1, 3, 4, 5] target = 4，那么返回 2
     *
     * 此算法的缺陷是 [1, 2, 2, 2, 3]，target = 2，此时返回的索引值等于2，是没错的。
     * 但是，我们想要得到target最左侧边界，索引等于1或者最右侧边界索引等于3该怎么处理呢。
     * binarySearchLeft 方法能够实现
     * @param arr
     * @param target
     * @return
     */
    private static int binarySearch(int [] arr, int target) {
        int left = 0;
        int right = arr.length -1;
        // 由于 right 等于最后一个元素的索引，而不是数组的长度，所以 遍历的时候需要 <=
        while (left <= right) {
            int mid = left + (right -left)/2;
            if (arr[mid] == target) {
                return mid;
            } else if (target > arr[mid]) {
                left = mid + 1; // 由于while循环两端都闭区间[left, right]
            } else if (target < arr[mid]) {
                right = mid - 1;
            }
        }
        return -1;
    }

    /**
     * 寻找第一个等于 target 的索引. [1, 2, 2, 4, 6] target=2，返回索引为1
     * @param arr
     * @param target
     * @return
     */
    private static int binarySearchLeft(int [] arr, int target) {
        int left = 0;
        int right = arr.length; // 注意
        while (left < right) { // 注意
            int mid = left + (right - left) / 2;
            if (arr[mid] == target) {
                right = mid; // 关键点，找到之后继续向左搜索
            } else if (arr[mid] < target) {
                left = mid + 1;
            } else if (arr[mid] > target) {
                // 因为 while 循环是左闭右开的区间[left, right)，当 arr[mid] 被检测后，
                // 下一步的搜索区间应该去掉mid分割成2个区间，即[left, mid) 和 [mid+1, right)，
                // 所以不能写成 right = mid - 1
                right = mid;
            }
        }
        // 没找到或者 left 已经等于数组长度了
        if (left == arr.length || arr[left] != target) {
            return -1;
        }
        return left;
    }

    /**
     * 寻找最后一个等于 target 的索引. [1, 2, 2, 4, 6] target=2，返回索引为2
     * @param arr
     * @param target
     * @return
     */
    private static int binarySearchRight(int [] arr, int target) {
        int left = 0;
        int right = arr.length;
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (arr[mid] == target) {
                left = mid + 1;
            } else if (arr[mid] < target) {
                left = mid + 1;
            } else if (arr[mid] > target) {
                right = mid;
            }
        }
        // 也可以用 left -1 因为while循环的终止条件是 left = right
        return arr[right -1] == target ? right -1 : -1;
    }

    //-----------------------------  下面查找左右边界全部用闭区间   ------------------------------------

    private static int leftBound(int [] arr, int target) {
        int left = 0;
        int right = arr.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (arr[mid] == target) {
                right = mid -1; // 别返回，继续锁定左侧边界
            } else if (arr[mid] < target) {
                left = left + 1;
            } else if (arr[mid] > target) {
                right = mid -1;
            }
        }
        if (left >= arr.length || arr[left] != target) return -1;
        return left;
    }

    private static int rightBound(int [] arr, int target) {
        int left = 0;
        int right = arr.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (arr[mid] == target) {
                left = mid + 1;
            } else if (arr[mid] < target) {
                left = mid + 1;
            } else if (arr[mid] > target) {
                right = mid -1;
            }
        }

        if (right < 0 || arr[right] != target)
            return -1;
        return right;
    }

}
