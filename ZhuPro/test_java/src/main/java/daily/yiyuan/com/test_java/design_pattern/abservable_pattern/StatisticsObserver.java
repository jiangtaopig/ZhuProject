package daily.yiyuan.com.test_java.design_pattern.abservable_pattern;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2020/6/11
 */
public class StatisticsObserver implements Observer, IDisplay {

    private WeatherBean weatherBean;
    private Subject weatherData;

    public StatisticsObserver(Subject weatherData){
        this.weatherData = weatherData;
        this.weatherData.registerObserver(this); // 订阅 主题 Subject
    }

    public void unRegisterObserver(){
        weatherData.unRegisterObserver(this);
    }

    @Override
    public void display() {
        System.out.println("Statistics : pressure = "+weatherBean.pressure);
    }

    @Override
    public void update(WeatherBean weatherBean) {
        this.weatherBean = weatherBean;
        display();
    }
}
