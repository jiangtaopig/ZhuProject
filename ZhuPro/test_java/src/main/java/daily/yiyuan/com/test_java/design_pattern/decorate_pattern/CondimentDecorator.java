package daily.yiyuan.com.test_java.design_pattern.decorate_pattern;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2020/6/11
 * 咖啡里面需要各种调料（Condiment）， 奶泡、豆浆等; 首先让 CondimentDecorator 能够取代 Beverage ,所以 CondimentDecorator 必须扩展自 Beverage
 *
 */
public abstract class CondimentDecorator extends Beverage {
    public abstract String getDescription();
}
