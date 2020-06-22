package daily.yiyuan.com.test_java.design_pattern.abservable_pattern;

import java.util.ArrayList;
import java.util.List;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2020/6/11
 * 观察者模式
 * WeatherData 是用来收集天气数据, 一旦WeatherData有新的测量数据，目前的3个布告必须更新
 * 目前状况、天气统计、天气预报
 */
public class WeatherData implements Subject { // 实现主题接口作为具体的 主题

    private WeatherBean weatherBean;
    private List<Observer> observerList;

    public WeatherData() {
        observerList = new ArrayList<>();
    }

    public void measurementChange() {
        notifyObservers();
    }

    @Override
    public void registerObserver(Observer observer) {
        observerList.add(observer);
    }

    @Override
    public void unRegisterObserver(Observer observer) {
        observerList.remove(observer);
    }

    @Override
    public void notifyObservers() {
        // 通知所有的观察者 做出相应的改变
        for (Observer observer : observerList) {
            observer.update(weatherBean);
        }
    }

    public void setMeasurement(WeatherBean weatherBean){
        this.weatherBean = weatherBean;
        measurementChange();
    }
}
