package daily.yiyuan.com.test_java.cas;

import java.lang.reflect.Field;

import sun.misc.Unsafe;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2020/3/29
 */
public class TestCAS2 {
    public static void main(String[] args) {
        Info info = new Info();
        info.setName("pig");
        info.setPrice(10000);

        Unsafe unsafe = UnSafeUtils.getUnsafe();
        Field nameField = null;
        long nameOffset;
        try {
            nameField = info.getClass().getDeclaredField("name");
            nameOffset = unsafe.objectFieldOffset(nameField);
            unsafe.putObject(info, nameOffset, "hhh");
            System.out.println("name = "+info.getName());
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }
}

class Info{
    private String name;
    private int price;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
