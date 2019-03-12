package daily.yiyuan.com.test_java;

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

        System.out.println("s1 == s2 ? "+(s1 == s2));
        s1.isEmpty();
        s1.equals(s2);

        StringBuffer sb1 = new StringBuffer("hello");
        sb1.equals("abc");

    }
}
