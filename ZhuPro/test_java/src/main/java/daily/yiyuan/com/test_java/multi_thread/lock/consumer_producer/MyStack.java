package daily.yiyuan.com.test_java.multi_thread.lock.consumer_producer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2019/8/29
 */
public class MyStack {
    private List<Integer> mList = new ArrayList<>();

    public synchronized void push() {
        try {
            while (mList.size() == 1) {
                System.out.println("push wait");
                this.wait();
            }
            int data = new Random().nextInt(10);
            mList.add(data);
            System.out.println("push data = " + data);
            this.notifyAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public synchronized void pop() {
        try {
            while (mList.size() == 0) {
                System.out.println("线程 "+Thread.currentThread().getName() +" pop wait" );
                this.wait();
            }
            System.out.println("线程 "+Thread.currentThread().getName() + " pop data = " + mList.remove(0));
            this.notifyAll();//notify 如果是多个线程则会导致假死的线下，将notify 改为notifyAll

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
