package daily.yiyuan.com.test_java.design_pattern.proxy;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2021/1/12
 *  法国实际卖酒的商家，法国的好朋友小王就是代理这个商家
 *  他喝小王都必须实现共同的接口 IWine
 */
class RealWineSeller implements IWine{
    @Override
    public void buyWine(double price) {
        System.out.println("这酒花费 "+price +" 元");
    }
}
