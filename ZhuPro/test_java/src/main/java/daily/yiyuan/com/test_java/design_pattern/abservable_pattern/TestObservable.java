package daily.yiyuan.com.test_java.design_pattern.abservable_pattern;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2020/6/11
 */
public class TestObservable {
    public static void main(String[] args) {
        WeatherData subject = new WeatherData();
        Observer currentConditionOb = new CurrentConditionObserver(subject);
        Observer statisticsOb = new StatisticsObserver(subject);

        WeatherBean weatherBean = new WeatherBean();
        weatherBean.pressure = 60f;
        weatherBean.humidity = 22.4f;
        weatherBean.temperature = 28.3f;

        subject.setMeasurement(weatherBean);

    }
}
