package daily.yiyuan.com.test_java;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2020/5/8
 */
public class TestFastFail {
    public static void main(String[] args) {
        testListRemove();
    }

    private static void testListRemove() {
        List<String> list = new ArrayList<>();
        list.add("zhuiangtao");
        list.add("zhujiangtao");
        list.add("pig");
        list.add("ZHU");

        Iterator<String> iterator = list.iterator();
        do {
            if (!iterator.hasNext()) {
                break;
            }
            String str = iterator.next();
            if (str.equals("zhujiangtao")) {
                list.remove(str);
            }
        } while (true);


//        for (String val : list) {
//            if (val.equals("zhujiangtao")) {
//                list.remove(val);
//            }
//        }
    }
}
