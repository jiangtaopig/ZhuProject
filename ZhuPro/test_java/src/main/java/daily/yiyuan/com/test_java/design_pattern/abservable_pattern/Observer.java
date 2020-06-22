package daily.yiyuan.com.test_java.design_pattern.abservable_pattern;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2020/6/11
 * 观察者: 需要订阅主题 Subject 才能收到主题的通知
 */
public interface Observer {
    void update(WeatherBean weatherBean);
}
