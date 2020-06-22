package daily.yiyuan.com.test_java.design_pattern.factory_pattern;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2020/6/11
 */
public class ShangHaiPizzaStore extends PizzaStore {
    @Override
    public Pizza createPizza(String type) {
        if ("shanghai_sweet".equals(type)){ // 这里面的 shanghai_sweet 最好写成 枚举类型以免用户 拼写错误
            return new ShanghaiSweetPizza();
        }else if ("shanghai_seafood".equals(type)){
            return new ShanghaiSeaFoodPizza();
        }
        return null;
    }
}
