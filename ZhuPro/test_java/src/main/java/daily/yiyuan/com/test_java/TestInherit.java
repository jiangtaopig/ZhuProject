package daily.yiyuan.com.test_java;

import java.util.ArrayList;
import java.util.List;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2020/1/9
 */
public class TestInherit {
    public static void main(String[] args) {

        List<BaseData> baseDataList = new ArrayList<>();
        ItemDataX dataX = new ItemDataX();
        dataX.type = 1;
        dataX.age = 23;
        dataX.name = "zhujiangtao";

        ItemDataY dataY = new ItemDataY();
        dataY.type = 2;
        dataY.title = "我十点";
        dataY.subTitle = "皇帝蕉敌基督";

        baseDataList.add(dataX);
        baseDataList.add(dataY);

        ItemDataY dataY1 = (ItemDataY) baseDataList.get(1);
        System.out.println(dataY1.subTitle);

    }
}

class BaseData{
    protected int type;
}

class ItemDataX extends BaseData{
    public String name;
    public int age;
}

class ItemDataY extends BaseData{
    public String title;
    public String subTitle;
}


