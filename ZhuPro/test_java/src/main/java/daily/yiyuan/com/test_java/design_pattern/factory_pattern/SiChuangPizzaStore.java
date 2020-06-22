package daily.yiyuan.com.test_java.design_pattern.factory_pattern;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2020/6/11
 */
public class SiChuangPizzaStore extends PizzaStore {
    @Override
    public Pizza createPizza(String type) {
        if ("sichuang_spicy".equals(type)){
            return new SiChuangSpicyPizza();
        }else if ("sichuang_bacon".equals(type)){
            return new SiChuangBaconPizza();
        }
        return null;
    }
}
