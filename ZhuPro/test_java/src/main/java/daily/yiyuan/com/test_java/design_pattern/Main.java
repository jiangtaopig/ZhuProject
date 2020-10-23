package daily.yiyuan.com.test_java.design_pattern;

import java.util.ArrayList;
import java.util.List;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2020/6/11
 */
public class Main {

    public static void main(String[] args) {
        List<MyInfo> myInfoList = new ArrayList<>();

        MyInfo myInfo = null;
        for (int i = 0; i < 5; i++){
            myInfo = new MyInfo("xx"+i, i);
            myInfoList.add(myInfo);
        }
    }
}

class MyInfo{
    private String name;
    private int age;

    public MyInfo(String name, int age){
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
