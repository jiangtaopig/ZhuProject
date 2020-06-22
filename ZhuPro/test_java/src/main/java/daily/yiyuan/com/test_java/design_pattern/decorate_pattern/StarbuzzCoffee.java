package daily.yiyuan.com.test_java.design_pattern.decorate_pattern;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2020/6/11
 */
public class StarbuzzCoffee {
    public static void main(String[] args) {

        Beverage espresso = new Espresso(); // 浓缩咖啡
        System.out.println(espresso.description);
        System.out.println(espresso.cost());

        espresso = new Mocha(espresso); // 加 摩卡
        espresso = new Whip(espresso); // 加 奶泡
        espresso = new Soy(espresso); // 加 豆浆

        System.out.println("----------------------加调料后---------------------");

        System.out.println(espresso.getDescription());
        System.out.println(espresso.cost());
    }
}
