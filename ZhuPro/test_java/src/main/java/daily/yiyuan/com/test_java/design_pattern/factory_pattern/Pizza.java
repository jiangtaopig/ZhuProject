package daily.yiyuan.com.test_java.design_pattern.factory_pattern;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2020/6/11
 * 这里以Pizza来举例--- Pizza类 是一种抽象的产品，后续由工厂模式创建各种各样的 Pizza
 */
public abstract class Pizza {
    protected String name;

    protected void prepare(){
        System.out.println(name + " prepare ing ...");
    }

    protected void bake(){
        System.out.println(name + " baking ...");
    }

    protected void cut(){
        System.out.println(name + " cutting in diamond slices");
    }

    protected void box(){
        System.out.println(name + "Place pizza in a beautiful box");
    }

}
