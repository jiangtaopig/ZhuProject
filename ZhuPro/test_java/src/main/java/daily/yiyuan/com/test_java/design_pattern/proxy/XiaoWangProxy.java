package daily.yiyuan.com.test_java.design_pattern.proxy;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2021/1/12
 */
class XiaoWangProxy implements IWine, IPerfume {

    private IWine mRealWineSeller;
    private IPerfume mRealPerfumeSeller;

    public XiaoWangProxy(IWine realWineSeller){
        mRealWineSeller = realWineSeller;
    }

    public XiaoWangProxy(IPerfume realPerfumeSeller) {
        mRealPerfumeSeller = realPerfumeSeller;
    }

    @Override
    public void buyWine(double price) {
        System.out.println("小王先去实际的酒庄");
        mRealWineSeller.buyWine(price);
        System.out.println("买完酒后，小王包邮寄给我");
    }

    @Override
    public void buyPerfume(double price) {
        System.out.println("小王先去实际的香水商店");
        mRealPerfumeSeller.buyPerfume(price);
        System.out.println("买完香水后，小王包邮寄给我");
    }
}
