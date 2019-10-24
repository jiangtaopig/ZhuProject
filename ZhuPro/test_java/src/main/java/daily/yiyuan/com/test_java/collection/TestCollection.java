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
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.Vector;
import java.util.concurrent.CopyOnWriteArrayList;

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

        MyArrayList myArrayList = new MyArrayList();
        myArrayList.add("java");
        myArrayList.add("android");

        String[] ELEMENTS = new String[0];
        System.out.println("size = "+ ELEMENTS.length);
        ELEMENTS = Arrays.copyOf(ELEMENTS, 10);
        System.out.println("size = "+ ELEMENTS.length);


        LinkedList<String> stringList = new LinkedList<>();
        stringList.add("a");
        System.out.println("last = "+stringList.getLast());

        Queue<Integer> queue = new PriorityQueue<>();
        queue.offer(3);
        queue.offer(4);
        queue.offer(1);
        queue.offer(2);

        int lo = 0;
        System.out.println("xxx ... "+~lo);


//        while (iterator.hasNext()){
//            System.out.println(" 元素: "+iterator.next());
//        }
//        System.out.println("队列头元素："+queue.poll());

        System.out.println("第2个元素"+queue.remove(2));
        Iterator<Integer> iterator = queue.iterator();
        while (iterator.hasNext()){
            System.out.println(" 元素: "+iterator.next());
        }

        Vector<String> vector = new Vector<>();
        vector.add("1");

        int [] arr = new int[5];
        arr[0] = 5;

        int ss = binarySearch(arr, 1, 2);
        System.out.println("ss = "+ss);

        StringBuilder stringBuilder = new StringBuilder();
        StringBuffer stringBuffer = new StringBuffer();

        LinkedList<String> linkedList = new LinkedList<>();

        linkedList.add("1");
        linkedList.add("2");
        linkedList.add("3");

        System.out.println(" val = "+linkedList.poll()+", size = "+linkedList.size());

        List<String> strings = new ArrayList<>();
        strings.add("a");
        strings.add("b");
        strings.add("c");
        linkedList.addAll(1, strings);

        for (String ssr : linkedList){
            System.out.println("v = "+ssr);
        }

    }
    static int binarySearch(int[] array, int size, int value) {
        int lo = 0;
        int hi = size - 1;

        while (lo <= hi) {
            final int mid = (lo + hi) >>> 1;
            final int midVal = array[mid];

            if (midVal < value) {
                lo = mid + 1;
            } else if (midVal > value) {
                hi = mid - 1;
            } else {
                return mid;  // value found
            }
        }
        return ~lo;  // value not present
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
