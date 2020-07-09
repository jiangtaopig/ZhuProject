package daily.yiyuan.com.test_java.multi_thread;

import java.util.ArrayList;

public class TestWait {
    public static void main(String [] args){

        final Buf buf = new Buf();
        new Thread("ThreadA"){ //线程A
            @Override
            public void run() {
                try {
                    buf.get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();

        new Thread("ThreadB"){ //线程B
            @Override
            public void run() {
                try {
                    buf.get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();

        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread("ThreadC"){ //线程C
            @Override
            public void run() {
                try {
                    buf.put(3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}


class Buf{
    private final int MAX = 5;
    private final ArrayList<Integer> list = new ArrayList<>();
    synchronized void put(int v) throws InterruptedException {
        if (list.size() == MAX) {
            wait();
        }
        System.out.println("put v = "+v + ", thread = "+ Thread.currentThread().getName());
        list.add(v);
        notifyAll();
    }

    synchronized int get() throws InterruptedException {
        // 为什么多线程同步的时候 要用while而不是if
        //A线程和B线程在执行 get 方法的时候，由于list 的 size 为 0， 所以A和B线程都执行wait方法阻塞住并且释放了锁。
        //C 线程执行put操作是，调用 notifyAll() 方法唤醒A和B线程，假设是A线程先获得锁，那么A remove 后，现在 list 的大小又为 0 了
        // 此时线程A调用 notifyAll 方法来唤醒B线程，那么B线程获得锁之后，会直接调用 remove 方法，此时 list 的大小为0，所以报 IndexOutOfBoundsException

        // 解决方法就是将 if 改为 while 就好了，因为 是while的话， 此时线程A调用 notifyAll 方法来唤醒B线程，那么B线程获得锁之后 会执行 while 语句，判断 size == 0，执行 wait 方法
        if (list.size() == 0) {
            System.out.println("get 执行 wait" + ", thread = "+ Thread.currentThread().getName());
            wait();
        }
        int v = list.remove(0);
        System.out.println("get v = " + v + ", thread = "+ Thread.currentThread().getName());
        notifyAll();
        return v;
    }

    synchronized int size() {
        return list.size();
    }
}


