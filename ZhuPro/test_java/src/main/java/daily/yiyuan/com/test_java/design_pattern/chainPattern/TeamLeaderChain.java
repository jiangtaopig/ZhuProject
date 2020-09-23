package daily.yiyuan.com.test_java.design_pattern.chainPattern;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2020/9/23
 */
public class TeamLeaderChain implements VocationChain{
    @Override
    public void approve(RealChain realChain, Task task) {
        if (task.getDays() == 1) {
            System.out.println("假期小于2天，我批准了");
            task.showResult("假期批准了，去吃嗨！！！");
        }else {
            System.out.println("假期大于2天，我作不了主，去找 Manager");
            realChain.approve(realChain, task);
        }
    }
}
