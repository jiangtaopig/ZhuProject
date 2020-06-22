package daily.yiyuan.com.test_java.design_pattern.decorate_pattern;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2020/6/11
 * 星巴克 的 首先咖啡
 */
public class HouseBlend extends Beverage {

    public HouseBlend(){
        description = "我是综合咖啡";
    }

    @Override
    public double cost() {
        return 0.98;
    }
}
