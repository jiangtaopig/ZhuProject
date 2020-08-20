package daily.yiyuan.com.test_java.multi_thread;

import java.util.concurrent.atomic.AtomicInteger;

import daily.yiyuan.com.test_java.multi_thread.my_thread.MyThreadLocal;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2019/12/20
 *
 * 总结：一个 ThreadLocal 只可以set一个值，最终保存的是最后一次set的值，
 * 一个线程可以通过new 多个不同的 ThreadLocal 来往 ThreadLocalMap 中存放不同的本地变量副本
 */
public class TestMyThreadLocal {
    private final int threadLocalHashCode = nextHashCode();
    static AtomicInteger nextHashCode = new AtomicInteger();
    private static final int HASH_INCREMENT = 1640531527;

    public static void main(String[] args) {
        MyThreadLocal<String> myThreadLocal = new MyThreadLocal<>();
        myThreadLocal.set("zjt");
        myThreadLocal.set("wq");

        System.out.println("----- val ----"+myThreadLocal.get());

        MyThreadLocal<String> threadLocal2 = new MyThreadLocal<>();
        threadLocal2.set("zcy");


        int a = new TestMyThreadLocal().threadLocalHashCode;
        int b = new TestMyThreadLocal().threadLocalHashCode;
        int c = new TestMyThreadLocal().threadLocalHashCode;

        System.out.println("a = " + a + ", b = " + b + ", c = " + c);

//       final Thread thread = new Thread(){
//           @Override
//           public void run() {
//               super.run();
//               ThreadLocal<String> threadLocal = new ThreadLocal<>();
//               threadLocal.set("123");
//
//               ThreadLocal<String> threadLocal2 = new ThreadLocal<>();
//               threadLocal2.set("4");
//
//               System.out.println(threadLocal.get()+", "+threadLocal2.get());
//
//           }
//       };
//
//       thread.start();

    }

    public static void val(TestMyThreadLocal testMyThreadLocal) {
        System.out.println(testMyThreadLocal.threadLocalHashCode);
    }

    public static int nextHashCode() {
        return nextHashCode.getAndAdd(HASH_INCREMENT);
    }

    class MyAtomicInteger {
        private volatile int value;

        public MyAtomicInteger(){

        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }
    }

}
