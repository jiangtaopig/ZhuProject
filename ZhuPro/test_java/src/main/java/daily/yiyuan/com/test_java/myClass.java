package daily.yiyuan.com.test_java;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static com.sun.org.apache.xalan.internal.xsltc.compiler.util.Util.println;

public class myClass {

    private final char[] value;

    public myClass(char[] value) {
        this.value = value;
    }

    class CardBean{
        String code;
    }

    public static void main(String[] args) {
        int val = 197400;
        double vv = val / 100.0;
        System.out.println("vv = " + vv);
        println("hello ,world");

        String s1 = "hello";
        String s2 = "hello";
        String s3 = new String();

//        System.out.println("s1 == s2 ? "+(s1 == s2));
//        s1.isEmpty();
//        s1.equals(s2);
//
//        StringBuffer sb1 = new StringBuffer("hello");
//        sb1.equals("abc");
//
//        Child child = new Child();
//        child.test();

        /**
         * 测试类的初始化顺序
         */
        //静态初始化块只在类第一次加载到内存时调用一次，下面的Father1不会再调用静态的初始化块。
//        Father father = new Father();
//        Father father1 = new Father();

        ArrayList<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);


        Iterator<Integer> iterator = list.iterator();


        Integer [] arr1 = new Integer[3];
        list.toArray(arr1);

        Integer [] arr2 = list.toArray(new Integer[3]);

        System.out.println(Arrays.equals(arr1, arr2));
        System.out.println(arr1 == arr2);

//        Integer [] array = {1, 2, 3};
//        List<Integer> list1 = Arrays.asList(array); //因为Arrays.asList返回的是Arrays的内部类ArrayList，而且不支持 add 操作
//        list1.add(4);


    }
}

class Base{
    public Base(){
        test();
    }

    public void test(){
        System.out.println("Base :: test");
    }
}

class Child extends Base{
    private int a = 13;

    public Child(){
        System.out.println("Child 构造函数");
    }

    public void test(){
        System.out.println("Child test a = "+a);
    }
}

class Father {
    {
        System.out.println("父类的普通代码块1");
    }

    static {
        System.out.println("父类的静态代码块1");
    }

    static int a = 1;

    static {
        System.out.println("父类的静态代码块2 ， a = "+a );
    }

    int b = 1;

    static {
        a++;
        System.out.println("父类的静态代码块3 ， a = "+a );
    }

    final static int c = 1;

    static {
        System.out.println("父类的静态代码块4 ， c = "+c );
    }

    public Father(){
        a++;
        b++;
        System.out.println("父类的构造函数 a = "+a +", b = "+b+", c = "+c+", d = "+d);
    }

    {
        a++;
        b++;
        System.out.println("父类的普通代码块2 , a = "+a +", b = "+b+", c = "+c);
    }

    final int d = 1;

    public void func1(){
        a++;
        b++;
        System.out.println("func1 a = "+a +", b = "+b+", c = "+c+", d = "+d);
    }

    public static void func2(){
        a++;
        System.out.println("func2 , a = "+a +", c = "+c );
    }

}

class Son extends Father{
    {
        System.out.println("子类的普通代码块1");
    }

    static {
        System.out.println("子类的静态代码块1");
    }

    static int a = 1;

    static {
        System.out.println("子类的静态代码块2 , a = " + a);
    }

    int b = 1;

    static {
        a++;
        System.out.println("子类的静态代码块3, a = " + a);
    }

    public static int c = 1;

    static {
        System.out.println("子类的静态代码块4, c = " + c);
    }

    public Son(){
        a++;
        b++;
        System.out.println("子类的构造函数， a = "+a+", b = "+b+", c = "+c);
    }

    {
        a++;
        b++;
        System.out.println("子类的普通代码块， a = "+a+", b = "+b+", c = "+c);
    }

    final int d = 1;

    public void func1(){
        a++;
        b++;
        System.out.println("子类的func1 a = "+a+", b = "+b+", c = "+c+", d = "+d);
    }

    public static void func2(){
        a++;
        System.out.println("子类的func1 a = "+a + ", c = "+c);
    }

    public static class BBQ{
        static {
            System.out.println("BBQ");
        }
    }
}
