package daily.yiyuan.com.test_java.design_pattern.factory_pattern;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2020/6/11
 */
public class SiChuangSpicyPizza extends Pizza {
    public SiChuangSpicyPizza(){
        name = "四川辣味pizza";
    }

    @Override
    protected void prepare() {
        super.prepare();
        System.out.println(name + " 添加四川独有的辣椒");
    }

    @Override
    protected void cut() {
        System.out.println(name +" 不用切");
    }
}
