package daily.yiyuan.com.test_java.data_structure;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2021/2/2
 */
class RandomizedCollection {
    List<Integer> list;
    Map<Integer, Integer> map;
    Random random;

    public RandomizedCollection() {
        list = new ArrayList();
        map = new HashMap();
        random = new Random();
    }

    public boolean insert(int val) {
        if (map.containsKey(val))
            return false;
        map.put(val, list.size()); // value 表示存储在 list 中的索引下标
        list.add(val); // 添加在数组 list 尾部
        return true;
    }

    public boolean remove(int val) {
        if (!map.containsKey(val)) // 如果哈希映射中不存在该键 直接返回 False
            return false;
        int tmp = list.get(list.size() - 1); // 暂存数组最后一位元素值
        int index = map.get(val); // 获取待删除元素在 list 数组中对应的索引下标 index
        list.set(index, tmp); // 将 list 中该元素值改为暂存的数组最后一位值
        map.put(tmp, index); // 更新哈希映射中代表数组最后一位的键值对 对应的索引下标为 index
        list.remove(list.size() - 1); // 删除数组最后一位
        map.remove(val); // 删除哈希映射中该键值对
        return true;
    }

    /**
     * Get a random element from the set.
     */
    public int getRandom() {
        return list.get(random.nextInt(list.size())); // 产生一个大小为 [0, 数组大小) 的随机索引下标
    }
}
