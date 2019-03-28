package daily.yiyuan.com.test_java.collection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 测试集合
 */
public class TestCollection {

    private static Map<String, String> tail;

    public static void main(String [] args){
        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");
        list.add("3");

        for (int i = 0; i < list.size(); i++){
            System.out.println("list.get("+i+") = " + list.get(i));
            if ("2".equals(list.get(i))){
                list.remove(i);
            }
        }

        Object a1 = 1l;
        Object a2 = 1;

        System.out.println("a1 == a2 ? "+ (a1 == a2));


        Map<String, String> map = new LinkedHashMap<>();
        map.put("a", "aa");

        linkNodeLast(map);

        Set<String> set = new HashSet<>();
        set.add("1");
        set.add("2");

//        Iterator<String> iterator = list.iterator();
//        while (iterator.hasNext()){
//            String tmp = iterator.next();
//            if ("1".equals(tmp)){
//                iterator.remove();
//            }
//        }
    }

    private static void linkNodeLast(Map<String, String> p){
        Map<String, String> last = tail;
        tail = p;
        if (last == null){
            System.out.println("................null......................");
        }
    }

}
