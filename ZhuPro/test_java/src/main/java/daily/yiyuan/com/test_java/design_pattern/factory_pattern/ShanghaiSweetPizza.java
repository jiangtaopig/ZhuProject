package daily.yiyuan.com.test_java.design_pattern.factory_pattern;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2020/6/11
 */
public class ShanghaiSweetPizza extends Pizza {

    public ShanghaiSweetPizza(){
        name = "上海甜味 pizza";
    }

    @Override
    protected void prepare() {
        super.prepare();
        System.out.println(name + "需要蜂蜜来增加甜味");
    }
}
