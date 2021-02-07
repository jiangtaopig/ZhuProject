package daily.yiyuan.com.test_java.design_pattern.abservable_pattern;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2020/6/11
 * 目前状况 布告板：具体的观察者
 */
public class CurrentConditionObserver implements Observer, IDisplay {

    private WeatherBean weatherBean;
    private Subject realSubject;

    public CurrentConditionObserver(Subject realSubject){
        this.realSubject = realSubject;
        this.realSubject.registerObserver(this);
    }

    public void unRegisterObserver(){
        realSubject.unRegisterObserver(this);
    }

    @Override
    public void display() {
        System.out.println("CurrentCondition: degree = "+weatherBean.temperature+", humidity = "+weatherBean.humidity);
    }

    @Override
    public void update(WeatherBean weatherBean) {
        this.weatherBean = weatherBean;
        display();
    }
}
