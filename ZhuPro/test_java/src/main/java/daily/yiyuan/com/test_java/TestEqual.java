package daily.yiyuan.com.test_java;

public class TestEqual {


    public static void main(String[] args) {

        Circle circle = new Circle(1);
        ColorCircle colorCircle = new ColorCircle(1, ColorCircle.COLOR.RED);
        ColorCircle colorCircle1 = new ColorCircle(1, ColorCircle.COLOR.BLUE);
        boolean eq1 = circle.equals(colorCircle);
        boolean eq2 = circle.equals(colorCircle1);
        boolean eq3 = colorCircle.equals(colorCircle1);
        System.out.println("eq1 = " + eq1 + ", eq2 = "+eq2 + ", eq3 = "+eq3);// 违反了传递性原则
    }
}

class Circle {
    private int radius;

    public Circle(int radius) {
        this.radius = radius;
    }

    public int getRadius() {
        return radius;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Circle) {
            return radius == ((Circle) o).radius;
        }
        return false;
    }
}

class ColorCircle extends Circle {

    public static enum COLOR {
        RED, WHITE, BLUE, GREEN, YELLOW
    };

    private COLOR color;


    public ColorCircle(int radius, COLOR color) {
        super(radius);
        this.color = color;
    }

    public COLOR getColor() {
        return color;
    }

    @Override
    public boolean equals(Object o) {
        if(o.getClass() == Circle.class){
            return super.equals(o);
        }else if (o.getClass() == ColorCircle.class){
            return super.equals(o) && color == ((ColorCircle) o).getColor();
        }
        return false;
    }
}