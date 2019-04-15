package daily.yiyuan.com.test_java.collection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 测试集合
 */
public class TestCollection {

    private static Map<String, String> tail;

    public static void main(String [] args){
//        testConcurrentModificationException();

        Object a1 = 1l;
        Object a2 = 1;
        System.out.println("a1 == a2 ? "+ (a1 == a2));

        Map<String, String> map = new LinkedHashMap<>();
        map.put("a", "aa");
        linkNodeLast(map);


        List<String> list = new ArrayList<>();
        list.add("java");
        list.add("android");

        List<String> subList = list.subList(0, list.size());
        subList.add("c++");


        String[] ELEMENTS = new String[0];
        System.out.println("size = "+ ELEMENTS.length);
        ELEMENTS = Arrays.copyOf(ELEMENTS, 10);
        System.out.println("size = "+ ELEMENTS.length);


        LinkedList<String> stringList = new LinkedList<>();
        stringList.add("a");
        System.out.println("last = "+stringList.getLast());
    }

    private static void testConcurrentModificationException() {
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
    }

    private static void linkNodeLast(Map<String, String> p){
        Map<String, String> last = tail;
        tail = p;
        if (last == null){
            System.out.println("................null......................");
        }
    }

}
