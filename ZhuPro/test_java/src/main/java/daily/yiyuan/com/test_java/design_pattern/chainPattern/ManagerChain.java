package daily.yiyuan.com.test_java.design_pattern.chainPattern;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2020/9/23
 */
public class ManagerChain implements VocationChain{
    @Override
    public void approve(RealChain realChain, Task task) {
        if (task.getDays() < 5){
            System.out.println("假期小于5天，经理批准了");
            task.showResult("假期批准了，去吃嗨！！！");
        }else {
            System.out.println("假期大于5天，只能让 CEO 批准了");
            realChain.approve(realChain, task);
        }
    }
}
