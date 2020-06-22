package daily.yiyuan.com.test_java.design_pattern.decorate_pattern;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2020/6/11
 * 咖啡的基础抽象类 -- Beverage 饮料
 */
public abstract class Beverage {

   protected String description = "Unknown Beverage";

    public String getDescription(){
        return description;
    }

    public abstract double cost();
}
