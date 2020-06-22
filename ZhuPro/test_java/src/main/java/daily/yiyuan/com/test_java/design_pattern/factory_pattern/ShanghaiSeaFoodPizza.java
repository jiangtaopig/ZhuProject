package daily.yiyuan.com.test_java.design_pattern.factory_pattern;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2020/6/11
 */
public class ShanghaiSeaFoodPizza extends Pizza{
    public ShanghaiSeaFoodPizza(){
        name = "上海海鲜味pizza";
    }

    @Override
    protected void prepare() {
        super.prepare();
        System.out.println(name +" 需要准备新鲜的海产品");
    }
}
