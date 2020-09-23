package daily.yiyuan.com.test_java.design_pattern.chainPattern;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2020/9/23
 */
public class CeoChain implements VocationChain{
    @Override
    public void approve(RealChain realChain, Task task) {
        if (task.getDays() < 10){
            System.out.println("假期小于10天，CEO批准了");
            task.showResult("假期批准了，去吃嗨！！！");
        }else {
            System.out.println("假期太长了，...");
            realChain.approve(realChain, task);
        }
    }
}
