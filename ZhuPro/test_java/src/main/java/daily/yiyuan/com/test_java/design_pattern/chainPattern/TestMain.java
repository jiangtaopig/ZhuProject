package daily.yiyuan.com.test_java.design_pattern.chainPattern;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2020/9/23
 */
class TestMain {

    public static void main(String[] args) {
        Task task = new Task(12);
        RealChain realChain = new RealChain();
        realChain.addChain(new TeamLeaderChain());
        realChain.addChain(new ManagerChain());
        realChain.addChain(new CeoChain());

        realChain.approve(realChain, task);

    }
}
