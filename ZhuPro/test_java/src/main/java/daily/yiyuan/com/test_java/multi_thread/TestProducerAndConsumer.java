package daily.yiyuan.com.test_java.multi_thread;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2019/12/19
 */
public class TestProducerAndConsumer {
    public static void main(String[] args) {

        final MyService myService = new MyService();
        new Thread(){
            @Override
            public void run() {
                for (int i = 0; i<5; i++){
                    myService.showOdd(i);
                }
            }
        }.start();


        new Thread(){
            @Override
            public void run() {
                for (int i = 0; i<10; i++){
                    myService.showEven(i);
                }
            }
        }.start();
    }
}


class MyService {
    private static boolean isOdd = true; //是否是奇数
    private ReentrantLock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();


    public void showOdd(int val) {
//        System.out.println("ThreadName "+Thread.currentThread().getName()+"  val = "+val);
        try {
            lock.lock();
            while (isOdd == false){//这里一定要用while不能用if，由于我们这里使用的是一个线程显示奇数一个线程显示偶数没问题，但是要是多个线程就会有问题
                condition.await();
            }
            System.out.println("ThreadName "+Thread.currentThread().getName()+" 1");
            isOdd = false;
            condition.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void showEven(int val){
//        System.out.println("ThreadName "+Thread.currentThread().getName()+"  val = "+val);
        try {
            lock.lock();
            while (isOdd == true){
                condition.await();
            }
            System.out.println("ThreadName "+Thread.currentThread().getName()+" 2");
            isOdd = true;
            condition.signal();
        }catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }


}