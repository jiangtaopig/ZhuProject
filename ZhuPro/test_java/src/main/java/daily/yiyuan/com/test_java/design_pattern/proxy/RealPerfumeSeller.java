package daily.yiyuan.com.test_java.design_pattern.proxy;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2021/1/12
 */
class RealPerfumeSeller implements IPerfume {
    @Override
    public void buyPerfume(double price) {
        System.out.println("这款香奈儿香水 "+ price +" 人民币");
    }
}
