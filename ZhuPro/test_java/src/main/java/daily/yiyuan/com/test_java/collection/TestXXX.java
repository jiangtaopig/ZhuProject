package daily.yiyuan.com.test_java.collection;

import java.util.ArrayList;
import java.util.List;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2019/7/19
 */
public class TestXXX {
    public static void main(String [] args){
        MyLRUCache lruCache = MyLRUCache.getInstance();
        lruCache.put("1", "1");
        lruCache.put("2", "2");
        lruCache.put("3", "3");

        lruCache.traverse();
        lruCache.get("2");
        lruCache.traverse();
        lruCache.put("4", "4");
        lruCache.traverse();

        List<String> list = new ArrayList<>();
        list.add("1");
        list.add(null);

        System.out.println(list);

    }
}
