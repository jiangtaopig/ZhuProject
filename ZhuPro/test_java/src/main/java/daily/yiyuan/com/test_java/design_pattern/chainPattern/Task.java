package daily.yiyuan.com.test_java.design_pattern.chainPattern;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2020/9/23
 */
public class Task {
    private int days;

    public Task(int days){
        this.days = days;
    }

    public void showResult(String str){
        System.out.println(str);
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }
}
