package daily.yiyuan.com.test_java.leet_code;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2020/1/6
 * 回溯算法
 */
public class TestBackTrack {
    private static List<List<Integer>> res = new ArrayList<>();
    private static int [] Candidates ;

    public static void main(String[] args) {
//        permute(new int [] {1, 3});

        List<List<Integer>> result = combinationSum(new int[]{2, 5, 1, 2, 2}, 5);
        System.out.println(result);
    }


    /* 主函数，输入一组不重复的数字，返回它们的全排列 */
    private static List<List<Integer>> permute(int[] nums) {
        // 记录「路径」
        Stack<Integer> track = new Stack<>();
        backtrack(nums, track);
        return res;
    }

    /**
     *
     * @param nums
     * @param track 路径：记录在 track 中
     *              选择列表：nums 中不存在于 track 的那些元素
     *              结束条件：nums 中的元素全都在 track 中出现
     */

    private static void backtrack(int[] nums, Stack<Integer> track) {
        // 触发结束条件
        if (track.size() == nums.length) {
            res.add(new ArrayList(track));
            return;
        }

        for (int i = 0; i < nums.length; i++) {
            // 排除不合法的选择
            if (track.contains(nums[i]))
                continue;
            // 做选择
            track.add(nums[i]);
            // 进入下一层决策树
            backtrack(nums, track);
            // 取消选择
            track.pop();
        }
    }

    /**
     * 输入: candidates = [2,3,6,7], target = 7,
     * 所求解集为:
     * [
     *   [7],
     *   [2,2,3]
     * ]
     */
    public static List<List<Integer>> combinationSum(int[] candidates, int target){
        if (candidates == null || candidates.length == 0){
            return res;
        }
        Arrays.sort(candidates);
        Candidates = candidates;
        Stack<Integer> track = new Stack();
        backTrack(target, 0, track);
        return res;
    }

    private static void backTrack(int residue, int start, Stack<Integer> pre){
        if (residue == 0){
            res.add(new ArrayList<Integer>(pre));
            return;
        }
        int len = Candidates.length;
        // 表示下一轮的剩余，如果下一轮的剩余都小于 0 ，就没有必要进行后面的循环了
        for (int i = start; i < len && residue - Candidates[i] >= 0; i++){
            pre.add(Candidates[i]);
            //因为数字可以无限制重复被选取，因此起始位置还是自 i 而不是i+1
            backTrack(residue - Candidates[i], i, pre);
            pre.pop();
        }
    }


    /**
     * 解集不能包含重复的组合。
     * @param candidates
     * @param target
     * @return
     */
    public static List<List<Integer>> combinationSum2(int[] candidates, int target){
        if (candidates == null || candidates.length == 0){
            return res;
        }
        Arrays.sort(candidates);
        Candidates = candidates;
        Stack<Integer> track = new Stack();
        backTrack2(target, 0, track);
        return res;
    }

    private static void backTrack2(int residue, int start, Stack<Integer> pre){
        if (residue == 0){
            res.add(new ArrayList<Integer>(pre));
            return;
        }
        int len = Candidates.length;
        // 表示下一轮的剩余，如果下一轮的剩余都小于 0 ，就没有必要进行后面的循环了
        for (int i = start; i < len && residue - Candidates[i] >= 0; i++){
            // 这一步之所以能够生效，其前提是数组一定是排好序的，这样才能保证：
            // 在递归调用的统一深度（层）中，一个元素只使用一次。
            // 这一步剪枝操作基于 candidates 数组是排序数组的前提下

            if (i > start && Candidates[i] == Candidates[i-1]){
                continue;
            }
            pre.add(Candidates[i]);
            backTrack2(residue - Candidates[i], i+1, pre);
            pre.pop();
        }
    }



}
