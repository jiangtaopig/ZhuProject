package daily.yiyuan.com.test_java;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2020/2/27
 * 测试 对象的序列化
 * 注意： 1. static 属性 不能被序列化，因为 序列化的是对象属性 ，而 static 修饰的属性是属于类
 *  2. transient 修饰的字段不能被序列化，它可以阻止字段被序列化到文件中，
 *  在被反序列化后，transient 字段的值被设为初始值，比如 int 型的初始值为 0，对象型的初始值为 null。
 *
 */
public class TestSerializable {


    public static void main(String[] args) {
        User user = new User();
        user.setAge(23);
        user.setName("诸葛");
        User.common = "男人";
        user.useless = "妹子";

        System.out.println("normal : "+user);
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("zjt"));
            outputStream.writeObject(user);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("zjt"));
            User user1 = (User) inputStream.readObject();
            System.out.println("反序列化后的结果 : "+user1); //输出的 useless 为 null
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


    }


    public static class User implements Serializable {
        private String name;
        private int age;
        public static String common;

        transient String useless;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        @Override
        public String toString() {
            return "name : "+name+", age : "+age+", common = "+common+", useless = "+useless;
        }
    }
}
