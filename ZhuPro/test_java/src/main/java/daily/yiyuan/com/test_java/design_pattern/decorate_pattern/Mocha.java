package daily.yiyuan.com.test_java.design_pattern.decorate_pattern;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2020/6/11
 *  调料的具体装饰者 -- 摩卡
 */
public class Mocha extends CondimentDecorator {

    /**
     * 用一个实例变量记录饮料，也就是被装饰者
     */
    private Beverage beverage;

    public Mocha(Beverage beverage){
        this.beverage = beverage;
    }

    @Override
    public String getDescription() {
        return beverage.getDescription() + ", Mocha";
    }

    @Override
    public double cost() {
        return 0.20 + beverage.cost();
    }
}
