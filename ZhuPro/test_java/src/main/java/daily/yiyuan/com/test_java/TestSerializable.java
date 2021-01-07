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
 * 2. transient 修饰的字段不能被序列化，它可以阻止字段被序列化到文件中，
 * 在被反序列化后，transient 字段的值被设为初始值，比如 int 型的初始值为 0，对象型的初始值为 null。
 * 但是可以 自定义 writeObject(ObjectOutputStream stream) 和 readObject(ObjectInputStream)来使得transient修饰的字段被序列化。
 * 比如说 ArrayList 是通过数组实现的且也是可序列化的（实现了Serializable），transient Object[] elementData ，elementData用了 transient修饰，为什么这样做？
 * 比方说你给 ArrayList的初始容量设置为100，当你add 第101个数据的时候就会触发扩容（1.5倍），如果elementData不用transient修饰，那么序列化就会有49个内存地址的浪费，所以ArrayList使用了transient来修饰。
 * 且 自定义了 writeObject(),只把ArrayList的size循环写进去，而不是 elementData.length的数据循环写进去；readObject也是这样处理的。
 */
public class TestSerializable {

    public static void main(String[] args) {
        User user = new User();
        user.setAge(23);
        user.setName("诸葛");
        user.useless = "妹子";

        System.out.println("normal : " + user);
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("zjt"));
            outputStream.writeObject(user);
        } catch (IOException e) {
            e.printStackTrace();
        }

        user.common = "小猪猪";

        try {
            ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("zjt"));
            User user1 = (User) inputStream.readObject();
            System.out.println("反序列化后的结果 : " + user1); //输出的 useless 为 null
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        // 反序列化后 common 变成 小猪猪，说明 static 字段无法进行序列化
    }


    public static class User implements Serializable {
        private String name;
        private int age;
        public static String common = "猪猪";

        transient String useless;

        public void setName(String name) {
            this.name = name;
        }

        public void setAge(int age) {
            this.age = age;
        }

        private void writeObject(ObjectOutputStream outputStream) throws IOException {
            outputStream.defaultWriteObject();
            outputStream.writeObject(useless);
        }

        private void readObject(ObjectInputStream inputStream) throws IOException, ClassNotFoundException {
            inputStream.defaultReadObject();
            useless = (String) inputStream.readObject();
        }

        @Override
        public String toString() {
            return "name : " + name + ", age : " + age + ", common = " + common + ", useless = " + useless;
        }
    }
}
