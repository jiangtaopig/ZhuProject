package daily.yiyuan.com.test_java.multi_thread.lock.join;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2019/8/30
 */
public class TestJoin {
    public static void main(String[] args) {

        B b = new B();
        A a = new A(b);

        try {
//            a.start();
            b.start();
            b.join(2000);
            System.out.println("main thread " + System.currentTimeMillis());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}

class B extends Thread {
    Object lock = new Object();

    @Override
    synchronized public void run() {
        try {
            System.out.println("B begin " + System.currentTimeMillis());
            Thread.sleep(5000);
            System.out.println("B end " + System.currentTimeMillis());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class A extends Thread {
    private B b;

    public A(B b) {
        this.b = b;
    }

    @Override
    public void run() {
        try {
            synchronized (b) {
                System.out.println("A begin " + System.currentTimeMillis());
                Thread.sleep(5000);
                System.out.println("A end " + System.currentTimeMillis());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
