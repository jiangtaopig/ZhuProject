package daily.yiyuan.com.test_java.generics;

import java.util.ArrayList;
import java.util.List;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2021/1/12
 */
class Main {
    /**
     * @param args
     */
    public static void main(String[] args) {
        Point<Double> point = new Point<>();
        point.setX(1.0);
        String s = point.getX("ss");
        System.out.println("----s = " + s);

        List<Integer> integerList = new ArrayList<>();
        for (int i = 1; i < 5; i++)
            integerList.add(i);

        List<? extends Number> list = new ArrayList<>(integerList);
        Number v1 = list.get(0);
        System.out.println("xx = " + v1);
//        list.add(1); // 编译报错

    }
}
