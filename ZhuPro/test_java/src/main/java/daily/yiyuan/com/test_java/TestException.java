package daily.yiyuan.com.test_java;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2020/12/18
 */
class TestException {
    public static void main(String[] args) {
//        new Thread() {
//            @Override
//            public void run() {
//                testException();
//            }
//        }.start();

        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");

        for (String str : list){
            if ("1".equals(str)){
                list.remove(str);
            }
        }

        Integer a = 1;
    }


    private static void testException() {
        try {
            try {
                sayHi();
            } catch (RuntimeException e) {
                e.printStackTrace();
                throw e;
            } finally {
                System.out.println("vvvvvvvvv");
            }
        } finally {
            System.out.println("--------");
        }
    }

    private static void sayHi() {
        System.out.println("----------sayHi--------");
        throw new RuntimeException("我又抛出异常啦， 哈哈哈！");
    }
}
