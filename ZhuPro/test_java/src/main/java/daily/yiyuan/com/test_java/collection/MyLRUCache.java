package daily.yiyuan.com.test_java.collection;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2019/7/19
 */
public class MyLRUCache {
    private static volatile MyLRUCache mInstance;
    private LinkedHashMap<String, String> map;

    private MyLRUCache() {
        map = new LinkedHashMap<String, String>(6, 0.75f, true) {
            @Override
            protected boolean removeEldestEntry(Map.Entry<String, String> entry) {
                //当map的size 大于3的时候就删除表头结点
                return map.size() > 3;
            }
        };
    }

    public static MyLRUCache getInstance() {
        if (mInstance == null) {
            synchronized (MyLRUCache.class) {
                if (mInstance == null) {
                    mInstance = new MyLRUCache();
                }
            }
        }
        return mInstance;
    }

    public void put(String key, String value) {
        map.put(key, value);
    }

    public String get(String key) {
        return map.get(key);
    }

    public void traverse() {
        Iterator<Map.Entry<String, String>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> entry = iterator.next();
            System.out.println("key = " + entry.getKey() + ", value = " + entry.getValue());
        }
        System.out.println("--------------------------------I am separator line---------------------------------");
    }

}
