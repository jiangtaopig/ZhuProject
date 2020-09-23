package daily.yiyuan.com.test_java.design_pattern.chainPattern;

import java.util.ArrayList;
import java.util.List;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2020/9/23
 */
public class RealChain implements VocationChain {

    private List<VocationChain> vocationChainList;
    private int pos = 0;

    public void addChain(VocationChain chain){
        if (vocationChainList == null)
            vocationChainList = new ArrayList<>();
        vocationChainList.add(chain);
    }

    @Override
    public void approve(RealChain realChain, Task task) {
        if (pos == vocationChainList.size()){
            task.showResult("请假天数太长，谁都没权限处理了，去试试线下请假吧！！");
            return;
        }
        vocationChainList.get(pos++).approve(realChain, task);
    }
}
