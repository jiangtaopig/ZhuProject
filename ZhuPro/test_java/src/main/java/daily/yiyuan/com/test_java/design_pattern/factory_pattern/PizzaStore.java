package daily.yiyuan.com.test_java.design_pattern.factory_pattern;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2020/6/11
 * 这个是 创建者 Creator 类，它是抽象类，定义一个抽象的工厂方法，让子类去实现创建什么样的Pizza
 * 比如有 上海披萨店 扩展了该类，ShanghaiPizzaStore ,创建甜味披萨 和 海鲜味披萨；四川披萨店(SiChuangPizzaStore) 则可能买辣味披萨和火腿味披萨
 */
public abstract class PizzaStore {
    public void orderPizza(String type) {
        Pizza pizza = createPizza(type);
        pizza.prepare();
        pizza.bake();
        pizza.cut();
        pizza.box();
    }

    /**
     * 创建Pizza的工厂方法
     *
     * @param type
     * @return
     */
    public abstract Pizza createPizza(String type);
}
