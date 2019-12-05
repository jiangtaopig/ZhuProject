package daily.yiyuan.com.test_java.leet_code;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2019/12/4
 */
public class TestAlgorithm {
    public static void main(String[] args) {

        final Map<Integer, Integer> map = new HashMap<>();
        map.put(1, 3);
        map.put(2, 1);
        map.put(4, 6);
        map.put(5, 2);

        PriorityQueue<Integer> pq = new PriorityQueue<>(new Comparator<Integer>() {
            @Override
            public int compare(Integer a, Integer b) {
                System.out.println("----------");
                System.out.println("a = "+a+", b = "+b);
                return map.get(a) - map.get(b);
            }});

        for (Integer key : map.keySet()){
            pq.add(key);
        }
        pq.size();
    }
}
