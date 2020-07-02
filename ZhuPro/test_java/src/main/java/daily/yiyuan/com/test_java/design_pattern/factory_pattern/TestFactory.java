package daily.yiyuan.com.test_java.design_pattern.factory_pattern;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2020/6/11
 *
 * 工厂方法模式 定义了一个创建对象的接口，但由子类决定要实例化哪一个类；工厂方法让累的实例化推迟到子类
 * 比如这里 的 由 ShangHaiPizzaStore 和 SiChuangPizzaStore 来决定实例化哪一个类
 *
 */
public class TestFactory {

    public static void main(String[] args) {
        PizzaStore pizzaStore = new ShangHaiPizzaStore();
        pizzaStore.orderPizza("shanghai_sweet");

        PizzaStore sichuangStore = new SiChuangPizzaStore();
        sichuangStore.orderPizza("sichuang_bacon");
    }
}
