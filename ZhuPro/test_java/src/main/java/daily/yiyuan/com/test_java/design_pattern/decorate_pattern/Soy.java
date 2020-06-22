package daily.yiyuan.com.test_java.design_pattern.decorate_pattern;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2020/6/11
 *  调料 豆浆---装饰者
 */
public class Soy extends CondimentDecorator {

    private Beverage beverage;
    public Soy(Beverage beverage){
        this.beverage = beverage;
    }

    @Override
    public String getDescription() {
        return beverage.getDescription() + ", Soy";
    }

    @Override
    public double cost() {
        return 0.16 + beverage.cost();
    }
}
