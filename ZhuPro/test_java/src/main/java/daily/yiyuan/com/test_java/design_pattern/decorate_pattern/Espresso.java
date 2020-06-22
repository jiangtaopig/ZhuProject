package daily.yiyuan.com.test_java.design_pattern.decorate_pattern;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2020/6/11
 * 浓缩咖啡 是一种饮料 ， 所以要扩展自 Beverage
 */
public class Espresso extends Beverage {

    public Espresso(){
        description = "我是浓缩咖啡";
    }


    @Override
    public double cost() {
        return 1.99;
    }
}
