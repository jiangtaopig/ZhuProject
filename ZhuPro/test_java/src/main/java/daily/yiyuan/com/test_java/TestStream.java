package daily.yiyuan.com.test_java;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import daily.yiyuan.com.test_java.data_structure.SearchUtils;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2020/3/9
 */
public class TestStream {
    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);

        List<Integer> integerList = list.stream()
                .filter(v -> {
                    System.out.println(v);
                    return v > 1;
                })
                .map(v -> {
                    return v + 10;
                })
                .collect(Collectors.toList());

        List<Car> carList = new ArrayList<>();
        for (int i = 1; i < 4; i++) {
            Car car = new Car();
            car.name = "audi" + i;
            car.speed = 200 + i;
            carList.add(car);
        }

        System.out.println("source............................");
        for (Car car : carList) {
            System.out.println(car);
        }

        List<Car> cars = new ArrayList<>();
        cars.clear();
        cars.addAll(carList);

        cars.get(1).name = "zjt";

        System.out.println("new ..........................");
        for (Car car : cars) {
            System.out.println(car);
        }

        System.out.println("source............................");
        for (Car car : carList) {
            System.out.println(car);
        }

        int sum = sum(3);
        System.out.println("sum = " + sum);
        int fibonacci = fibonacci(4);
        System.out.println("fibonacci = " + fibonacci);
        int index = SearchUtils.binarySearchFirst(new int[]{1, 2, 4, 4, 4, 5, 6, 7}, 4);
        System.out.println("index = " + index);

        binaryInsert(new int[]{1, 2, 6, 8, 13, 15, 19}, 14);
    }

    public static int binarySearch(int[] arr, int target) {
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

    public static int sum(int n) {
        if (n <= 0) {
            return 0;
        } else {
            return n + sum(n - 1);
        }
    }

    public static int fibonacci(int n) {
        if (n < 0) {
            return 0;
        } else if (n == 0 || n == 1) {
            return 1;
        } else {
            return fibonacci(n - 2) + fibonacci(n - 1);
        }
    }

    public static int maxSubSequence(int[] arr) {
        //dp[i] 表示 以 arr[i]结尾的最长子序列的个数
        int[] dp = new int[arr.length];
        Arrays.fill(dp, 1);//因为最小的子序列就是自己，所以至少有一个
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < i; j++) {
                if (arr[i] > arr[j]) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
        }
        int res = 0;
        for (int i = 0; i < dp.length; i++) {
            res = Math.max(res, dp[i]);
        }
        return res;
    }

    public synchronized void doSth(Object object) {
        try {
            object.wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    ReentrantLock reentrantLock = new ReentrantLock();
    Condition condition = reentrantLock.newCondition();

    public void doSth() {
        try {
            reentrantLock.lock();
            /**
             * 逻辑代码
             */
            condition.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            reentrantLock.unlock();
        }
    }

    public static class Car {
        public String name;
        public int speed;

        @Override
        public String toString() {
            return "[name = " + name + "; speed = " + speed + "]";
        }
    }


    /**
     *  有序数组插入一个 数字
     * @param arr
     * @param target
     * @return
     */
    public static int binaryInsert(int [] arr, int target){
        int left = 0;
        int right = arr.length - 1;
        while (left <= right){
            int mid = left + ((right - left) >> 1);
            if (arr[mid] > target){
                right = mid - 1;
                if (arr[right] <= target){
                    return right;
                }
            }else if (arr[mid] < target){
                left = mid + 1;
                if (arr[left] >= target){
                    return left - 1;
                }
            }else {
                return mid;
            }
        }
        return -1;
    }
}
