package daily.yiyuan.com.test_java.design_pattern.abservable_pattern;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2020/6/11
 * 主题： 观察者要订阅此主题，当主题对象改变时，所有注册此主题的 观察者都会发生改变
 */
public interface Subject {
    void registerObserver(Observer observer);
    void unRegisterObserver(Observer observer);
    void notifyObservers();
}
