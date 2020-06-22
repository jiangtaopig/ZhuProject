package daily.yiyuan.com.test_java.design_pattern.factory_pattern;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2020/6/11
 */
public class SiChuangBaconPizza extends Pizza {
    public SiChuangBaconPizza(){
        name = "四川腊味pizza";
    }

    @Override
    protected void prepare() {
        super.prepare();
        System.out.println(name + " 上等的腊肉");
    }
}
